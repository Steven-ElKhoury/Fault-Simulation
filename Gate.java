import java.util.ArrayList;

public class Gate {

    public ArrayList<Line> inputs = new ArrayList<>();
    public Line output;
    public static int Id=-1;
    public int gateId;

    
    public Gate() {
        Id++;
        gateId = Id;
    }

    public void addInput(Line input) {
        inputs.add(input);
    }

    public void setOutput(Line output) {
        this.output = output;
    }

    public Line getOutput() {
        return output;
    }

    public ArrayList<Line> getInputs() {
        return inputs;
    }

    public int getGateId() {
        return gateId;
    }

}

