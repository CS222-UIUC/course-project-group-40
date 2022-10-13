from addict import Dict
import copy
import os
import sys
from importlib import import_module
from .RecModel import RecModel

support_model = {'RecModel': RecModel}


def parse_args_for_ocr(args):
    config_path = os.path.abspath(os.path.expanduser(args.config))
    assert os.path.isfile(config_path)
    if config_path.endswith('.py'):
        module_name = os.path.basename(config_path)[:-3]
        config_dir = os.path.dirname(config_path)
        sys.path.insert(0, config_dir)
        mod = import_module(module_name)
        sys.path.pop(0)
        return mod.config
    else:
        raise IOError('Only py type are supported now!')


def build_model(args):
    """
    get architecture model class
    """
    config = copy.deepcopy(parse_args_for_ocr(args))
    model_config = config['model']
    print(args)
    print(config)
    arch_type = model_config.pop('type')
    assert arch_type in support_model, f'{arch_type} is not developed yet!, only {support_model} are support now'
    arch_model = support_model[arch_type](Dict(model_config))
    return arch_model
