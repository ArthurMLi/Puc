// -------------------------
// Guia_0701 - LU AND/NAND
// Nome: Arthur Mendes Lima
// Matrícula: 885134
// -------------------------
module LU_AND_NAND (
    input a, b,
    input sel,         // 0 = NAND, 1 = AND
    output out,
    output out_and,
    output out_nand
);
    // Saídas paralelas
    and AND1 (out_and, a, b);
    nand NAND1 (out_nand, a, b);

    // Saída selecionável
    wire not_sel;
    not NOT1 (not_sel, sel);
    wire and_sel, nand_sel;
    and AND2 (and_sel, out_and, sel);
    and AND3 (nand_sel, out_nand, not_sel);
    or OR1 (out, and_sel, nand_sel);
endmodule

module test_Guia_0701;
    reg x, y, s;
    wire out, out_and, out_nand;
    LU_AND_NAND U1 (x, y, s, out, out_and, out_nand);

    initial begin
        $display("x y sel | out out_and out_nand");
        x=0; y=0; s=0; #1 $display("%b %b %b | %b %b %b",x,y,s,out,out_and,out_nand);
        x=0; y=1; s=0; #1 $display("%b %b %b | %b %b %b",x,y,s,out,out_and,out_nand);
        x=1; y=0; s=1; #1 $display("%b %b %b | %b %b %b",x,y,s,out,out_and,out_nand);
        x=1; y=1; s=1; #1 $display("%b %b %b | %b %b %b",x,y,s,out,out_and,out_nand);
    end
endmodule
