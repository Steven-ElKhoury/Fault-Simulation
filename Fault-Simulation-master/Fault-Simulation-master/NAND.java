public class NAND extends Gate {
        
        public NAND() {
            super();
        }   
    
        public void compute() {
            int result = 0;
            for (Line input : inputs) {
                if (input.getLineValue() == 0) {
                    result = 1;
                }
            }
            output.setLineValue(result);
        }
}
