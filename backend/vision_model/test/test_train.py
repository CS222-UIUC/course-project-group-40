import numpy as np
import torch
import os
import sys
# import random # Curently not used
# import pathlib
# sys.path.append(".")


# TEST FOR SEED SETUP
def test_seed():
    from src.train import setup_seed
    setup_seed(17)

    # TESTS
    assert os.environ['PYTHONHASHSEED'] == "17"
    assert np.random.get_state()[1][0] == 17
    assert torch.backends.cudnn.benchmark is False
    assert torch.backends.cudnn.deterministic is True


# TEST FOR Arguments Parser
def test_args():
    from src.train import setup_args
    sys.argv = ['']
    args = setup_args()

    # TESTS
    assert args.data_path == "./dataset/ocr/icdar2015/recognition/test/"
    assert args.data_type == "test.txt"
    assert args.device == "cpu"
    assert args.num_classes == 5
    assert args.epochs == 10
    assert args.batch_size == 8
    assert args.lr == 0.0002
    assert args.weights == "./vision.pt"
    assert args.step_size == 5
    assert args.gamma == 0.1


# TEST FOR DEVICE SETUP
def test_device():
    from src.train import setup_device
    from src.train import setup_args
    sys.argv = ['']
    args = setup_args()
    device = setup_device(args)

    # TESTS
    assert str(device) == "cpu"


# TEST FOR TRAINING MAIN FUNCTION
def test_main():
    from src.train import setup_args, main
    sys.argv = ['']
    opt = setup_args()

    # TESTS
    main(opt)


# TEST FOR BUILD OPTIMIZER
def test_optimizer():
    from src.train import build_optimizer
    from src.train import setup_args
    from src.mlp import MLP

    sys.argv = ['']
    args = setup_args()

    # TESTS
    net = MLP()
    optimizer = build_optimizer(net, args)
    assert isinstance(optimizer, torch.optim.Adam)


# TEST FOR BUILD SCHEDULER
def test_scheduler():
    from src.train import build_optimizer
    from src.train import build_scheduler
    from src.mlp import MLP
    from src.train import setup_args
    sys.argv = ['']
    args = setup_args()

    # TESTS
    net = MLP()
    optimizer = build_optimizer(net, args)
    scheduler = build_scheduler(optimizer, args)
    assert isinstance(scheduler, torch.optim.lr_scheduler.StepLR)


if __name__ == "__main__":
    test_seed()
    test_args()
    test_device()
    test_main()
