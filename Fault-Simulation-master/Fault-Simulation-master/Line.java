import java.util.List;
import java.util.ArrayList;


public class Line {
   // private int lineId;
    private int lineValue;
    private String lineType;
    private enum possibleFaults {SA0, SA1, NONE};
    private possibleFaults fault;
    private int lineFaultValue;
    private List<Gate> connectedGates; //input gates which will have to be recomputed on line changes
    private List<Line> connectedLines; //fanout
    
    public Line(int lineValue, String type) {
        //this.lineId = lineId;
        this.lineValue = lineValue;
        this.lineType = type;
        this.fault = possibleFaults.NONE;
        this.connectedGates = new ArrayList<>();
        this.connectedLines = new ArrayList<>();
    }

    //public int getLineId() {
        //return lineId;
    //}

    public int getLineValue() {
        return lineValue;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineId(int lineId) {
       // this.lineId = lineId;
    }

    public void setLineValue(int lineValue) {
        this.lineValue = lineValue;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public void setFault(possibleFaults fault) {
        this.fault = fault;
    }

    public String getFault() {
        if (fault == possibleFaults.SA0) {
            return "SA0";
        } else if (fault == possibleFaults.SA1) {
            return "SA1";
        } else {
            return "NONE";
        }
    }

    public void setLineFaultValue(int lineFaultValue) {
        this.lineFaultValue = lineFaultValue;
    }

    public int getLineFaultValue() {
        return lineFaultValue;
    }

    public void addConnectedGate(Gate gate) {
        connectedGates.add(gate);
    }

    public void addConnectedLine(Line line) {
        connectedLines.add(line);
    }

    public List<Gate> getConnectedGates() {
        return connectedGates;
    }

    public List<Line> getConnectedLines() {
        return connectedLines;
    }


}
