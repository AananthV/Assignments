module b2g(
  input [3:0] b,
  output [3:0] g
);

  wire [3:0] g;

  assign g[3] = b[3];
  assign g[2] = b[2] ^ b[3];
  assign g[1] = b[1] ^ b[2];
  assign g[0] = b[0] ^ b[1];

endmodule

module test;

  reg [3:0] b;
  wire [3:0] g;
  integer i;

  b2g uut(b, g);

  initial
    $monitor("B = %b, G = %b", b, g);

  initial
    begin
      for(i = 0; i < 16; i++)
      begin
        b = i;
        #5;
      end
    end
endmodule
