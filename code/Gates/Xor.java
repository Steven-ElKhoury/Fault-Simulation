package Gates;

public class Xor {

    boolean inputA;
    boolean inputB;

    public Xor(boolean inputA, boolean inputB){
        this.inputA = inputA;
        this.inputB = inputB;
    }

    public boolean performXor(){
        return (!inputA && inputB) || (inputA && !inputB);
    }

}