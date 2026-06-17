ori $8,$0,0x1234
sll $8,$8,16
ori $8,$8,0x5678

or $9,$8,$zero
srl $9,$9,24


or $10,$8,$zero
srl $10,$10,16
andi $10,$10,0xff	

or $11,$8,$zero
srl $11,$11,8
andi $11,$11,0xff

or $12,$8,$zero
andi $12,$12,0xff