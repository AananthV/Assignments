# Done by V.Aananth (106118103)

# Multilayer Feed Forward Neural Network with Backpropagation

## Dependencies
1. Python3
2. Numpy

## Usage
0. Install dependencies using ```pip install -r requirements.txt```
1. Use the Neural Network class in neuralnetwork.py
2. Initialise with information about the layers

```
    layer {
        inputs: 2, # Number of inputs,
        outputs: 2, # Number of outputs,
        activation: activation_dict, # Activation function, check activations.py for sample,
        learning_rate: 1e-3 # Learning rate of layer 
    }
```

3. Train using nn.train(X, Y)
4. Predict using nn.predict(X)

## Example
1. For a sample used to classify samples using the IRIS dataset check sample.py and samples/ folder for screenshots.
2. Running example: ```python sample.py```
