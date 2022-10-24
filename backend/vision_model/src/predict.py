import os
import sys
import pathlib
import numpy as np
import torch

__dir__ = pathlib.Path(os.path.dirname(os.path.realpath('__file__')))
sys.path.append(str(__dir__))
sys.path.append(str(__dir__.parent))
sys.path.append(str(__dir__.parent.parent))


# CLASS FOR RECOGNIZATION PREDICT
class RecInfer:
    def __init__(self, model_path, batch_size=16):
        from model.ocr import build_model
        from utils import CTCLabelConverter
        from src.data import RecDataProcess

        ckpt = torch.load(model_path, map_location='cpu')
        cfg = ckpt['cfg']
        self.model = build_model(cfg)
        state_dict = {}

        for k, v in ckpt['state_dict'].items():
            state_dict[k.replace('module.', '')] = v
        self.model.load_state_dict(state_dict)

        self.device = torch.device('cuda:0' if torch.cuda.is_available() else 'cpu')
        self.model.to(self.device)
        self.model.eval()

        self.process = RecDataProcess(cfg['dataset']['train']['dataset'])

        self.converter = CTCLabelConverter(cfg['dataset']['alphabet'])
        self.batch_size = batch_size

    # PREDICT FUNCTION
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
                out = self.model(tensor)
                out = out.softmax(dim=2)
            out = out.cpu().numpy()
            txts.extend([self.converter.decode(np.expand_dims(txt, 0)) for txt in out])

        # Sort by the order of the input images
        idxs = np.argsort(idxs)
        out_txts = [txts[idx] for idx in idxs]
        return out_txts


# INIT ARUGMENTS
def init_args():
    import argparse
    parser = argparse.ArgumentParser(description='PytorchOCR infer')
    parser.add_argument('--model_path', required=True, type=str, help='rec model path')
    parser.add_argument('--img_path', required=True, type=str, help='img path for predict')

    args = parser.parse_args()
    return args


if __name__ == '__main__':
    import cv2
    # from utils import save_checkpoint, get_logger

    args = init_args()

    img = [cv2.imread(args.img_path)]

    # log_files_list = [str(x) for x in pathlib.Path(args.img_path).glob("*.jpg")]
    # img = [cv2.imread(img_path) for img_path in log_files_list]

    model = RecInfer(args.model_path)
    out = model.predict(img)

    print(out)
    # for i in range(30):
    #     print(out[i])

    # ckpt = torch.load(args.model_path, map_location='cpu')
    # cfg = ckpt['cfg']

    # cfg.train_options['checkpoint_save_dir'] = "./output/ocr/CRNN/"
    # cfg['dataset']['alphabet'] = "./dataset/ocr/alphabets/ppocr_keys_v1.txt"
    # print(cfg.train_options['checkpoint_save_dir'])

    # logger = get_logger('ocr', log_file=os.path.join(cfg.train_options['checkpoint_save_dir'], 'train.log'))
    # model = nn.DataParallel(model.model)
    # optimizer = build_optimizer(model, cfg['optimizer'])
    # scheduler = build_scheduler(optimizer, cfg['lr_scheduler'])

    # save_checkpoint("./output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth", model, optimizer, logger, cfg)
