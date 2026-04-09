// -------------------------
// Guia_0705 - LU completa (NOT, AND, NAND, OR, NOR, XOR, XNOR)
// Nome: Arthur Mendes Lima
// Matrícula: 885134
// -------------------------
module LU_COMPLETA (
    input a, b,
    input [2:0] sel,   // 000=NOT(a), 001=AND, 010=NAND, 011=OR, 100=NOR, 101=XOR, 110=XNOR
    output out
);
    wire out_not, out_and, out_nand, out_or, out_nor, out_xor, out_xnor;
    not NOT1(out_not, a);
    and AND1(out_and, a, b);
    nand NAND1(out_nand, a, b);
    or OR1(out_or, a, b);
    nor NOR1(out_nor, a, b);
    xor XOR1(out_xor, a, b);
    xnor XNOR1(out_xnor, a, b);

    wire s0, s1, s2;
    not NOTS0(s0, sel[0]);
    not NOTS1(s1, sel[1]);
    not NOTS2(s2, sel[2]);

    wire sel000, sel001, sel010, sel011, sel100, sel101, sel110;
    and AND000(sel000, out_not, s2, s1, s0);
    and AND001(sel001, out_and, s2, s1, sel[0]);
    and AND010(sel010, out_nand, s2, sel[1], s0);
    and AND011(sel011, out_or, s2, sel[1], sel[0]);
    and AND100(sel100, out_nor, sel[2], s1, s0);
    and AND101(sel101, out_xor, sel[2], s1, sel[0]);
    and AND110(sel110, out_xnor, sel[2], sel[1], s0);
    or OR_FINAL(out, sel000, sel001, sel010, sel011, sel100, sel101, sel110);
endmodule

module test_Guia_0705;
    reg x, y;
    reg [2:0] s;
    wire out;
    LU_COMPLETA U1(x,y,s,out);

    initial begin
        $display("x y sel | out");
        x=0; y=0; s=3'b000; #1 $display("%b %b %b | %b",x,y,s,out);
        x=0; y=1; s=3'b001; #1 $display("%b %b %b | %b",x,y,s,out);
        x=1; y=0; s=3'b010; #1 $display("%b %b %b | %b",x,y,s,out);
        x=1; y=1; s=3'b011; #1 $display("%b %b %b | %b",x,y,s,out);
        x=1; y=1; s=3'b100; #1 $display("%b %b %b | %b",x,y,s,out);
        x=1; y=1; s=3'b101; #1 $display("%b %b %b | %b",x,y,s,out);
        x=1; y=1; s=3'b110; #1 $display("%b %b %b | %b",x,y,s,out);
    end
endmodule
