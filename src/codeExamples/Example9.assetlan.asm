li $b 0 
subi $sp $sp 1 
subi $sp $sp 1
//Start codgen for  calling fun main
push $fp 
li $a0 1 
push $a0 
li $a0 2 
push $a0 
li $a0 3 
push $a0 
mv $fp $al 
 jal label2

print $b
halt
label0: //Label of function f
subi $sp $sp 1 

push $al
mv $sp $fp
push $ra
li $a0 0 
mv $fp $al 
sw $a0 1($al) 
mv $fp $al 
lw $a0 1($al) 

push $a0 
li $a0 0 

lw $a1 0($sp)
eq $a0 $a1 $a0 
pop 
li $a1 0
beq $a0 $a1 label4
 // START THEN BRANCH IF STATEMENT 
mv $fp $al 
lw $a0 4($al) 
li $a1 0
sw $a1 4($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
lw $a0 -2($al) 
lw $a1 0($sp)
pop
add $a0 $a0 $a1
mv $fp $al 
lw $al 0($al) 
sw $a0 -2($al) 
push $fp 
mv $fp $al 
lw $a0 3($al) 
li $a1 0
sw $a1 3($al)
push $a0 
mv $fp $al 
lw $a0 2($al) 
li $a1 0
sw $a1 2($al)
push $a0 
mv $fp $al 
lw $a0 4($al) 
li $a1 0
sw $a1 4($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
jal label0

b label5 
label4: 
  // START ELSE BRANCH IF STATEMENT 
label5: //END IF 
mv $fp $al 
lw $a0 4($al) 
li $a1 0
sw $a1 4($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
lw $a0 -2($al) 
lw $a1 0($sp)
pop
add $a0 $a0 $a1
mv $fp $al 
lw $al 0($al) 
sw $a0 -2($al) 
push $fp 
mv $fp $al 
lw $a0 3($al) 
li $a1 0
sw $a1 3($al)
push $a0 
mv $fp $al 
lw $a0 2($al) 
li $a1 0
sw $a1 2($al)
push $a0 
mv $fp $al 
lw $a0 4($al) 
li $a1 0
sw $a1 4($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
jal label0

li $a0 99 
print $a0 
label1: //End Label of function f
lw $ra 0($sp)
pop 
addi $sp $sp 3 //pop decp & pop adec
addi $sp $sp 1 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION f
label2: //Label of function main

push $al
mv $sp $fp
push $ra
push $fp 
mv $fp $al 
lw $a0 3($al) 
li $a1 0
sw $a1 3($al)
push $a0 
mv $fp $al 
lw $a0 2($al) 
li $a1 0
sw $a1 2($al)
push $a0 
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
jal label0

mv $fp $al 
lw $al 0($al) 
lw $a0 -2($al) 
li $a1 0
sw $a1 -2($al)
add $b $b $a0
label3: //End Label of function main
lw $ra 0($sp)
pop 
addi $sp $sp 3 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION main
