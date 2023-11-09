package Gates;

public class And {

    boolean inputA;
    boolean inputB;

    public And(boolean inputA, boolean inputB){
        this.inputA = inputA;
        this.inputB = inputB;
    }

    public boolean performAND(){
        return inputA && inputB;
    }

}