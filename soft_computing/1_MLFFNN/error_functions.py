import numpy as np

def rms_error(target, predicted):
    predicted = predicted.squeeze()
    return np.sqrt(1 / len(target) * np.sum((target - predicted) ** 2))

def cross_entropy_error(target, predicted):
    predicted = predicted.squeeze()
    epsilon = 1e-6 # To prevent log(0) errors
    predicted = np.clip(predicted, epsilon, 1 - epsilon)
    return -np.sum((target * np.log(predicted)) + ((1 - target) * np.log(1 - predicted))) / len(target)