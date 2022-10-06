from torch import nn
import torch
import torch.nn.functional as F


# Currently NOT in use!!
# USE the simple MLP to test the other part of the code
class MLP(nn.Module):
    def __init__(self, input_dim=8, output_dim=2, hidden_dim=32, dropout=0.1):
        super().__init__()
        self.output_dim = output_dim
        self.layer1 = nn.Linear(input_dim, hidden_dim)
        self.dropout = nn.Dropout(dropout)
        self.layer2 = nn.Linear(hidden_dim, output_dim)

    def forward(self, X, **kwargs):
        X = F.relu(self.layer1(X))
        X = self.dropout(X)
        X = self.layer2(X)
        return X.squeeze(-1) if self.output_dim == 1 else F.log_softmax(X, dim=-1)

    def save(self, name):
        torch.save(self, name)

    def load(self, name):
        self.load_state_dict(torch.load(name).state_dict())
