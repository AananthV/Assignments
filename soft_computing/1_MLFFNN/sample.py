import numpy as np
import matplotlib.pyplot as plt
from neuralnetwork import *
from activations import relu_activation, sigmoid_activation, softmax_activation
from error_functions import rms_error, cross_entropy_error

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

# Parameters
lr = 1e-1 # Learning rate

# Define layers
layers = [
    {
        'inputs': 4,
        'outputs': 4,
        'activation': relu_activation,
        'learning_rate': lr
    },
    {
        'inputs': 4,
        'outputs': 3,
        'activation': softmax_activation,
        'learning_rate': lr
    },
]

# Initialize network
nn = NeuralNetwork(layers)

# Train the network
nn.train(X, Y, num_epochs=20)

# Predict outputs
predictions = nn.predict(X)

# Print final weights
print("\nFinal weights are:")

for i, layer in enumerate(nn.layers):
    print("\nLayer", i + 1, "\nWeights:\n", layer.weights, "\nBiases:\n", layer.biases, "\n")


# Calculate and print loss
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
