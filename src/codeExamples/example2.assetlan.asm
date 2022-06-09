li $b 0 
subi $sp $sp 1 
li $a0 0 
push $a0 
subi $sp $sp 1
//Start codgen for  calling fun f
push $fp 
li $a0 2 
push $a0 
li $a0 1 
push $a0 
mv $fp $al 
 jal label2

print $b
halt
label0: //Label of function g

push $al
mv $sp $fp
push $ra
mv $fp $al 
lw $al 0($al) 
lw $a0 -3($al) 
li $a1 0
sw $a1 -3($al)
add $b $b $a0
label1: //End Label of function g
lw $ra 0($sp)
pop 
addi $sp $sp 0 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION g
label2: //Label of function f

push $al
mv $sp $fp
push $ra
mv $fp $al 
lw $a0 2($al) 
mv $fp $al 
lw $al 0($al) 
sw $a0 -1($al) 
mv $fp $al 
lw $al 0($al) 
lw $a0 -2($al) 

push $a0 
mv $fp $al 
lw $a0 1($al) 

lw $a1 0($sp)
add $a0 $a1 $a0 
pop 
mv $fp $al 
lw $al 0($al) 
sw $a0 -2($al) 
mv $fp $al 
lw $a0 2($al) 
li $a1 0
sw $a1 2($al)
push $a0 
mv $fp $al 
lw $al 0($al) 
lw $a0 -3($al) 
lw $a1 0($sp)
pop
add $a0 $a0 $a1
mv $fp $al 
lw $al 0($al) 
sw $a0 -3($al) 
push $fp 
mv $fp $al 
lw $al 0($al) 
jal label0

label3: //End Label of function f
lw $ra 0($sp)
pop 
addi $sp $sp 2 //pop decp & pop adec
addi $sp $sp 0 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION f
