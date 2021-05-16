module test;

  reg a, b, c;

  initial
    $monitor($time, a, b, c);

  initial
    begin
      a = 0;
      b = 0;
      c = 0;
      fork
        #5 a = 1;
        #10 b = 1;
      join
      #5; c = 1;
    end
endmodule
