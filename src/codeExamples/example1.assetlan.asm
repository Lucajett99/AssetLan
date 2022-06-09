li $b 0 
//Start codgen for  calling fun main
push $fp 
li $a0 2 
push $a0 
mv $fp $al 
 jal label0

print $b
halt
label0: //Label of function main

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
li $a0 5 
print $a0 
lw $fp 0($fp)
lw $fp 0($fp) //Load old $fp pushed 
b label1 
b label3 
label2: 
  // START ELSE BRANCH IF STATEMENT 
label3: //END IF 
li $a0 10 
print $a0 
lw $fp 0($fp)
lw $fp 0($fp) //Load old $fp pushed 
b label1 
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
