// -------------------------
// Guia_0704 - LU OR/NOR e XOR/XNOR
// Nome: Arthur Mendes Lima
// Matrícula: 885134
// -------------------------
module LU_ORNOR_XORXNOR (
    input a, b,
    input [1:0] sel,   // 00=NOR, 01=OR, 10=XOR, 11=XNOR
    output out
);
    wire out_nor, out_or, out_xor, out_xnor;
    nor NOR1 (out_nor, a, b);
    or OR1 (out_or, a, b);
    xor XOR1 (out_xor, a, b);
    xnor XNOR1 (out_xnor, a, b);

    wire sel0, sel1, sel00, sel01, sel10, sel11;
    not NOT1(sel0, sel[0]);
    not NOT2(sel1, sel[1]);

    and AND00(sel00, out_nor, sel1, sel0);
    and AND01(sel01, out_or, sel1, sel[0]);
    and AND10(sel10, out_xor, sel[1], sel0);
    and AND11(sel11, out_xnor, sel[1], sel[0]);
    or OR_FINAL(out, sel00, sel01, sel10, sel11);
endmodule

module test_Guia_0704;
    reg x, y;
    reg [1:0] s;
    wire out;
    LU_ORNOR_XORXNOR U1(x,y,s,out);

    initial begin
        $display("x y sel | out");
        x=0; y=0; s=2'b00; #1 $display("%b %b %b | %b",x,y,s,out);
        x=0; y=1; s=2'b01; #1 $display("%b %b %b | %b",x,y,s,out);
        x=1; y=0; s=2'b10; #1 $display("%b %b %b | %b",x,y,s,out);
        x=1; y=1; s=2'b11; #1 $display("%b %b %b | %b",x,y,s,out);
    end
endmodule
