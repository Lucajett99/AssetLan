li $b 0 
li $a0 0 
sw $a0 -2($fp) 
label0:
mv $fp $sp
push $ra
lw $al 0($fp) 
lw $al 0($al) 
sw $a0 -3($al) 
li $a1 0
sw $a1 -3($al)
add $b $b $a0
lw $ra 0($sp)
pop 
lw $fp 0($sp)
pop 
jr $ra 
label1:
mv $fp $sp
push $ra
lw $al 0($fp)
lw $al1
lw $al 0($fp) 
lw $al 0($al) 
sw $a0 -1($al) 
sw $a0 0($al)
lw $al 0($fp)
lw $al 0($al) 
lw $al0

push $a0 
lw $al 0($fp)
lw $al1

lw $a1 0($sp)
add $a0 $a1 $a0 
pop 
lw $al 0($fp) 
lw $al 0($al) 
sw $a0 -2($al) 
sw $a0 0($al)
lw $al 0($fp) 
sw $a0 -3($al) 
li $a1 0
sw $a1 -3($al)
push $a0 
lw $al 0($fp) 
lw $al 0($al) 
sw $a0 -3($al) 
lw $a1 0($sp) 
add $a0 $a0 $a1 
sw $a0-3($al)
push $fp 
lw $al 0($fp) 
lw $al 0($al) 
push $al
jal label0
print $b 
lw $ra 0($sp)
pop 
pop 
pop 
lw $fp 0($sp)
pop 
jr $ra 
push $fp 
li $a0 2 
push $a0 
li $a0 1 
push $a0 
lw $al 0($fp) 
push $al
jal label1

print $b
