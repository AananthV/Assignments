function p = predict(Theta1, Theta2, X)
m = size(X, 1);
num_labels = size(Theta2, 1);
p = zeros(size(X, 1), 1);
tem1 = [ones(m, 1) X];
z_theta1 = tem1*Theta1';
tem2 = [ones(size(z_theta1, 1), 1) sigmoid(z_theta1)]
z_theta2 = tem2 * Theta2';
predict = sigmoid(z_theta2);
[p_m, i_m] = max(predict, [], 2);
p = i_m;
end
