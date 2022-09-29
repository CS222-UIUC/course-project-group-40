import argparse
import torch

def setup_args():
    parser = argparse.ArgumentParser()
    parser.add_argument('--data_path', type=str,
                        default="./data/")
    parser.add_argument('--device', default='cuda:0', help='device id (i.e. 0 or 0,1 or cpu)')

    parser.add_argument('--num_classes', type=int, default=5)
    parser.add_argument('--epochs', type=int, default=10)
    parser.add_argument('--batch_size', type=int, default=8)
    parser.add_argument('--lr', type=float, default=0.0002)
    parser.add_argument('--weights', type=str, default='./vision.pt',
                        help='initial weights path')
    return parser.parse_args()

def main(args):
    # Device configuration
    device = torch.device(args.device if torch.cuda.is_available() else "cpu")
    
    # Placeholder for the model
    #
    #


if __name__ == '__main__':
    opt = setup_args()
    main(opt)