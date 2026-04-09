module tb_Guia_0501;
  reg a, b;
  wire s;

  Guia_0501 dut(s, a, b);

  initial begin
    $display("Ex01: (~a & ~b)");
    $display("a b | s");
    $display("---------");

    a=0; b=0; #1 $display("%b %b | %b", a, b, s);
    a=0; b=1; #1 $display("%b %b | %b", a, b, s);
    a=1; b=0; #1 $display("%b %b | %b", a, b, s);
    a=1; b=1; #1 $display("%b %b | %b", a, b, s);

    $finish;
  end
endmodule
