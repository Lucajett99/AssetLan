package gen.interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecuteVM {
    public static final int CODESIZE = 10000;
    public static final int MEMSIZE = 10000;
    private Instruction[] code;
    private int[] memory = new int[MEMSIZE];

    private int ip = 0;         //Instruction Pointer
    private int sp = MEMSIZE;   //Stack Pointer
    private int fp = MEMSIZE;   //Frame pointer
    private int ra;             //Return Address
    private int al;             //Access link

    private int t;              //General Purpose Register

    public ExecuteVM(Instruction[] code) {
        this.code = code;
    }

    public void cpu() {
        while (true) {
            if ( 0 <= sp) {      //Check if I am out of memory
                System.out.println("\nError: Out of memory");
                return;
            } else {
                Instruction bytecode = code[ip++]; // fetch
                String arg1 = bytecode.getArg1();
                String arg2 = bytecode.getArg2();
                String arg3 = bytecode.getArg3();

                int offset, value;
                int address;

                try {
                    switch (bytecode.getCode()) {

                        case SVMParser.PUSH:
                            if (isRegister(arg1))
                                push(regRead(arg1));
                            else
                                push(Integer.parseInt(arg1));
                            break;
                        case SVMParser.POP:
                            if (arg1 != null && isRegister(arg1))
                                regStore(arg1, pop());
                            else
                                pop();
                            break;

                        case SVMParser.ADD:
                            sum(arg1, regRead(arg2), regRead(arg3));
                            break;
                        case SVMParser.ADDI:
                            value = Integer.parseInt(arg3);
                            sum(arg1, regRead(arg2), value);
                            break;

                        case SVMParser.SUB:
                            sub(arg1, regRead(arg2), regRead(arg3));
                            break;
                        case SVMParser.SUBI:
                            value = Integer.parseInt(arg3);
                            sub(arg1, regRead(arg2), value);
                            break;

                        case SVMParser.MULT:
                            multiplication(arg1, regRead(arg2), regRead(arg3));
                            break;
                        case SVMParser.MULTI: //Also used for negate (*-1)
                            value = Integer.parseInt(arg3);
                            multiplication(arg1, regRead(arg2), value);
                            break;

                        case SVMParser.DIV:
                            value = regRead(arg3);
                            division(arg1,regRead(arg2),value);
                            break;
                        case SVMParser.DIVI:
                            value = Integer.parseInt(arg3);
                            division(arg1,regRead(arg2),value);
                            break;

                        case SVMParser.STOREW:
                            offset = Integer.parseInt(arg2);
                            int addressStoreWord = offset + regRead(arg3);
                           // memory.write(addressStoreWord, regRead(arg1));
                            break;
                        case SVMParser.LOAD:
                            value = Integer.parseInt(arg2);
                            regStore(arg1,value);
                            break;
                        case SVMParser.LOADW:
                            offset = Integer.parseInt(arg2);
                            address = offset + regRead(arg3);
                           // regStore(arg1, memory.read(address));
                            break;
                        case SVMParser.MOVE:
                            value = regRead(arg1);
                            regStore(arg2, value);
                            break;

                        case SVMParser.BRANCH:
                            address = Integer.parseInt(code[ip].getArg1());
                            ip = address;
                            break;
                        case SVMParser.BCOND:
                            address = Integer.parseInt(code[ip].getArg1());
                            ip++;
                            value = regRead(bytecode.getArg1());
                            if (value!=0) ip = address;
                            break;

                        case SVMParser.JAL:
                            regStore("$ra", ip);
                            address = Integer.parseInt(code[ip].getArg1());
                            ip = address;
                            break;
                        case SVMParser.JR:
                            ip = regRead(arg1);
                            break;

                        case SVMParser.EQ:
                            regStore(arg1, regRead(arg2)==regRead(arg3)?1:0);
                            break;

                        case SVMParser.LE:
                            regStore(arg1, regRead(arg2)<=regRead(arg3)?1:0);
                            break;
                        case SVMParser.LT:
                            regStore(arg1, regRead(arg2)<regRead(arg3)?1:0);
                            break;
                        case SVMParser.GE:
                            regStore(arg1, regRead(arg2)>=regRead(arg3)?1:0);
                            break;
                        case SVMParser.GT:
                            regStore(arg1, regRead(arg2)>regRead(arg3)?1:0);
                            break;

                        case SVMParser.NOT:
                            regStore(arg1, regRead(arg2) != 0 ? 0 : 1);
                            break;
                        case SVMParser.OR:
                            regStore(arg1, (regRead(arg2)>0 || regRead(arg3)>0) ?1:0);
                            break;
                        case SVMParser.AND:
                            regStore(arg1, (regRead(arg2)>0 && regRead(arg3)>0) ?1:0);
                            break;

                        case SVMParser.PRINT:
                            /*if (arg1==null)
                                System.out.println((sp < MEMSIZE) ? memory.read(sp) : "Empty stack!");
                            else{
                                System.out.println( "Print: "+ regRead(arg1));
                            }*/
                            break;

                        case SVMParser.HALT:
                            System.out.println("Halting program...");
                            //printStack(20);
                            //System.out.println("\nResult: " + memory.read(sp) + "\n");

                            return;
                    }
                } catch (Exception e) {
                    System.out.println("Program stopped at program counter: " + ip);
                    /*
                    String toPrint = "";
                    int cont = 0;
                    for (Instruction ins:code){
                        if(ins == null)
                            break;
                        if(cont > ip -10){
                            String literalName = SVMParser._LITERAL_NAMES[ins.getCode()];
                            String str = literalName +" "+(ins.getArg1()!=null?ins.getArg1():"") +" "+(ins.getArg2()!=null?ins.getArg2():"")+" "+(ins.getArg3()!=null?ins.getArg3():"");
                            toPrint += cont+": "+ str +"\n";
                            //break;
                        }
                        else if(cont == ip){
                            String literalName = SVMParser._LITERAL_NAMES[ins.getCode()];
                            String str = literalName +" "+(ins.getArg1()!=null?ins.getArg1():"") +" "+(ins.getArg2()!=null?ins.getArg2():"")+" "+(ins.getArg3()!=null?ins.getArg3():"");
                            toPrint += cont+": "+ str +"\n";
                            break;
                        }
                        cont++;

                    }
                    */

                    e.printStackTrace();
                    //printStack(30);
                    return;
                }
            }
        }
    }
    private boolean isRegister(String str) {
        Pattern p = Pattern.compile("\\$(([ar][0-9])|(sp)|(fp)|(hp)|(al)|(ra)|(bsp))");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private void push(int v) throws Exception {
        regStore("$sp",sp-1);
        //memory.write(sp, v);
    }
    private Integer pop() throws Exception {
        //Integer val = memory.read(sp);
        regStore("$sp",sp+1);

        return 0;
    }

    void sum(String lhs, int first, int second) throws Exception {
        regStore(lhs, first + second);
    }
    void sub(String lhs, int first, int second) throws Exception {
        regStore(lhs, first - second);
    }
    void multiplication(String lhs, int first, int second) throws Exception {
        regStore(lhs, first * second);
    }
    void division(String lhs, int numerator, int denominator) throws Exception {
        if(denominator == 0) {
            System.err.println("Cannot divide per 0");
            System.exit(0);
        }
        regStore(lhs, numerator / denominator);
    }

    private int regRead(String reg) {

        switch (reg) {
            case "$fp":
                return fp;
            case "$al":
                return al;
            case "$sp":
                return sp;
            case "$ra":
                return ra;

            default:
                switch (reg.charAt(1)) {
                    case 'r':
                        // return r[Integer.parseInt(reg.substring(2))];
                    case 'a':
                      //  return a[Integer.parseInt(reg.substring(2))];

                }
                break;
        }
        return 0;
    }
    private void regStore(String reg, int v) throws Exception {
       /* switch (reg) {
            case "$fp":
                fp = v;
                break;
            case "$bsp":
                bsp = v;
                break;
            case "$al":
                al = v;
                break;
            case "$sp":
                if (v > sp) {
                    memory.cleanMemory(sp, v);
                }
                sp = v;
                if (sp <= hp) {
                    throw new Exception("Stack overflow!");
                }
                break;
            case "$ra":
                ra = v;
                break;
            default:
                switch (reg.charAt(1)) {
                    case 'r':
                        // r[Integer.parseInt(reg.substring(2))] = v;
                        break;
                    case 'a':
                        //a[Integer.parseInt(reg.substring(2))] = v;

                        break;
                }
                break;
        }

    }*/
            }
        }

