import numpy as np

relu_activation = {
    'function': lambda x: np.maximum(0, x),
    'gradient': lambda x: x > 0
}

linear_activation = {
    'function': lambda x: x,
    'gradient': lambda x: 1
}

def sigmoid(x):
    return 1 / (1 + np.exp(-x))

def sigmoid_gradient(x):
    s = sigmoid(x)
    return s * (1 - s)

sigmoid_activation = {
    'function': sigmoid,
    'gradient': sigmoid_gradient
}

def softmax(x):
    return np.exp(x) / np.sum(np.exp(x))

def softmax_gradient(x):
    s = softmax(x)
    return s * (1 - s)

softmax_activation = {
    'function': softmax,
    'gradient': softmax_gradient
}