li $b 0 
//Start codgen for  calling fun main
push $fp 
mv $fp $al 
 jal label2

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
gt $a0 $a1 $a0 
pop 
li $a1 0
beq $a0 $a1 label4
 // START THEN BRANCH IF STATEMENT 
mv $fp $al 
lw $a0 1($al) 

push $a0 
li $a0 2 

lw $a1 0($sp)
add $a0 $a1 $a0 
pop 
lw $fp 0($fp)
lw $fp 0($fp) //Load old $fp pushed 
b label1 
b label5 
label4: 
  // START ELSE BRANCH IF STATEMENT 
li $a0 5 
lw $fp 0($fp)
lw $fp 0($fp) //Load old $fp pushed 
b label1 
label5: //END IF 
label1: //End Label of function f
lw $ra 0($sp)
pop 
addi $sp $sp 2 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION f
label2: //Label of function main
subi $sp $sp 1 

push $al
mv $sp $fp
push $ra
push $fp 
li $a0 1 
push $a0 
li $a0 0 
push $a0 
mv $fp $al 
lw $al 0($al) 
jal label0

mv $fp $al 
sw $a0 1($al) 
mv $fp $al 
lw $a0 1($al) 
print $a0 
label3: //End Label of function main
lw $ra 0($sp)
pop 
addi $sp $sp 0 //pop decp & pop adec
addi $sp $sp 1 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION main
