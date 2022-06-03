li $b 0 
subi $sp $sp 1
push $fp 
li $a0 1 
push $a0 
mv $fp $al 
push $al
 jal label0

label0: //Label of function main
mv $sp $fp
push $ra
lw $al 0($fp)
lw $a0 -2($al)
print $a0 
mv $fp $al 
lw $a0 -2($al) 
li $a1 0
sw $a1 -2($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
lw $a1 0($fp)
add $a0 $a0 $a1
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
li $a1 0
sw $a1 -1($al)
add $b $b $a0
label1: //End Label of function main
lw $ra 0($sp)
pop 
addi $sp $sp 1 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION main
print $b
halt