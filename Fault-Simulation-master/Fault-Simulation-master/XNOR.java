public class XNOR extends Gate{
    public XNOR(){
        super();
    }
  
    public void compute() {
        int result;
        int firstValue = inputs.get(0).getLineValue();
        int secondValue = inputs.get(1).getLineValue();
        if (firstValue != secondValue) {
            result = 0;
        }else{
            result = 1;
        }
        output.setLineValue(result);
    }
}
