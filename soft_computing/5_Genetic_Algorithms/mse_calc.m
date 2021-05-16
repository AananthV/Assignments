function mse_calc = mse_test(x, net, inputs, targets)
    net = setwb(net, x');

    y = net(inputs);

    mse_calc = sum((y-targets).^2) / length(y);
end