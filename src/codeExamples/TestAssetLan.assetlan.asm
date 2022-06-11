li $b 0 
subi $sp $sp 1
//Start codgen for  calling fun initcall
push $fp 
li $a0 5 
push $a0 
mv $fp $al 
 jal label2

print $b
halt
label0: //Label of function call

push $al
mv $sp $fp
push $ra
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
add $b $b $a0
li $a0 5 
lw $fp 0($fp)
lw $fp 0($fp) //Load old $fp pushed 
b label1 
label1: //End Label of function call
lw $ra 0($sp)
pop 
addi $sp $sp 1 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION call
label2: //Label of function initcall

push $al
mv $sp $fp
push $ra
push $fp 
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
jal label0

lw $fp 0($fp)
lw $fp 0($fp) //Load old $fp pushed 
b label3 
label3: //End Label of function initcall
lw $ra 0($sp)
pop 
addi $sp $sp 1 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION initcall
