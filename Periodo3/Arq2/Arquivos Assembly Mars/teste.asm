
# a = 10
# b = -1
# a = 4 * a + 1
# c = a a + b 

addi $s0,$zero,10 # a = 10
addi $s1,$zero,-1 # b = -1
add $t0,$s0,$s0 # t0 = a + a
add $t0,$t0,$t0	# t0 = t0 + t0
addi $s0,$t0,1 # a = t0 + 1
add $s2,$s0,$s1 # c = a + b

# a = 0x10
# b = 0x1ABC
# c = a a + b 

addi $s3,$zero,0x10 # a = 0x10
addi $s4,$zero,0x1ABC # b = 0x1ABC //  6844
add $s5,$s3,$s4 # c = a + b





