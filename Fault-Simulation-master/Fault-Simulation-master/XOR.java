public class XOR extends Gate {
        
    public XOR(){
        super();
    }

    public void compute() {
        int result;
        int firstValue = inputs.get(0).getLineValue();
        int secondValue = inputs.get(1).getLineValue();
        if (firstValue != secondValue) {
            result = 1;
        }else{
            result = 0;
        }
        output.setLineValue(result);
    }

}
