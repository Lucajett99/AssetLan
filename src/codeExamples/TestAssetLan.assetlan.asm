li $b 0 
subi $sp $sp 1 
subi $sp $sp 1
//Start codgen for  calling fun initcall
push $fp 
mv $fp $al 
 jal label0

print $b
halt
label0: //Label of function initcall

push $al
mv $sp $fp
push $ra
mv $fp $al 
lw $al 0($al) 
lw $a0 -2($al) 
li $a1 0
sw $a1 -2($al)
add $b $b $a0
label1: //End Label of function initcall
lw $ra 0($sp)
pop 
addi $sp $sp 0 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION initcall
