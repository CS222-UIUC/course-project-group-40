import sys
sys.path.append("./backend/vision-model/")
import torch
import random
import os
import numpy as np

## TEST FOR SEED SETUP
def test_seed():
    from src.train import setup_seed
    setup_seed(17)

    # TESTS
    assert os.environ['PYTHONHASHSEED'] == "17"
    assert np.random.get_state()[1][0] == 17
    assert torch.backends.cudnn.benchmark == False
    assert torch.backends.cudnn.deterministic == True

## TEST FOR Arguments Parser
def test_args():
    from src.train import setup_args
    sys.argv=['']
    args = setup_args()

    # TESTS
    assert args.data_path == "./data/"
    assert args.device == "cpu"
    assert args.num_classes == 5
    assert args.epochs == 10
    assert args.batch_size == 8
    assert args.lr == 0.0002
    assert args.weights == "./vision.pt"

## TEST FOR DEVICE SETUP
def test_device():
    from src.train import setup_device
    from src.train import setup_args
    sys.argv=['']
    args = setup_args()
    device = setup_device(args)

    # TESTS
    assert str(device) == "cpu"

## TEST FOR TRAINING MAIN FUNCTION 
def test_main():
    from src.train import main
    from src.train import setup_args
    sys.argv=['']
    args = setup_args()

    # TESTS
    main(args)

if __name__ == "__main__":
    test_seed()
    test_args()
    test_device()
    test_main()