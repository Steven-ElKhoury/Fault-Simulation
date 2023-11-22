package Gates;

public class Not {

    boolean inputA;

    public Not(boolean inputA){
        this.inputA = inputA;
    }

    public boolean performNot(){
        return !inputA;
    }

}