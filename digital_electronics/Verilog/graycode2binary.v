module b2g(
  input [3:0] g,
  output [3:0] b
);

  wire [3:0] g;

  assign b[3] = g[3];
  assign b[2] = g[2] ^ g[3];
  assign b[1] = g[1] ^ g[2] ^ g[3];
  assign b[0] = g[0] ^ g[1] ^ g[2] ^ g[3];

endmodule

module test;

  wire [3:0] b;
  reg [3:0] g;
  integer i;

  b2g uut(g, b);

  initial
    $monitor("G = %b, B = %b", g, b);

  initial
    begin
      for(i = 0; i < 16; i++)
      begin
        g = i;
        #5;
      end
    end
endmodule
