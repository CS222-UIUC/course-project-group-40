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

        self.device = torch.device('cpu')
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
    ############################################################
    # 1. INIT ARUGMENTS AND LOAD IMAGE

    import cv2
    args = init_args()
    img = [cv2.imread(args.img_path)]

    # log_files_list = [str(x) for x in pathlib.Path(args.img_path).glob("*.jpg")]
    # img = [cv2.imread(img_path) for img_path in log_files_list]

    ############################################################
    # 2. LOAD MODEL AND PREDICT

    # RecModel = RecInfer(args.model_path)
    # out = RecModel.predict(img)

    # print(out)
    # for i in range(30):
    #     print(out[i])
    # exit()

    ############################################################
    # 3. MODIFY AND SAVE THE PRETRAINED MODEL TO FIT RECOGNIZATION TASK

    # from torch import nn
    # model = nn.DataParallel(model.model)

    # ckpt = torch.load(args.model_path, map_location='cpu')
    # cfg = ckpt['cfg']


    # cfg.train_options['checkpoint_save_dir'] = "./output/ocr/CRNN/"
    # cfg['dataset']['alphabet'] = "./dataset/ocr/alphabets/ppocr_keys_v1.txt"

    # from utils import get_logger
    # logger = get_logger('ocr', log_file=os.path.join(cfg.train_options['checkpoint_save_dir'], 'train.log'))
    # optimizer = torch.optim.Adam(model.parameters(), lr=0.001)
    # scheduler = torch.optim.lr_scheduler.StepLR(
    #     optimizer,
    #     step_size=1,
    #     gamma=0.95
    # )
    # save_checkpoint("./output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth", model, optimizer, logger, cfg)

    ############################################################
    # 4. LOAD MODEL(JAVA STYLE) AND PREDICT

    from model.ocr import build_model_for_java

    ckpt = torch.load(args.model_path, map_location='cpu')
    cfg = ckpt['cfg']
    RecModelForJava = build_model_for_java(cfg)
    state_dict = {}

    for k, v in ckpt['state_dict'].items():
        state_dict[k.replace('module.', '')] = v
    RecModelForJava.load_state_dict(state_dict)

    device = torch.device('cuda:0' if torch.cuda.is_available() else 'cpu')
    RecModelForJava.to(device)
    RecModelForJava.eval()

    out = RecModelForJava.predict(img)
    print(out)
    
    exit()
    ############################################################
    # 5. MODIFY AND SAVE THE PRETRAINED MODEL TO FIT JAVA STYLE

    from model.ocr import build_model_for_java

    ckpt = torch.load(args.model_path, map_location='cpu')
    cfg = ckpt['cfg']
    RecModelForJava = build_model_for_java(cfg)
    state_dict = {}


    for k, v in ckpt['state_dict'].items():
        state_dict[k.replace('module.', '')] = v
    RecModelForJava.load_state_dict(state_dict)

    device = torch.device('cuda:0' if torch.cuda.is_available() else 'cpu')
    RecModelForJava.to(device)
    RecModelForJava.eval()

    cfg.train_options['checkpoint_save_dir'] = "./output/ocr/CRNN/"
    cfg['dataset']['alphabet'] = "./dataset/ocr/alphabets/ppocr_keys_v1.txt"

    mode_state_dict = RecModelForJava.state_dict()
    checkpoint_path = "./output/ocr/CRNN/ch_rec_moblie_crnn_mbv2.pth"
    state = {'state_dict': mode_state_dict,
             'cfg': cfg}
    torch.save(state, checkpoint_path)

