li $b 0 
subi $sp $sp 1
//starting codgen for fun main
push $fp 
li $a0 2 
push $a0 
li $a0 3 
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
lw $a0 -3($al) 
print $a0 
mv $fp $al 
lw $a0 -1($al) 
print $a0 
mv $fp $al 
lw $a0 -1($al) 

push $a0 
li $a0 1 

lw $a1 0($sp)
gt $a0 $a1 $a0 
pop 
li $a1 0push $a1bc $a0 label2
 // START THEN BRANCH IF STATEMENT 
mv $fp $al 
lw $a0 -3($al) 
li $a1 0
sw $a1 -3($al)
add $b $b $a0
b label3 
label2: 
  // START ELSE BRANCH IF STATEMENT 
label3: 
 //END IF
 pop 
mv $fp $al 
lw $a0 -1($al) 

push $a0 
li $a0 1 

lw $a1 0($sp)
sub $a0 $a1 $a0 
pop 
mv $fp $al 
sw $a0 -1($al) 
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
