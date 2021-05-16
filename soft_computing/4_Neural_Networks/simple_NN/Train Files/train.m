clear ; close all; clc
input_layer_size  = 400; 
num_labels = 10;        
fprintf('Loading and Visualizing Data ...\n')
load('data.mat'); 
m = size(X, 1);
rand_indices = randperm(m);
sel = X(rand_indices(1:100), :);
displayData(sel);
fprintf('Program paused. Press enter to continue.\n');
pause;
fprintf('\nTesting lrCostFunction()');
theta_t = [-2; -1; 1; 2];
X_t = [ones(5,1) reshape(1:15,5,3)/10];
y_t = ([1;0;1;0;1] >= 0.5);
lambda_t = 3; 
[J grad] = lrCostFunction(theta_t, X_t, y_t, lambda_t);
fprintf('\nCost: %f\n', J);
fprintf('Gradients:\n');
fprintf(' %f \n', grad);
fprintf('Program paused. Press enter to continue.\n');
pause;
lambda = 0.1;
[all_theta] = oneVsAll(X, y, num_labels, lambda);
fprintf('Program paused. Press enter to continue.\n');
pause;
pred = predictOneVsAll(all_theta, X);
fprintf('\nTraining Set Accuracy: %f\n', mean(double(pred == y)) * 100);

