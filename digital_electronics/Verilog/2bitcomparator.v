module twobitcomp(
  input [1:0] a,
  input [1:0] b,
  output c
  );

  assign c = a[1] & ~b[1] | (a[1] & b[1] | ~a[1] & ~b[1]) & a[0] & ~b[0];

endmodule

module test;

  reg [1:0] a;
  reg [1:0] b;
  wire c;

  twobitcomp t(
    .a(a),
    .b(b),
    .c(c)
  );

  initial
    begin
      $dumpfile("abc.vcd");
      $dumpvars(0, test);
      a = 1;
      b = 0;

      #10 a = 1;
      b = 1;

      #10 a = 3;
      b = 2;

      #10 a = 1;
      b = 3;
    end

  initial
    begin
      $monitor("t = %2d, a = %2d, b = %2d, c = %d", $time, a, b, c);
    end

endmodule
