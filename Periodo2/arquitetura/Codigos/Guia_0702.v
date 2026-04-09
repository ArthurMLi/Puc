// -------------------------
// Guia_0702 - LU OR/NOR
// Nome: Arthur Mendes Lima
// Matrícula: 885134
// -------------------------
module LU_OR_NOR (
    input a, b,
    input sel,     // 0 = NOR, 1 = OR
    output out
);
    wire out_or, out_nor;
    or OR1 (out_or, a, b);
    nor NOR1 (out_nor, a, b);

    wire not_sel;
    not NOT1 (not_sel, sel);
    wire or_sel, nor_sel;
    and AND1 (or_sel, out_or, sel);
    and AND2 (nor_sel, out_nor, not_sel);
    or OR2 (out, or_sel, nor_sel);
endmodule

module test_Guia_0702;
    reg x, y, s;
    wire out;
    LU_OR_NOR U1 (x, y, s, out);

    initial begin
        $display("x y sel | out");
        x=0; y=0; s=0; #1 $display("%b %b %b | %b",x,y,s,out);
        x=0; y=1; s=1; #1 $display("%b %b %b | %b",x,y,s,out);
        x=1; y=0; s=0; #1 $display("%b %b %b | %b",x,y,s,out);
        x=1; y=1; s=1; #1 $display("%b %b %b | %b",x,y,s,out);
    end
endmodule
