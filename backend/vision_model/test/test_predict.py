import torch
import sys


# TEST FOR Arguments Parser
def test_args():
    from src.predict import init_args
    sys.argv = ['', '--model_path', '1', '--img_path', '2']
    args = init_args()

    # TESTS
    assert args.model_path == "1"
    assert args.img_path == "2"


def test_RecInfer():
    from src.predict import RecInfer, init_args
    import cv2

    sys.argv = [
        '',
        '--model_path',
        './output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth',
        '--img_path',
        './dataset/ocr/icdar2015/recognition/test/img_10_1.jpg'
    ]
    args = init_args()

    model = RecInfer(args.model_path)
    img_list = [cv2.imread(args.img_path)]
    out_list = model.predict(img_list)

    img = cv2.imread(args.img_path)
    out = model.predict(img)

    # TESTS
    assert model.batch_size == 16
    assert model.device == torch.device('cuda:0' if torch.cuda.is_available() else 'cpu')
    assert out_list[0][0][0] == 'lowe'
    assert out[0][0][0] == 'lowe'
