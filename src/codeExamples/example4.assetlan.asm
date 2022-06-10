li $b 0 
subi $sp $sp 1
//Start codgen for  calling fun main
push $fp 
li $a0 1 
push $a0 
mv $fp $al 
push $al
 jal label10

print $b
halt
label8: //Label of function f
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
beq $a0 $a1 label12
 // START THEN BRANCH IF STATEMENT 
mv $fp $al 
lw $a0 3($al) 
li $a1 0
sw $a1 3($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
lw $a1 0($sp)
pop
add $a0 $a0 $a1
mv $fp $al 
lw $al 0($al) 
sw $a0 -1($al) 
b label13 
label12: 
  // START ELSE BRANCH IF STATEMENT 
mv $fp $al 
lw $a0 3($al) 
li $a1 0
sw $a1 3($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
lw $a1 0($sp)
pop
add $a0 $a0 $a1
mv $fp $al 
lw $al 0($al) 
sw $a0 -1($al) 
mv $fp $al 
lw $a0 2($al) 
li $a1 0
sw $a1 2($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
lw $a1 0($sp)
pop
add $a0 $a0 $a1
mv $fp $al 
lw $al 0($al) 
sw $a0 -1($al) 
label13: //END IF 
label9: //End Label of function f
lw $ra 0($sp)
pop 
addi $sp $sp 3 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION f
label10: //Label of function main
mv $sp $fp
push $ra
push $fp 
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
push $a0 
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
push $a0 
li $a0 0 
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label8

mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
li $a1 0
sw $a1 -1($al)
add $b $b $a0
label11: //End Label of function main
lw $ra 0($sp)
pop 
addi $sp $sp 1 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION main
