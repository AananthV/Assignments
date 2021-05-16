data = table2array(databanknoteauthentication);

inputs = data(:,1:4).';
targets = data(:,5).';
num_inputs = length(inputs);

input_size = 4;
output_size = 1;
hidden_size = [4, 2];

num_wb = 0;
t = input_size;
for h = hidden_size
    num_wb = num_wb + (t * h + h);
    t = h;
end
num_wb = num_wb + (t * output_size + output_size);

net = feedforwardnet([4, 2]);

net = configure(net, inputs, targets);

fitness_func = @(x) mse_calc(x, net, inputs, targets);

ga_opts = optimoptions('ga', 'TolFun', 1e-2, 'display','iter');

[x, fval] = ga(fitness_func, num_wb, ga_opts);

net = setwb(net, x);

out = net(inputs);

mse_error = sum((out - targets).^2) / num_inputs

accuracy = (sum((out > 0.5) == targets) / num_inputs) * 100


