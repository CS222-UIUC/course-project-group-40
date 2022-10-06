import argparse
import torch
import random
import os
import numpy as np
from torchvision import transforms
SEED = 17


# SET UP RANDOM SEEDS
def setup_seed(seed=SEED):
    random.seed(seed)
    os.environ['PYTHONHASHSEED'] = str(seed)
    np.random.seed(seed)
    torch.manual_seed(seed)
    torch.cuda.manual_seed(seed)
    torch.cuda.manual_seed_all(seed)  # if you are using multi-GPU.
    torch.backends.cudnn.benchmark = False
    torch.backends.cudnn.deterministic = True


# SET UP ARGUMENTS PARSER
def setup_args():
    parser = argparse.ArgumentParser()
    parser.add_argument('--data_path', type=str,
                        default="./datasets/icdar2015/recognition/test/")
    parser.add_argument('--data_type', type=str,
                        default="test.txt")
    parser.add_argument('--device', default='cpu',
                        help='device id (i.e. 0 or 0,1 or cpu)')

    parser.add_argument('--num_classes', type=int, default=5)
    parser.add_argument('--epochs', type=int, default=10)
    parser.add_argument('--batch_size', type=int, default=8)
    parser.add_argument('--lr', type=float, default=0.0002)
    parser.add_argument('--weights', type=str, default='./vision.pt',
                        help='initial weights path')
    parser.add_argument('--step_size', type=int, default=5)
    parser.add_argument('--gamma', type=float, default=0.1)
    return parser.parse_args()


# SET UP DEVICE
def setup_device(args):
    device = torch.device(args.device if torch.cuda.is_available() else "cpu")
    return device


# BUILD OPTIMIZER
def build_optimizer(model, args):
    optimizer = torch.optim.Adam(model.parameters(), lr=args.lr)
    return optimizer


# BUILD SCHDULER
def build_scheduler(optimizer, args):
    scheduler = torch.optim.lr_scheduler.StepLR(optimizer, step_size=args.step_size, gamma=args.gamma)
    return scheduler


# TRAINING MAIN FUNCTION
def main(args):
    # ===> SET UP DEVICE
    device = setup_device(args)

    # ===> SET UP RANDOM SEEDS
    setup_seed()

    # ===> LOAD DATA
    from src.data import OcrRecDataSet
    dataset = OcrRecDataSet(args.data_path, args.data_type, transform=transforms.ToTensor())

if __name__ == '__main__':
    setup_seed()
    opt = setup_args()
    main(opt)
