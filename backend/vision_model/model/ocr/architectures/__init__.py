from addict import Dict
import copy
import os
import sys
from importlib import import_module
from .RecModel import RecModel, RecModelForJava

support_model = {'RecModel': RecModel}
support_model_for_java = {'RecModel': RecModelForJava}


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
    if not isinstance(args, Dict):
        args = parse_args_for_ocr(args)
    config = copy.deepcopy(args)
    model_config = config['model']
    arch_type = model_config.pop('type')
    assert arch_type in support_model, f'{arch_type} is not developed yet!, only {support_model} are support now'
    arch_model = support_model[arch_type](Dict(model_config))
    return arch_model


def build_model_for_java(args):
    """
    get architecture model class FOR Java only
    """
    if not isinstance(args, Dict):
        args = parse_args_for_ocr(args)
    config = copy.deepcopy(args)
    model_config = config['model']
    dataset_config = config['dataset']['train']['dataset']
    convertor_config = config['dataset']['alphabet']
    arch_type = model_config.pop('type')
    assert arch_type in support_model_for_java, f'{arch_type} is not developed yet!, only {support_model_for_java} are support now'
    arch_model = support_model_for_java[arch_type](Dict(model_config), dataset_config, convertor_config)
    return arch_model
