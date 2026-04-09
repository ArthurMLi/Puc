/*
Guia_0301.v
Arthur Mendes - 885134
*/
module Guia_0301;
  // define data
  reg [7:0] a = 8'b0011_0010; 
  reg [6:0] b = 7'b010_1101; 
  reg [5:0] c = 6'b101_011;   

  reg [7:0] d = 8'b0; // C2(a)
  reg [6:0] e = 7'b0; // C2(b)
  reg [5:0] f = 6'b0; // C2(c)
  // actions 
  initial begin : main
    $display("Guia_0301 - Tests");

    d = ~a + 1'b1;
    $display("a = %8b -> C1(a) = %8b -> C2(a) = %8b", a, ~a, d);

    e = ~b + 1'b1;
    $display("b = %7b -> C1(b) = %7b -> C2(b) = %7b", b, ~b, e);

    f = ~c + 1'b1;
    $display("c = %6b -> C1(c) = %6b -> C2(c) = %6b", c, ~c, f);
  end
endmodule