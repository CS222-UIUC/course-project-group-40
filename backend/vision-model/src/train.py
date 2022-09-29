import argparse
import torch
import random
import os
import numpy as np
SEED = 17

def setup_seed(seed=SEED):
    random.seed(seed)
    os.environ['PYTHONHASHSEED'] = str(seed)
    np.random.seed(seed)
    torch.manual_seed(seed)
    torch.cuda.manual_seed(seed)
    torch.cuda.manual_seed_all(seed) # if you are using multi-GPU.
    torch.backends.cudnn.benchmark = False
    torch.backends.cudnn.deterministic = True
    # torch.backends.cudnn.enabled = False

def setup_args():
    parser = argparse.ArgumentParser()
    parser.add_argument('--data_path', type=str,
                        default="./data/")
    parser.add_argument('--device', default='cpu', help='device id (i.e. 0 or 0,1 or cpu)')

    parser.add_argument('--num_classes', type=int, default=5)
    parser.add_argument('--epochs', type=int, default=10)
    parser.add_argument('--batch_size', type=int, default=8)
    parser.add_argument('--lr', type=float, default=0.0002)
    parser.add_argument('--weights', type=str, default='./vision.pt',
                        help='initial weights path')
    return parser.parse_args()

def setup_device(args):
    device = torch.device(args.device if torch.cuda.is_available() else "cpu")
    return device

def main(args):
    # Device configuration
    device = setup_device(args)
    
    # Placeholder for the model
    # model = nn.Sequential(
    #


if __name__ == '__main__':
    setup_seed()
    opt = setup_args()
    main(opt)