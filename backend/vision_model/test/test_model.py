import torch
import os
import sys
sys.path.append(".")


# TEST FOR MLP
def test_MLP():
    from src.model import MLP
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
