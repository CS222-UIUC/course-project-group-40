import torch
from torch import nn

from model.ocr.backbones.RecMobileNetV3 import MobileNetV3
from model.ocr.backbones.RecResNetvd import ResNet
from model.ocr.necks.RNN import SequenceEncoder, Im2Seq

from model.ocr.heads.RecCTCHead import CTC
from utils import CTCLabelConverter
from src.data import RecDataProcess
import numpy as np


backbone_dict = {'MobileNetV3': MobileNetV3, 'ResNet': ResNet}
neck_dict = {'PPaddleRNN': SequenceEncoder, 'None': Im2Seq}
head_dict = {'CTC': CTC}


class RecModel(nn.Module):
    def __init__(self, config):
        super().__init__()
        assert 'in_channels' in config, 'in_channels must in model config'
        backbone_type = config.backbone.pop('type')
        assert backbone_type in backbone_dict, f'backbone.type must in {backbone_dict}'
        self.backbone = backbone_dict[backbone_type](config.in_channels, **config.backbone)

        neck_type = config.neck.pop('type')
        assert neck_type in neck_dict, f'neck.type must in {neck_dict}'
        self.neck = neck_dict[neck_type](self.backbone.out_channels, **config.neck)

        head_type = config.head.pop('type')
        assert head_type in head_dict, f'head.type must in {head_dict}'
        self.head = head_dict[head_type](self.neck.out_channels, **config.head)

        self.name = f'RecModel_{backbone_type}_{neck_type}_{head_type}'

    def forward(self, x):
        x = self.backbone(x)
        x = self.neck(x)
        x = self.head(x)
        return x


class RecModelForJava(RecModel):
    def __init__(self, config, dataset_config, converter_config, batch_size=16):
        super().__init__(config)
        self.process = RecDataProcess(dataset_config)
        self.converter = CTCLabelConverter(converter_config)
        self.batch_size = batch_size
        self.device = torch.device('cuda:0' if torch.cuda.is_available() else 'cpu')

    def forward(self, x):
        x = self.backbone(x)
        x = self.neck(x)
        x = self.head(x)
        return x

    def predict(self, imgs):
        if not isinstance(imgs, list):
            imgs = [imgs]
        imgs = [self.process.normalize_img(self.process.resize_with_specific_height(img)) for img in imgs]
        widths = np.array([img.shape[1] for img in imgs])
        idxs = np.argsort(widths)
        txts = []
        for idx in range(0, len(imgs), self.batch_size):
            batch_idxs = idxs[idx: min(len(imgs), idx + self.batch_size)]
            batch_imgs = [self.process.width_pad_img(imgs[idx], imgs[batch_idxs[-1]].shape[1]) for idx in batch_idxs]
            batch_imgs = np.stack(batch_imgs)
            tensor = torch.from_numpy(batch_imgs.transpose([0, 3, 1, 2])).float()
            tensor = tensor.to(self.device)
            with torch.no_grad():
                out = self.forward(tensor)
                out = out.softmax(dim=2)
            out = out.cpu().numpy()
            txts.extend([self.converter.decode(np.expand_dims(txt, 0)) for txt in out])

        # Sort by the order of the input images
        idxs = np.argsort(idxs)
        out_txts = [txts[idx] for idx in idxs]
        return out_txts