package Gates;

public class Or {

    boolean inputA;
    boolean inputB;

    public Or(boolean inputA, boolean inputB){
        this.inputA = inputA;
        this.inputB = inputB;
    }

    public boolean performOr(){
        return inputA || inputB;
    }

}