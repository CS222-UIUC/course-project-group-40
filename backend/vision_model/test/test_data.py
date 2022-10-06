import numpy as np
from regex import D
import torch
import os
import sys
sys.path.append(".")


# TEST FOR OcrRecDataSet
def test_OcrRecDataSet():
    from torchvision import transforms
    from torch.utils.data import Dataset, DataLoader
    import PIL
    from src.data import OcrRecDataSet
    from src.train import setup_args
    
    sys.argv = ['']
    args = setup_args()
    
    # TEST OcrRecDataSet
    dataset = OcrRecDataSet(args.data_path, args.data_type)

    assert dataset.txt_path == "./datasets/icdar2015/recognition/test/"
    assert dataset.txt_type == "test.txt"
    assert dataset.transform is None
    assert isinstance(dataset[0][0], PIL.Image.Image)


    # TEST OcrRecDataSet with transform
    dataset = OcrRecDataSet(args.data_path, args.data_type, transform=transforms.ToTensor())

    # TEST DATASET
    train_loader = DataLoader(dataset=dataset, batch_size=1, shuffle=False, num_workers=0)
    for img, label in train_loader:
        break

    assert len(dataset) == 2074
    assert img.shape == torch.Size([1, 3, 33, 113])
    assert label == ('JOINT', )

    # TEST GET ITEM WITH ERROR
    assert dataset[10000000]


if __name__ == "__main__":
    test_OcrRecDataSet()