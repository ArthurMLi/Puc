#enunciado prescede isso
.data
x1: .word 15
x2: .word 25
x3: .word 13
x4: .word 17
soma: .word -1
# minha soluçãos
.text	
#x1 = s0
#x2 = s1
#x3 = s2
#x4 = s3
#soma = s4
ori $t0,$t0,0x1001
sll $t0,$t0,16
lw $s0,0($t0)
lw $s1,4($t0)
lw $s2,8($t0)
lw $s3,12($t0)
add $t1,$s0,$s1
add $t1,$t1,$s2
add $s4,$t1,$s3

sw $s4,16($t0)




