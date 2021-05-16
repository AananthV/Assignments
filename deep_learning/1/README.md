---
title: |
        Deep Learning (CSHO 23) \ \
        Assignment 1
author:
    - "S. Pavithra (106118091)"
    - "V. Aananth (106118103)"
    - "Yash Shah (106118107)"
toc: true
documentclass: scrartcl
...

\newpage

# 1. Multi-Class Classification on Fashion-MNIST

## 1.1. Dataset

- The dataset used here is Fashion-MNIST, which consists of **60,000** training examples and **10,000** test examples.
- Each example is a **28 x 28 grayscale image**, associated with a label from **10 classes** as specified below.

    | Class | Label         |
    | ----- | ------------- |
    | 0     | T-Shirt / Top |
    | 1     | Trouser       |
    | 2     | Pullover      |
    | 3     | Dress         |
    | 4     | Coat          |
    | 5     | Sandal        |
    | 6     | Shirt         |
    | 7     | Sneaker       |
    | 8     | Bag           |
    | 9     | Ankle Boot    |
    
![Sample Data with Labels](https://i.imgur.com/yoIC2Io.png)

## 1.2. Preprocessing

- To improve prediction accuracy, single-valued labels are **one hot encoded** into a list of size 10 (since there are 10 categories) with each position taking the binary value 0 or 1. ```OneHotEncoder``` from ```sklearn``` was used. 
- Value of **1** indicates that the corresponding label is **present** and a value of **0** indicates **absence** of that label.
- For example, if an image belongs to class 3 (Dress) it's one hot encoding will be 
    
    $$
    [0, 0, 0, 1, 0, 0, 0, 0, 0, 0]
    $$
    
## 1.3. Training

- ```RandomSearchCV``` was used to find the best hyperparameter combination from the range specified below:

    - \# of Hidden Layers: [5 - 30]
    - \# of Neurons per hidden layer: [16 - 1024]
    - Learning Rate: {0.01, 0.001, 0.0001, 0.00001}
    - Activation Function: {"relu","tanh"}

-  Since it's a multiclass classification problem, **Softmax Activation** function was used for the output layer along with **RMSProp Optimizer** to optimize our ANN and use **Categorical Cross Entropy Error** as our loss function and metric.
- Upon training the randomsearch cv model for **20 iterations** with 4 fold cross validation, the best hyperparameters found were:

    - \# of Hidden layers: 8
    - \# of neurons in each layer: [294, 709, 207, 796, 256, 165, 355, 450]
    - Learning rate: 0.001
    - Activation Functions: relu
 
## 1.4. Testing

- The best model obtained using ```grid_result.best_estimator_``` is tested with the test dataset and accuracy is measured as the percentage of correct outputs.
- The final accuracy obtained on the test dataset was **89.48%**.

\newpage

# 2. Linear Regression

## 2.1. Dataset

- The [dataset](https://www.kaggle.com/subashdump/california-housing-price-prediction) used here pertains to the houses found in a given California district and some summary stats about them based on the 1990 census data.
- Housing price depends on the following parameters:


    | \#  | Parameter          |
    |:---:| ------------------ |
    |  1  | longitude          |
    |  2  | latitude           |
    |  3  | housing_median_age |
    |  4  | total_rooms        |
    |  5  | total_bedrooms     |
    |  6  | population         |
    |  7  | households         |
    |  8  | median_income      |
    |  9  | ocean_proximity    |


## 2.2. Preprocessing

- Dataset was preprocessed to remove null values, substitute non-numeric values and normalize the input values. 
- Data was split into training and testing data with 80/20 split.  

## 2.3. Training

- In order to expand our learning, we built a **Custom Random Search** funtion which performs all the functionalities as ScikitLearn's ```RandomizedSearchCV``` 
- This was used was used to find the model with the best parameters. 
- Since it's a Linear Regression problem, **ReLU Activation** function was used for the output layer along with **Adam Optimizer** to optimize our ANN and use **Mean Squared Error** as our loss function and metric.
- Upon calling the random search function for **20 iterations**, the best hyperparameters found were:

    - \# of Hidden layers: 22
    - \# of neurons in each layer: [239, 350, 417, 461, 328, 787, 318, 488, 725, 646, 883, 419, 983, 29, 113, 647, 345, 207, 392, 571, 478, 528]
    - Learning rate: 0.0001
    - Activation Functions: relu

## 2.4. Testing

- The minimum MSE error on the test data is **0.3811634**
 
## 2.5. Google Colab Link

[https://drive.google.com/file/d/1S8iyXSQy7fOVvgFciRs28gr5LMe1ns_Y/view?usp=sharing](https://drive.google.com/file/d/1S8iyXSQy7fOVvgFciRs28gr5LMe1ns_Y/view?usp=sharing)

\newpage

# 3. Cotton Disease Classification using DenseNet 169

## 3.1. Dataset

- As specified in the question, [cotton dataset](https://www.kaggle.com/janmejaybhoi/cotton-disease-dataset) was used which consists of 2204 training (+ validation) samples and 106 testing samples. 
- Each image is associated with a label as specified below

    | Class | Label                 |
    | ----- | --------------------- |
    | 0     | Diseased Cotton Leaf  |
    | 1     | Diseased Cotton Plant |
    | 2     | Fresh Cotton Leaf     |
    | 3     | Fresh Cotton Plant    |
    
## 3.2. Preprocessing

- Here we apply a transformation on the images by performing the following

    1. **Resize to 256x256** and **CenterCrop to 224x224** so that every image is of the same dimension.
    2. **Normalize the R, G, B Channels** of the image to assist in training.

- To load data easily, a custom Dataset class and data loaders were defined
    
![Sample Data with Labels](https://i.imgur.com/XhK9LoJ.png)
    
## 3.3. DenseNet 169

### 3.3.1. Problems with Traditional CNNs

When CNN's become very deep, the **path** the input and the gradient travel become extremely large. This can lead to the **Vanishing gradient** problem which hinders the training of the CNN. There is hence a need to ensure **maximal information and gradient flow** in the network.

### 3.3.2. What are DenseNets?

![DenseNet 169](https://i.imgur.com/uGfxlhv.png)

- DenseNets **simplify the connectivity patterns** between the layers such as 

    - Highway Networks
    - Residual Networks and
    - Fractal Networks

- The information flow problem is rectified by connecting every layer directly with each other. (Hence the name "Dense" Net). This ensures that **each layer has direct access to the gradients from the loss function** and the original input image.

- In contrast to other CNNs, DenseNets do not draw representational power from extremely deep or wide architectures. They exploit the potential of the network through **feature reuse**. 

- Due to feature reuse, DenseNets **require fewer parameters** than a traditional CNN, and there is **no need to learn redundant feature maps**. **DenseNet layers are hence very narrow**, and they just add a small set of new feature maps.

### 3.3.3. DenseNet Architecture

![Traditional CNN](https://i.imgur.com/qDMfXSv.png)

- Traditional feed-forward neural networks obtain the input of a given layer by applying a composition of operations on the output of the previous layer. In the case of a CNN this usually includes convolution, pooling, batch-norm, activation etc. This can be represented as

    $$
    x_n = H_n(x_{n-1})
    $$

    Where $x_0$ is the original image.
    
![DenseNet](https://i.imgur.com/ZPjhjzR.png)
    
- On the other hand, each layer is receiving a “collective knowledge” from all preceding layers in a DenseNet. This is acheived by concatenating the output of all preceeding layers. This can be represented as

    $$
    x_n = H_n([x_0, x_1, ..., x_{n-1}])
    $$
    
    Where $x_0$ is the original image.

- However, this concatenation is not possible unless the different Feature Maps are of the same size. This problem is solved in DenseNets by the use of **DenseBlocks**.

#### 3.3.3.1. The DenseBlock

- DenseBlocks are the fundamental building blocks of DenseNets.
- These blocks act as self contained units and can be analogous to the layers of a regular neural network.
- The **dimensions of feature maps remain constant within a block**, but the number of filters change between them. 
- The different DenseBlocks are composed of **DenseNet Composition Layers** and are linked using **DenseNet Transition Layers**.

#### 3.3.3.2. DenseNet Composition Layer

![DenseNet Composition Layer](https://i.imgur.com/KTUbbm0.png)

- These layers form the different DenseBlocks of the DenseNet. 
- In each composition layer the follow operations are performed:

    1. Pre-Activation
    2. Batch Normalization
    3. ReLU Activation and finally
    4. 3x3 Convolution.

- These operations are done on inputs of **n\*k channels** and output feature maps of **k channels**. That is they operate on the concatenation of $x_0, x_1, ..., x_{n-1}$ to produce $x_n$.

#### 3.3.3.3. Transition Layer

![Transition Layer](https://i.imgur.com/VqswXHd.png)

- These layers are used to connect two Dense Blocks.
- Typically, **1×1 Convolution** followed by **2×2 Average Pooling** is used as the transition layers between two contiguous dense blocks.
- Since feature map sizes are the same within the dense block, they can be concatenated together easily.
- At the end of the last dense block, a **Global Average Pooling** is performed and then a softmax classifier is attached.

### 3.3.4. Advantages of DenseNet

#### 3.3.4.1. Strong Gradient Flow

![Gradient Flow in DenseNets](https://i.imgur.com/049T2Sf.png)

- Since all the layers are connected to eachother, the error signal and the gradient is easily propagated to all the layers **direclty**. 
- This results in an **implicit deep supervision** as all layers get direct supervision from the final classification / output layer.

#### 3.3.4.2. Parameter and Computational Efficiency

- Since each layer's input is the **concatenation** of the outputs of the previous layers, the dimension of the channel is increasing at ever layer. 
- If each layer produces $k$ additional feature maps, then,

    $$
    k_l = k_0 + k * (l - 1)
    $$
    
- This hyperparameter $k$ is known as the **Growth Rate** and it regulates the amount of information added to the network.
- Thus, the number of parameters in DenseNet is proportional to $l \times k \times k$. This is **much smaller** than a traditional CNN or even a ResNet.

#### 3.3.4.3. More Diversified Features

- Since each layer in DenseNet receives all preceding layers as input, more diversified features and tends to have richer patterns.

#### 3.3.4.4. Maintains Low Complexity Features

- Since each layer in DenseNet receives all preceding layers as input, the classifier uses features of all complexity levels. 
- DenseNets tend to give smoother decision boundaries. 
- This allows DenseNets to perform well even when training data is insufficient.

### 3.3.5. Extensions to DenseNet

#### 3.3.5.1. DenseNet-B (Bottleneck Layer)

![Bottleneck Layer in DenseNet-B](https://i.imgur.com/f4j82r3.png)

- In this version of DenseNet, the **DenseNet Composition Layer** is split into two stages
    1. BN-ReLU-1×1 Conv (Bottleneck Layer)
    2. BN-ReLU-3×3 Conv (Regular DenseNet)
- This helps to reduce the model complexity and size.

#### 3.3.5.2. DenseNet-C (Compression)

- If a dense block contains m feature-maps, the transition layer generates $\theta \times m$ output feature maps, where $0 < \theta \leq 1$ is referred to as the compression factor.
- If $\theta = 1$, the number of feature-maps across transition layers remains unchanged. 
- DenseNet with $\theta < 1$ is referred as DenseNet-C. Where there exists a **compression factor.**

#### 3.3.5.3 DenseNet-BC

- When both bottleneck and transition layers with $\theta < 1$ are used, the model is referred to as DenseNet-BC.

## 3.4. Training

- Transfer learning was used to solve this problem. The choice of pretrained model was **DenseNet169** with the added final layer using **Softmax** Activation for Classification.
- **Cross Entropy Loss** was used with **Stochastic Gradient Descent (SGD)** as the optimizer.
- The following hyperparameters were found to produce the best results:

    | Column 1      | Column 2 |
    | ------------- | -------- |
    | Epochs        | 5        |
    | Batch size    | 16       |
    | Momentum      | 0.9      |
    | Learning Rate | 0.001    |
    
- The decrease in loss function with each epoch is as follows:

    | Epoch | Loss  |
    | ----- |:-----:|
    | 1     | 1.391 |
    | 2     | 0.961 |
    | 3     | 0.494 |
    | 4     | 0.341 |
    | 5     | 0.246 |

## 3.5. Testing

- The model was tested on 106 test images and was found to have an accuracy of **100%**.

![Sample Predictions](https://i.imgur.com/nMLfDY8.png)
