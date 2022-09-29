import sys
sys.path.append(".")

## TEST FOR Arguments Parser
def test_args():
    from src.train import setup_args
    sys.argv=['']
    args = setup_args()

    # TESTS
    assert args.data_path == "./data/"
    assert args.device == "cuda:0"
    assert args.num_classes == 5
    assert args.epochs == 10
    assert args.batch_size == 8
    assert args.lr == 0.0002
    assert args.weights == "./vision.pt"


## TEST FOR TRAINING MAIN FUNCTION 
def test_main():
    from src.train import main
    from src.train import setup_args
    sys.argv=['']
    args = setup_args()

    # TESTS
    main(args)

if __name__ == "__main__":
    test_args()
    test_main()