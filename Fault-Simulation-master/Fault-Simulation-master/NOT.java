public class NOT extends Gate {
    public NOT() {
        super();
    }
    public void compute() {
        int result = 1;
        for (Line input : inputs) {
            if (input.getLineValue() == 1) {
                result = 0;
            }
        }
        output.setLineValue(result);
    }
}
