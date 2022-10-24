import os
import torch


def load_checkpoint(_model, resume_from, to_use_device, _optimizers=None, third_name=None):
    """
    Loading pre-trained models
    Args:
        _model:  Model
        resume_from: Pre-trained model path
        to_use_device: Device
        _optimizers: Indicates that the training parameters of the model if it is not None
        third_name: Name of the third-party pre-trained model

    Returns:

    """
    global_state = {}
    if not third_name:
        state = torch.load(resume_from, map_location=to_use_device)
        _model.load_state_dict({'module.' + k: v for k, v in state['state_dict'].items()}, strict=True)
        # _model.load_state_dict(state['state_dict'])
        if 'optimizer' in state and _optimizers is not None:
            _optimizers.load_state_dict(state['optimizer'])
        if 'global_state' in state:
            global_state = state['global_state']

    return _model, _optimizers, global_state


def save_checkpoint(checkpoint_path, model, _optimizers, logger, cfg, **kwargs):
    mode_state_dict = model.module.state_dict()
    state = {'state_dict': mode_state_dict,
             'optimizer': _optimizers.state_dict(),
             'cfg': cfg}
    state.update(kwargs)
    torch.save(state, checkpoint_path)
    logger.info('models saved to %s' % checkpoint_path)


def save_checkpoint_logic(total_loss, total_num, min_loss, net, solver, epoch, rec_train_options, logger):
    """
    Save the model according to the configuration file
    Args:
        total_loss:
        total_num:
        min_loss:
        net:
        epoch:
        rec_train_options:
        logger:
    Returns:

    """
    # operation for model save as parameter ckpt_save_type is  HighestAcc
    if rec_train_options['ckpt_save_type'] == 'HighestAcc':
        loss_mean = sum([total_loss[idx] * total_num[idx] for idx in range(len(total_loss))]) / sum(total_num)
        if loss_mean < min_loss:
            min_loss = loss_mean
            save_checkpoint(os.path.join(rec_train_options['checkpoint_save_dir'], 'epoch_' + str(epoch) + '.pth'), net,
                            solver, epoch, logger)
    else:
        if epoch % rec_train_options['ckpt_save_epoch'] == 0:
            save_checkpoint(os.path.join(rec_train_options['checkpoint_save_dir'], 'epoch_' + str(epoch) + '.pth'), net,
                            solver, epoch, logger)
    return min_loss
