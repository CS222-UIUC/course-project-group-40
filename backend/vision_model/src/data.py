import os
import sys
import pathlib
import cv2
import numpy as np
from PIL import Image
from torch.utils.data import Dataset, DataLoader
from utils.CreateRecAug import RandomBrightness, RandomContrast, \
    RandomLine, RandomSharpness, Compress, Rotate, \
    Blur, MotionBlur, Salt, AdjustResolution
__dir__ = pathlib.Path(os.path.dirname(os.path.realpath('__file__')))
sys.path.append(str(__dir__))
sys.path.append(str(__dir__.parent))
sys.path.append(str(__dir__.parent.parent))


# LOAD DATA
def load(file_path: str):
    file_path = pathlib.Path(file_path)
    func_dict = {'.txt': load_txt, '.list': load_txt}
    assert file_path.suffix in func_dict
    return func_dict[file_path.suffix](file_path)


# LOAD DATA FOR TXT FORMAT
def load_txt(file_path: str):
    with open(file_path, 'r', encoding='utf8') as f:
        content = [x.strip().strip('\ufeff').strip('\xef\xbb\xbf')
                   for x in f.readlines()]
    return content


class OcrRecDataSet(Dataset):
    # INITIALIZATION
    def __init__(self, txt_path, txt_type, transform=None):
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


class RecDataProcess:
    def __init__(self, config):
        """
        The text is augmented by data

        :param config: input_h, mean, std
        """
        self.config = config
        self.random_contrast = RandomContrast(probability=0.3)
        self.random_brightness = RandomBrightness(probability=0.3)
        self.random_sharpness = RandomSharpness(probability=0.3)
        self.compress = Compress(probability=0.3)
        self.rotate = Rotate(probability=0.5)
        self.blur = Blur(probability=0.3)
        self.motion_blur = MotionBlur(probability=0.3)
        self.salt = Salt(probability=0.3)
        self.adjust_resolution = AdjustResolution(probability=0.3)
        self.random_line = RandomLine(probability=0.3)
        self.random_contrast.setparam()
        self.random_brightness.setparam()
        self.random_sharpness.setparam()
        self.compress.setparam()
        self.rotate.setparam()
        self.blur.setparam()
        self.motion_blur.setparam()
        self.salt.setparam()
        self.adjust_resolution.setparam()

    def resize_with_specific_height(self, _img):
        """
        Resize the image to the specified height
        :param _img:  Image to be resized
        :return:      Image after resizing
        """
        resize_ratio = self.config.input_h / _img.shape[0]
        return cv2.resize(_img, (0, 0), fx=resize_ratio, fy=resize_ratio, interpolation=cv2.INTER_LINEAR)

    def normalize_img(self, _img):
        """
        Normalization based on the mean and standard deviation of the configuration
        :param _img:  Image to be normalized
        :return:      Image after normalization
        """
        return (_img.astype(np.float32) / 255 - self.config.mean) / self.config.std

    def width_pad_img(self, _img, _target_width, _pad_value=0):
        """
        Padding the image to the same HEIGHT and WIDTH
        :param _img:    Image to be padded
        :param _target_width:   Target width
        :param _pad_value:  Pad value
        :return:    Desired image
        """
        _height, _width, _channels = _img.shape
        to_return_img = np.ones([_height, _target_width, _channels], dtype=_img.dtype) * _pad_value
        to_return_img[:_height, :_width, :] = _img
        return to_return_img


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
