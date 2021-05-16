module top(clk,reset,a1,a2,a3,b1,b2,b3,c1,c2,c3,c4,c5,c6,c7,c8,c9);

 parameter data_size=8;
 input wire clk,reset;
 input wire [data_size-1:0] a1,a2,a3,b1,b2,b3;
 output wire [2*data_size:0] c1,c2,c3,c4,c5,c6,c7,c8,c9;

 wire [data_size-1:0] a12,a23,a45,a56,a78,a89,b14,b25,b36,b47,b58,b69;

 pe pe1 (.clk(clk), .reset(reset), .in_a(a1), .in_b(b1), .out_a(a12), .out_b(b14), .out_c(c1));
 pe pe2 (.clk(clk), .reset(reset), .in_a(a12), .in_b(b2), .out_a(a23), .out_b(b25), .out_c(c2));
 pe pe3 (.clk(clk), .reset(reset), .in_a(a23), .in_b(b3), .out_a(), .out_b(b36), .out_c(c3));
 pe pe4 (.clk(clk), .reset(reset), .in_a(a2), .in_b(b14), .out_a(a45), .out_b(b47), .out_c(c4));
 pe pe5 (.clk(clk), .reset(reset), .in_a(a45), .in_b(b25), .out_a(a56), .out_b(b58), .out_c(c5));
 pe pe6 (.clk(clk), .reset(reset), .in_a(a56), .in_b(b36), .out_a(), .out_b(b69), .out_c(c6));
 pe pe7 (.clk(clk), .reset(reset), .in_a(a3), .in_b(b47), .out_a(a78), .out_b(), .out_c(c7));
 pe pe8 (.clk(clk), .reset(reset), .in_a(a78), .in_b(b58), .out_a(a89), .out_b(), .out_c(c8));
 pe pe9 (.clk(clk), .reset(reset), .in_a(a89), .in_b(b69), .out_a(), .out_b(), .out_c(c9));

endmodule

module pe(clk,reset,in_a,in_b,out_a,out_b,out_c);

 parameter data_size=8;
 input wire reset,clk;
 input wire [data_size-1:0] in_a,in_b;
 output reg [2*data_size:0] out_c;
 output reg [data_size-1:0] out_a,out_b;

 always @(posedge clk)begin
    if(reset) begin
      out_a=0;
      out_b=0;
      out_c=0;
    end
    else begin
      out_c<=out_c+in_a*in_b;
      out_a<=in_a;
      out_b<=in_b;
    end
 end

endmodule

module test;

 // Test Matrices
 reg A[2:0][2:0];
 reg B[2:0][2:0];

 // Inputs
 reg clk;
 reg reset;
 reg [7:0] a1;
 reg [7:0] a2;
 reg [7:0] a3;
 reg [7:0] b1;
 reg [7:0] b2;
 reg [7:0] b3;

 // Outputs
 wire [16:0] c1;
 wire [16:0] c2;
 wire [16:0] c3;
 wire [16:0] c4;
 wire [16:0] c5;
 wire [16:0] c6;
 wire [16:0] c7;
 wire [16:0] c8;
 wire [16:0] c9;

 // Instantiate the Unit Under Test (UUT)
 top uut (
  .clk(clk),
  .reset(reset),
  .a1(a1),
  .a2(a2),
  .a3(a3),
  .b1(b1),
  .b2(b2),
  .b3(b3),
  .c1(c1),
  .c2(c2),
  .c3(c3),
  .c4(c4),
  .c5(c5),
  .c6(c6),
  .c7(c7),
  .c8(c8),
  .c9(c9)
 );

 initial begin
  // Dump Waveform
  $dumpfile("systolic_array_matrix_multiplier.vcd");
  $dumpvars(0, test);

  // Initialize Inputs
  clk = 0;
  reset = 0;
  a1 = 0;
  a2 = 0;
  a3 = 0;
  b1 = 0;
  b2 = 0;
  b3 = 0;

  // Wait 100 ns for global reset to finish
  #5 reset = 1;
  #5 reset = 0;
  #5;  a1 = 1; a2 = 0; a3 = 0; b1 = 2; b2 = 0; b3 = 0;
  #10; a1 = 2; a2 = 4; a3 = 0; b1 = 4; b2 = 1; b3 = 0;
  #10; a1 = 3; a2 = 5; a3 = 7; b1 = 6; b2 = 5; b3 = 3;
  #10; a1 = 0; a2 = 6; a3 = 8; b1 = 0; b2 = 9; b3 = 7;
  #10; a1 = 0; a2 = 0; a3 = 9; b1 = 0; b2 = 0; b3 = 8;
  #10; a1 = 0; a2 = 0; a3 = 0; b1 = 0; b2 = 0; b3 = 0;
  #100;
  $display(
    "1 2 3\t  2 4 6\t  %d %d %d\n", c1, c2, c3,
    "4 5 6  X  1 5 9  =%d %d %d\n", c4, c5, c6,
    "7 8 9\t  3 7 8\t  %d %d %d\n", c7, c8, c9
  );
  $finish;

 end

 initial begin
  forever #5 clk = ~clk;
 end

endmodule
