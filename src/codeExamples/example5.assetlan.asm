li $b 0 
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
push $al
 jal label18

print $b
halt
label14: //Label of function g
mv $sp $fp
push $ra
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
add $b $b $a0
li $a0 1 
lw $fp 0($fp)
lw $fp 0($fp) //Load old $fp pushed 
b label15 
label15: //End Label of function g
lw $ra 0($sp)
pop 
addi $sp $sp 1 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION g
label16: //Label of function f
mv $sp $fp
push $ra
push $fp 
mv $fp $al 
lw $a0 2($al) 
li $a1 0
sw $a1 2($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label14

li $a1 0
beq $a0 $a1 label20
 // START THEN BRANCH IF STATEMENT 
push $fp 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
li $a1 0
sw $a1 -1($al)
push $a0 
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
push $a0 
mv $fp $al 
lw $a0 3($al) 
li $a1 0
sw $a1 3($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label16

push $fp 
mv $fp $al 
lw $a0 3($al) 
li $a1 0
sw $a1 3($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label14

push $fp 
mv $fp $al 
lw $a0 2($al) 
li $a1 0
sw $a1 2($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label14

push $fp 
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label14

b label21 
label20: 
  // START ELSE BRANCH IF STATEMENT 
push $fp 
mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
li $a1 0
sw $a1 -1($al)
push $a0 
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
push $a0 
mv $fp $al 
lw $a0 3($al) 
li $a1 0
sw $a1 3($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label16

push $fp 
mv $fp $al 
lw $a0 3($al) 
li $a1 0
sw $a1 3($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label14

push $fp 
mv $fp $al 
lw $a0 2($al) 
li $a1 0
sw $a1 2($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label14

push $fp 
mv $fp $al 
lw $a0 1($al) 
li $a1 0
sw $a1 1($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
push $al
jal label14

label21: //END IF 
li $a0 1 
lw $fp 0($fp)
lw $fp 0($fp) //Load old $fp pushed 
b label17 
label17: //End Label of function f
lw $ra 0($sp)
pop 
addi $sp $sp 3 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION f
label18: //Label of function main
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
push $al
jal label16

mv $fp $al 
lw $al 0($al) 
lw $a0 -1($al) 
li $a1 0
sw $a1 -1($al)
add $b $b $a0
label19: //End Label of function main
lw $ra 0($sp)
pop 
addi $sp $sp 3 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION main
