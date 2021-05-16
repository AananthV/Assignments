import numpy as np
import matplotlib.pyplot as plt

from cpnn import CPNN

# Load IRIS Dataset
[X, Y] = np.hsplit(np.loadtxt(open('iris.data', 'rb'), delimiter=','), [4])

# Pre process data

# Normalize X
X = (X - np.mean(X, axis=0)) / np.std(X, axis=0)

# One-Hot Encode Y
_Y = np.zeros((len(Y), 3))
for y in range(len(Y)):
    _Y[y][int(Y[y][0])] = 1

Y = _Y

# Restore indices
def get_indices(output):
    indices = []
    for o in output:
        indices.append(np.argmax(o.squeeze()))

    return np.array(indices)

cpnn = CPNN(7)

# Train
cpnn.train(X, Y)

# Test the network
predictions = cpnn.predict(X)

# Apply softmax to predictions
predictions = np.exp(predictions) / np.sum(np.exp(predictions), axis=1)[:, np.newaxis]

# Calculate and print loss
def cross_entropy_error(target, predicted):
    predicted = predicted.squeeze()
    epsilon = 1e-6 # To prevent log(0) errors
    predicted = np.clip(predicted, epsilon, 1 - epsilon)
    return -np.sum((target * np.log(predicted)) + ((1 - target) * np.log(1 - predicted))) / len(target)

print("Cross Entropy Error is", cross_entropy_error(Y, predictions))

# Plot the results
figure, axes = plt.subplots(1, 1)

axes.set_xlabel('Index')
axes.set_ylabel('Prediction')
axes.scatter([i for i in range(len(X))], get_indices(Y), label = 'Original')
axes.scatter([i for i in range(len(X))], get_indices(predictions), label = 'Predicted')
axes.legend()
axes.grid()

plt.show()
