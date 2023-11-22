import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Circuit {

    HashMap<String, Line> primaryInputs = new HashMap<>();
    HashMap<String, Line> primaryOutputs = new HashMap<>();
    List<Gate> gates = new ArrayList<>();
    Map<String, Line> lines = new HashMap<>();

    public Circuit (HashMap<String, Line> primaryInputs, HashMap<String, Line> primaryOutputs, List<Gate> gates, Map<String, Line> lines){
        this.primaryInputs = primaryInputs;
        this.gates = gates;
        this.lines = lines;
        this.primaryOutputs = primaryOutputs;
    }

    public void compute() {
        for(String s : primaryInputs.keySet()){
          
            if(primaryInputs.get(s).getConnectedLines().size()!=0){
                for(Line l : primaryInputs.get(s).getConnectedLines()){
                    //System.out.println("fanout");
                    l.setLineValue(primaryInputs.get(s).getLineValue());
                }
            }
        }

        for(Gate g : gates){
            g.compute();
            Line out = g.getOutput();
            
            if(out.getConnectedLines().size()!= 0){
                for(Line l : out.getConnectedLines()){
                    //System.out.println("fanout");               
                    l.setLineValue(out.getLineValue());
                }
            }
        }
    }

    public ArrayList<Integer> getOutput(){
        ArrayList<Integer> output = new ArrayList<>();
        for(String s : primaryOutputs.keySet()){
            output.add((lines.get(s).getLineValue()));
        }
        return output;
    }
}
