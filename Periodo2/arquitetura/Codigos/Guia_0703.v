// -------------------------
// Guia_0703 - LU AND/NAND e OR/NOR
// Nome: Arthur Mendes Lima
// Matrícula: 885134
// -------------------------
module LU_ANDNAND_ORNOR (
    input a, b,
    input sel_port,   // 0=NAND/NOR, 1=AND/OR
    input sel_group,  // 0=AND/NAND, 1=OR/NOR
    output out
);
    // Grupo AND/NAND
    wire out_and, out_nand;
    and AND1 (out_and, a, b);
    nand NAND1 (out_nand, a, b);
    wire not_sel_port, andnand_sel;
    not NOT1 (not_sel_port, sel_port);
    wire and_sel, nand_sel;
    and AND2 (and_sel, out_and, sel_port);
    and AND3 (nand_sel, out_nand, not_sel_port);
    or OR1 (andnand_sel, and_sel, nand_sel);

    // Grupo OR/NOR
    wire out_or, out_nor;
    or OR2 (out_or, a, b);
    nor NOR2 (out_nor, a, b);
    wire or_sel, nor_sel, ornor_sel;
    and AND4 (or_sel, out_or, sel_port);
    and AND5 (nor_sel, out_nor, not_sel_port);
    or OR3 (ornor_sel, or_sel, nor_sel);

    // Seleção final de grupo
    wire not_sel_group, group0, group1;
    not NOT2 (not_sel_group, sel_group);
    and AND6 (group0, andnand_sel, not_sel_group);
    and AND7 (group1, ornor_sel, sel_group);
    or OR4 (out, group0, group1);
endmodule

module test_Guia_0703;
    reg x, y, s_port, s_group;
    wire out;
    LU_ANDNAND_ORNOR U1(x,y,s_port,s_group,out);

    initial begin
        $display("x y sel_port sel_group | out");
        x=0; y=0; s_port=0; s_group=0; #1 $display("%b %b %b %b | %b",x,y,s_port,s_group,out);
        x=1; y=0; s_port=1; s_group=0; #1 $display("%b %b %b %b | %b",x,y,s_port,s_group,out);
        x=1; y=1; s_port=0; s_group=1; #1 $display("%b %b %b %b | %b",x,y,s_port,s_group,out);
        x=0; y=1; s_port=1; s_group=1; #1 $display("%b %b %b %b | %b",x,y,s_port,s_group,out);
    end
endmodule
