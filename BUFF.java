public class BUFF extends Gate {

    public BUFF() {
        super();
      
    }

    public void compute() {
        int result;
        result = inputs.get(0).getLineValue();

        output.setLineValue(result);
        // Here you can do something with the result
    }
}
