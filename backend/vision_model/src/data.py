import sys
import numpy as np
from PIL import Image
from torch.utils.data import Dataset, DataLoader
sys.path.append(".")


class OcrRecDataSet(Dataset):
    # INITIALIZATION
    def __init__(self, txt_path, txt_type, transform=None):
        from src.utils import load
        self.txt_path = txt_path
        self.txt_type = txt_type
        self.data_list = load(self.txt_path + self.txt_type)
        self.transform = transform

    # GET ITEM
    def __getitem__(self, item):
        try:
            line = self.data_list[item].split('\t')
            img = Image.open(self.txt_path + line[0]).convert('RGB')
            img = self.pre_processing(img)
            label = self.make_label(line[1])

            if self.transform:
                img = self.transform(img)
            return img, label
        except Exception:
            return self.__getitem__(np.random.randint(self.__len__()))

    # GET LENGTH
    def __len__(self):
        return len(self.data_list)

    # MAKE LABEL
    def make_label(self, label):
        return label

    # PRE PROCESSING
    def pre_processing(self, img):
        return img


if __name__ == '__main__':
    from tqdm import tqdm
    from torchvision import transforms
    from matplotlib import pyplot as plt

    # SUPPORT CHINESE
    plt.rcParams['font.sans-serif'] = ['SimHei']  # Show Chinese labels
    plt.rcParams['axes.unicode_minus'] = False  # Show minus sign

    # SET FILE PATH
    json_path = "./backend/vision_model/dataset/icdar2015/recognition/test/"
    json_type = "test.txt"

    # GENERATE DATASET
    dataset = OcrRecDataSet(
        json_path,
        json_type,
        transform=transforms.ToTensor()
    )
    test_loader = DataLoader(
        dataset=dataset,
        batch_size=1,
        shuffle=True,
        num_workers=0
    )

    # SHOW SAMPLE
    pbar = tqdm(total=len(test_loader))
    for i, (img, label) in enumerate(test_loader):
        img = img[0].numpy().transpose(1, 2, 0)
        plt.title(label[0])
        plt.imshow(img)
        plt.show()
        pbar.update(1)
    pbar.close()
