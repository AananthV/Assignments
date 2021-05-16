module mux(
  input d0, d1, d2, d3, s0, s1,
  output out
);

  wire out;

  assign out = s0 ? (s1 ? d3 : d1) : (s1 ? d2 : d0);

endmodule

module test;

  reg d0, d1, d2, d3, s0, s1;

  wire out;

  mux uut(d0, d1, d2, d3, s0, s1, out);

  initial
    begin
      $dumpfile("mux.vcd");
      $dumpvars(1, test);

      d0 = 0; d1 = 1; d2 = 1; d3 = 0;
      s0 = 0; s1 = 0;
      #5;
      s0 = 1;
      #5;
      s0 = 0; s1 = 1;
      #5;
      s0 = 1;
      #5;
      $finish;
    end
endmodule
