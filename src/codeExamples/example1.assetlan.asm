li $b 0 
li $a0 5 
push $a0 
//Start codgen for  calling fun main
push $fp 
li $a0 3 
push $a0 
li $a0 2 
push $a0 
mv $fp $al 
push $al
 jal label0

print $b
halt
label0: //Label of function main
mv $sp $fp
push $ra
mv $fp $al 
lw $a0 1($al) 
print $a0 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
print $a0 
mv $fp $al 
lw $a0 2($al) 
print $a0 
mv $fp $al 
lw $a0 2($al) 
li $a1 0
sw $a1 2($al)
add $b $b $a0
mv $fp $al 
lw $a0 2($al) 
print $a0 
label1: //End Label of function main
lw $ra 0($sp)
pop 
addi $sp $sp 2 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION main
