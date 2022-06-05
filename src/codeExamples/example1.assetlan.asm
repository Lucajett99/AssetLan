li $b 0 
subi $sp $sp 1
subi $sp $sp 1
//Start codgen for  calling fun main
push $fp 
li $a0 10 
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

push $a0 
li $a0 0 

lw $a1 0($sp)
gt $a0 $a1 $a0 
pop 
li $a1 0
push $a1
bc $a0 label2
 // START THEN BRANCH IF STATEMENT 
mv $fp $al 
lw $a0 1($al) 
print $a0 
b label3 
label2: 
  // START ELSE BRANCH IF STATEMENT 
li $a0 150 
print $a0 
label3: 
 //END IF
 pop 
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
