function [J, grad] = lrCostFunction(theta, X, y, lambda)
m = length(y);
J = 0;
grad = zeros(size(theta));
z = X * theta;
sigmo = sigmoid(z);
regularized = (lambda / (2 * m)) * sum(theta(2:end) .^ 2);
J = (1 / m) * sum(-y .* log(sigmo) - (1 - y) .* log(1 - sigmo)) + regularized;
temp = theta;
temp(1) = 0;
grad = (1 / m) * (X' * (sigmo - y) + lambda * temp);
end
