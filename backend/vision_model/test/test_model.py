import torch
import os

# __dir__ = pathlib.Path(os.path.dirname(os.path.realpath('__file__')))
# sys.path.append(str(__dir__))
# sys.path.append(str(__dir__.parent))
# sys.path.append(str(__dir__.parent.parent))


# TEST FOR MLP
def test_MLP():
    from src.mlp import MLP
    model = MLP()

    # TEST MLP
    assert model.output_dim == 2
    assert model.dropout.p == 0.1

    # TEST FORWARD
    input = torch.randn(1, 8)
    output = model(input)
    assert output.shape == torch.Size([1, 2])

    # TEST SAVE
    model.save("test.pt")
    assert os.path.exists("test.pt")

    # TEST LOAD
    model.load("test.pt")
    assert os.path.exists("test.pt")

    # TEST REMOVE
    os.remove("test.pt")
    assert not os.path.exists("test.pt")


def test_build_model():
    import sys
    from src.train import setup_args, setup_seed
    from model.ocr import build_model
    setup_seed()
    sys.argv = ['',]
    opt = setup_args()
    model = build_model(opt)
    assert model
