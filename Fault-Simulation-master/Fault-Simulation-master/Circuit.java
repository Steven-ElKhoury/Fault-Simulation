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

    public void compute(String fault) {
        Line faultedLine = null;
        int faultedValue = -1;
        List<Line> faultylines = new ArrayList<>();
        if(!fault.equals("none")){
        //fault: "3-SA0"
        String id = fault.substring(0, fault.indexOf("-"));
        faultedLine = lines.get(id);
        //System.out.println("faulted line: " + id);
        faultedValue = Integer.parseInt(fault.charAt(fault.length()-1)+"");
        //System.out.println("faulted value: " + faultedValue);
        //setting the faulted value on the faulty root line
        faultedLine.setLineValue(faultedValue);
        //System.out.println("value: " + faultedLine.getLineValue());
        faultylines.add(faultedLine);
        //setting the faulted value on the fanouts of the root
        for (Line fanout: faultedLine.getConnectedLines()){
            fanout.setLineValue(faultedValue);
            faultylines.add(fanout);
            }
        }
        for(String s : primaryInputs.keySet()){
            
            if(primaryInputs.get(s).getConnectedLines().size()!=0){
                for(Line l : primaryInputs.get(s).getConnectedLines()){
                    //System.out.println("fanout");
                    if(fault.equals("none") || !faultylines.contains(l)){
                        l.setLineValue(primaryInputs.get(s).getLineValue());
                    }else{
                    }
                }
            }
        }

        for(Gate g : gates){
            
            if(fault.equals("none") || !faultylines.contains(g.output)){     
                g.compute();
            }else{
                g.setOutput(faultedLine);
            }

            Line out = g.getOutput();
            
            if(out.getConnectedLines().size()!= 0){
                for(Line l : out.getConnectedLines()){
                    //System.out.println("fanout"); 
                    if(fault.equals("none") || !faultylines.contains(l)){     
                                l.setLineValue(out.getLineValue());
                        }
                    }
            }}
    }

    public ArrayList<Integer> getOutput(){
        ArrayList<Integer> output = new ArrayList<>();
        for(String s : primaryOutputs.keySet()){
            output.add((lines.get(s).getLineValue()));
        }
        return output;
    }
}
