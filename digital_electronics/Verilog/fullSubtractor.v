module fullSubtractor(input a, input b, input bin, output d, output bout);

  wire d, bout;

  assign d = a ^ b ^ bin;
  assign bout = ~a & b | ~(a ^ b) & bin;
endmodule

module test;
  reg a, b, bin;
  wire bout, d;
  integer i, j, k;

  fullSubtractor uut(a, b, bin, d, bout);

  initial
    $monitor("%d - %d - %d = %d %d", a, b, bin, bout, d);

  initial
    begin
      for(i = 0; i < 2; i++)
        begin
          for(j = 0; j < 2; j++)
            begin
              for(k = 0; k < 2; k++)
                begin
                  a = i;
                  b = j;
                  bin = k;
                  #5;
                end
            end
        end
    end
endmodule
