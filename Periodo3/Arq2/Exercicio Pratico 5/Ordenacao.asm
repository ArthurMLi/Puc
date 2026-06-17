.data 
x1: .word 13
x2: .word 7
x3: .word 18
x4: .word 12
x5: .word 15
x6: .word 83
x7: .word 2

# s0 é endereço das variaveis
# $s1 = n tamanho array
# $s2 = i
# $s3 = menor
# $s4 = temp
# $s5 = j


.text
#s0 igual a endereco [0]
addi $s0,$zero,0x1001
sll $s0,$s0,16
# n = 7 
addi $s1, $zero,7
#t2 = n * 4
#t4 = n-4
sll $t2,$s1,2
add $t2,$t2,$s0
addi $t4,$t2,-4 
addi $s2,$s0,0
addi $s5,$s0,0
loop1:

#menor = primeiro ainda nao ordenado
#t1 = endereco do menor
#temp = segundo ainda nao ordenado
lw $s3,0($s2)
add $t1,$s2,$zero

#j = i + 1
beq $s2,$t4,fim
addi $s5,$s2,4
loop:
#$t3 = v[j]
lw $t3,0($s5)
#t0 = if(v[j]>v[menor])
slt $t0,$t3,$s3
beq $t0,$zero,continue
#endereco do menor = endereco de j
add $t1,$s5,$zero
add $s3,$t3,$zero
continue:
addi $s5, $s5,4
bne $t2,$s5,loop
#cabou o loop
#paramntros = endereco da memoria e da posicao
add $a0,$zero,$t1
add $a1,$zero,$s2
#salvar temporarios na pilha
addi $sp,$sp,-16
sw $t0,12($sp)
sw $t1,8($sp)
sw $t2,4($sp)
sw $t3,0($sp)
jal swap
#voltar temporarios
lw $t0,12($sp)
lw $t1,8($sp)
lw $t2,4($sp)
lw $t3,0($sp)
addi $sp,$sp,16
addi $s2,$s2,4
bne $s2,$t2,loop1
j fim
swap:
#epilogo
addi $sp,$sp,-4
sw $s0,0($sp)

lw $t0,0($a0)
add $s0,$t0,$zero
lw $t1,0($a1)
sw $t1,0($a0)
sw $t0,0($a1)

#prologo
lw $s0,0($sp)
addi $sp,$sp,4
jr $ra

fim:
