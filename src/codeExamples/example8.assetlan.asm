li $b 0 
li $a0 1 
push $a0 
//Start codgen for  calling fun f
push $fp 
li $a0 10 
push $a0 
mv $fp $al 
 jal label0

print $b
halt
label0: //Label of function f

push $al
mv $sp $fp
push $ra
mv $fp $al 
lw $a0 1($al) 

push $a0 
li $a0 0 

lw $a1 0($sp)
eq $a0 $a1 $a0 
pop 
li $a1 0
beq $a0 $a1 label2
 // START THEN BRANCH IF STATEMENT 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
print $a0 
b label3 
label2: 
  // START ELSE BRANCH IF STATEMENT 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 

push $a0 
mv $fp $al 
lw $a0 1($al) 

lw $a1 0($sp)
mult $a0 $a1 $a0 
pop 
mv $fp $al 
lw $al 0($al) 
sw $a0 -1($al) 
push $fp 
mv $fp $al 
lw $a0 1($al) 

push $a0 
li $a0 1 

lw $a1 0($sp)
sub $a0 $a1 $a0 
pop 
push $a0 
mv $fp $al 
lw $al 0($al) 
jal label0

label3: //END IF 
label1: //End Label of function f
lw $ra 0($sp)
pop 
addi $sp $sp 1 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION f
