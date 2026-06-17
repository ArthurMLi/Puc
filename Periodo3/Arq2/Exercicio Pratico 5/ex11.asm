.data
x: .word 100000
z: .word 200000
y: .word 0 # esse valor deverá ser sobrescrito após a execução do programa


.text
addi $t0,$t0,0x1001
sll $t0,$t0,16
lw $s0,0($t0)
lw $s1,4($t0)

subi $t1,$s0,$s1

addi $t2,$t2,30000
sll $t2,$t2, 

