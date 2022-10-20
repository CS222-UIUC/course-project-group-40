import torch.nn as nn
import torch.nn.init as init


def initiation_weight(m, module_type):
    initiation_functions_map1 = {
        nn.Conv1d: init.kaiming_normal_,
        nn.Conv2d: init.kaiming_normal_,
        nn.Conv3d: init.kaiming_normal_,
        nn.ConvTranspose1d: init.kaiming_normal_,
        nn.ConvTranspose2d: init.xavier_uniform_,
        nn.ConvTranspose3d: init.xavier_normal_,
        nn.BatchNorm1d: lambda x: init.normal_(x, mean=1, std=0.02),
        nn.BatchNorm2d: lambda x: init.constant_(x, 1),
        nn.BatchNorm3d: lambda x: init.normal_(x, mean=1, std=0.02),
        nn.Linear: lambda x: init.normal_(x, 0, 0.01)
    }
    initiation_functions_map1[module_type](m.weight.data)


def initiation_bias(m, module_type):
    initiation_functions_map2 = {
        nn.Conv1d: init.kaiming_normal_,
        nn.Conv2d: init.normal_,
        nn.Conv3d: init.kaiming_normal_,
        nn.ConvTranspose1d: init.kaiming_normal_,
        nn.ConvTranspose2d: init.normal_,
        nn.ConvTranspose3d: init.normal_,
        nn.BatchNorm1d: lambda x: init.constant_(x, 0),
        nn.BatchNorm2d: lambda x: init.constant_(x, 0),
        nn.BatchNorm3d: lambda x: init.constant_(x, 0),
        nn.Linear: lambda x: init.constant_(x, 0)
    }
    if isinstance(m, nn.BatchNorm1d) or isinstance(m, nn.BatchNorm2d) or isinstance(m, nn.BatchNorm3d):
        initiation_functions_map2[module_type](m.bias.data)
    elif m.bias is not None:
        initiation_functions_map2[module_type](m.bias.data)


def init_type_1(m):
    initiation_weight(m, m.__class__)
    initiation_bias(m, m.__class__)


def init_type_2(m):
    for param in m.parameters():
        if len(param.shape) >= 2:
            init.orthogonal_(param.data)
        else:
            init.normal_(param.data)


def init_type_3(m):
    for param in m.parameters():
        if len(param.shape) >= 2:
            init.orthogonal_(param.data)
        else:
            init.xavier_uniform_(param.data)


def weight_init(m):
    """
    Usage:
        model = Model()
        model.apply(weight_init)
    """
    initiation_functions = {
        nn.Conv1d: init_type_1,
        nn.Conv2d: init_type_1,
        nn.Conv3d: init_type_1,
        nn.ConvTranspose1d: init_type_1,
        nn.ConvTranspose2d: init_type_1,
        nn.ConvTranspose3d: init_type_1,
        nn.BatchNorm1d: init_type_1,
        nn.BatchNorm2d: init_type_1,
        nn.BatchNorm3d: init_type_1,
        nn.Linear: init_type_1,
        nn.LSTM: init_type_2,
        nn.LSTMCell: init_type_3,
        nn.GRU: init_type_3,
        nn.GRUCell: init_type_3
    }

    initiation_functions[m](m)
