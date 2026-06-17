# estudo para array
#store na memoria do numeros 1 ate 8 e load deles nos registradores 
addi $t0,$zero,1
addi $s0,$zero,0x1001
sll $s0,$s0,16
sw $t0,0($s0)

addi $t0,$zero,2
sw $t0,4($s0)

addi $t0,$zero,3
sw $t0,8($s0)

addi $t0,$zero,4
sw $t0,12($s0)

addi $t0,$zero,5
sw $t0,16($s0)

addi $t0,$zero,6
sw $t0,20($s0)

addi $t0,$zero,7
sw $t0,24($s0)

addi $t0,$zero,8
sw $t0,28($s0)

lw $t0,0($s0)

lw $s1,4($s0)

lw $s2,8($s0)

lw $s3,12($s0)

lw $s4,16($s0)

lw $s5,20($s0)

lw $s6,24($s0)

lw $s7,28($s0)