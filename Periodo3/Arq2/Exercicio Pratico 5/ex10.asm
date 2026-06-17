#enunciado prescede isso
.data
x: .word 5
z: .word 7
y: .word 0

# minha soluçãos
.text	
#x = s0
#z = s1
#y = s2
ori $t0,$t0,0x1001
sll $t0,$t0,16
lw $s0,0($t0)
lw $s1,4($t0)

add $t1,$s0,$s0 # 2x
sll $t1,$t1,6 # 128x
sub $t1,$t1,$s0 # 127x

add $t2,$s1,$s1 # 2z
sll $t2,$t2,5 # 64z
add $t2,$t2,$s1 # 65z

sub $t3,$t1,$t2 # 127x - 65z
addi $s3,$t3,1 # 127x - 65z + 1

sw $s3,8($t0)