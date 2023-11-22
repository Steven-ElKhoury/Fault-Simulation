public class AND extends Gate {
    
    public AND() {
        super();
    }   

    public void compute() {
        int result = 1;
        for (Line input : inputs) {
            if (input.getLineValue() == 0) {
                result = 0;
            }
        }
        output.setLineValue(result);
    }

}
