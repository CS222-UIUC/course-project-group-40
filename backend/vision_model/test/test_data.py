import torch
import sys
sys.path.append(".")


# TEST FOR OcrRecDataSet
def test_OcrRecDataSet():
    from torchvision import transforms
    from torch.utils.data import DataLoader
    import PIL
    from src.data import OcrRecDataSet
    from src.train import setup_args

    sys.argv = ['']
    args = setup_args()

    # TEST OcrRecDataSet
    dataset = OcrRecDataSet(args.data_path, args.data_type)

    assert dataset.txt_path == "./dataset/ocr/icdar2015/recognition/test/"
    assert dataset.txt_type == "test.txt"
    assert dataset.transform is None
    assert isinstance(dataset[0][0], PIL.Image.Image)

    # TEST OcrRecDataSet with transform
    dataset = OcrRecDataSet(
        args.data_path,
        args.data_type,
        transform=transforms.ToTensor()
    )
    train_loader = DataLoader(
        dataset=dataset,
        batch_size=1,
        shuffle=False,
        num_workers=0
    )
    for img, label in train_loader:
        break

    assert len(dataset) == 2074
    assert img.shape == torch.Size([1, 3, 33, 113])
    assert label == ('JOINT', )

    # TEST GET ITEM WITH ERROR
    assert dataset[10000000]


def test_ObjDetectDataSet():
    from src.predict import ObjDecInfer, init_args
    import cv2

    sys.argv = [
        '',
        '--model_path',
        './output/object',
        '--img_path',
        './dataset/object/samples/dog.jpg'
    ]
    args = init_args()

    img = cv2.imread(args.img_path)
    print(img.shape)
    assert img.shape == (576, 768, 3)


if __name__ == "__main__":
    test_OcrRecDataSet()
