import torch


def save_checkpoint(checkpoint_path, model, _optimizers, epoch, logger):
    state = {'state_dict': model.state_dict(),
             'optimizer': [_.state_dict() for _ in _optimizers],
             'epoch': epoch}
    torch.save(state, checkpoint_path)
    logger.info('models saved to %s' % checkpoint_path)
