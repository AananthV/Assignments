module AND(
  input a,
  input b,
  output c
  );

  assign c = a & b;

endmodule

module test;
  reg x;
  reg y;
  wire z;

  AND uut(
    .a(x),
    .b(y),
    .c(z)
  );

  initial begin
    x = 0;
    y = 0;

    #20 x = 1;
    y = 0;

    #20 x = 0;
    y = 1;

    #20 y = 1;
    x = 1;

    #20 x = 0;
    y = 0;

  end

  initial begin
    $dumpfile("my_dumpfile.vcd");
    $dumpvars(0, test);
  end
endmodule
