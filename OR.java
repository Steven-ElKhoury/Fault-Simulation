public class OR extends Gate {
        
        public OR() {
            super();
        }   
    
        public void compute() {
            int result = 0;
            for (Line input : inputs) {
                if (input.getLineValue() == 1) {
                    result = 1;
                }
            }
            output.setLineValue(result);
        }
        
}
