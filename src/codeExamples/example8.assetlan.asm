li $b 0 
//Start codgen for  calling fun main
push $fp 
mv $fp $al 
push $al
 jal label0

print $b
halt
label0: //Label of function main
subi $sp $sp 1 
subi $sp $sp 1 
subi $sp $sp 1 
subi $sp $sp 1 
subi $sp $sp 1 
subi $sp $sp 1 
subi $sp $sp 1 
mv $sp $fp
push $ra
li $a0 0 
mv $fp $al 
sw $a0 1($al) 
mv $fp $al 
lw $a0 1($al) 

push $a0 
li $a0 5 

lw $a1 0($sp)
add $a0 $a1 $a0 
pop 
mv $fp $al 
sw $a0 2($al) 
li $a0 2 
mv $fp $al 
sw $a0 3($al) 
mv $fp $al 
lw $a0 1($al) 

push $a0 
li $a0 3 

lw $a1 0($sp)
add $a0 $a1 $a0 
pop 
mv $fp $al 
sw $a0 4($al) 
mv $fp $al 
lw $a0 1($al) 

push $a0 
mv $fp $al 
lw $a0 2($al) 

lw $a1 0($sp)
add $a0 $a1 $a0 
pop 

push $a0 
mv $fp $al 
lw $a0 3($al) 

lw $a1 0($sp)
add $a0 $a1 $a0 
pop 

push $a0 
mv $fp $al 
lw $a0 4($al) 

lw $a1 0($sp)
add $a0 $a1 $a0 
pop 
mv $fp $al 
sw $a0 5($al) 
mv $fp $al 
lw $a0 1($al) 
print $a0 
mv $fp $al 
lw $a0 2($al) 
print $a0 
mv $fp $al 
lw $a0 3($al) 
print $a0 
mv $fp $al 
lw $a0 4($al) 
print $a0 
mv $fp $al 
lw $a0 5($al) 
print $a0 
mv $fp $al 
lw $a0 6($al) 
print $a0 
mv $fp $al 
lw $a0 7($al) 
print $a0 
label1: //End Label of function main
lw $ra 0($sp)
pop 
addi $sp $sp 0 //pop decp & pop adec
addi $sp $sp 7 //pop dec 
pop //pop the old fp 
lw $fp 0($sp)
pop 
jr $ra 
 //END OF FUNCTION main
