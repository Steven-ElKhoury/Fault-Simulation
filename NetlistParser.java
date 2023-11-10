import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URL;
import java.util.HashSet;

public class NetlistParser {

    public static void main(String[] args) {
        HashMap<String, Line> primaryInputs = new HashMap<>();
        HashMap<String, Line> primaryOutputs = new HashMap<>();
        Map<String, Line> lines = new HashMap<>();
        List<Gate> gates = new ArrayList<>();
        Map<String, Integer> inputCounts = new HashMap<>(); // Keep track of how many times each line is used as an input
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL("https://www.pld.ttu.ee/~maksim/benchmarks/iscas85/bench/c17.bench").openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("INPUT")) {
                    String lineId = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                    lines.put(lineId, new Line( 0, "input"));
                    primaryInputs.put(lineId,lines.get(lineId));
                } else if (line.startsWith("OUTPUT")) {
                    String lineId = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                    lines.put(lineId, new Line(0, "output"));
                    primaryOutputs.put(lineId,lines.get(lineId));
                } else if (line.contains("=")) { 
                    String[] parts = line.split(" = ");
                    String outputId = parts[0];
                    String gateType = parts[1].substring(0, parts[1].indexOf("("));
                    String[] inputIds = parts[1].substring(parts[1].indexOf("(") + 1, parts[1].indexOf(")")).split(", ");
                    

                    Gate gate;
                    switch (gateType) {
                        case "AND":
                            gate = new AND();
                            break;
                        case "OR":
                            gate = new OR();
                            break;
                        case "NOR":
                            gate = new NOR();
                            break;
                        case "NOT":
                            gate = new NOT();
                            break;
                        case "NAND":
                            gate = new NAND();
                            break;
                        case "XOR":
                            gate = new XOR();
                            break;
                        case "XNOR":
                            gate = new XNOR();
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown gate type: " + gateType);
                    }

                    for (String inputId : inputIds) {
                        inputCounts.put(inputId, inputCounts.getOrDefault(inputId, 0) + 1); // Increment the count for this input line
                        if(inputCounts.get(inputId) > 1) {
                            char append = 'a';
                            // This line is used as an input for more than one gate, so we need to create a new line for it
                            if(inputCounts.get(inputId) > 2) {
                                Line newLine = new Line(0, "");
                                append += inputCounts.get(inputId) - 2;
                                lines.put(inputId+append, newLine);
                                gate.addInput(lines.get(inputId+append));
                                lines.get(inputId).addConnectedLine(lines.get(inputId+append));
                            }
                            Line newLine = new Line(0, "");
                            append += inputCounts.get(inputId) - 2;
                            lines.put(inputId+append, newLine);
                            gate.addInput(lines.get(inputId+append));
                            lines.get(inputId).addConnectedLine(lines.get(inputId+append));
                            Line newLine2 = new Line(0, "");
                            append++;
                            lines.put(inputId+append, newLine2);
                            gate.addInput(lines.get(inputId+append));
                            lines.get(inputId).addConnectedLine(lines.get(inputId+append));
                        }else{
                            gate.addInput(lines.get(inputId));
                        }
                    }
                    lines.get(inputIds[0]).addConnectedGate(gate);
                    lines.get(inputIds[1]).addConnectedGate(gate);

                    Line outputLine = new Line(0, "");
                    lines.put(outputId, outputLine);
                    gate.setOutput(outputLine);
                    gates.add(gate);
                }
            }           

        } catch (IOException e) {
            e.printStackTrace();
        }
           
        //Circuit circuit = new Circuit(primaryInputs, primaryOutputs, lines, gates);

        //circuit.compute();
        //circuit.printOutput();

        System.out.println("Primary inputs:");

        primaryInputs.forEach((k, v) -> System.out.println("Key: " + k + " Value: " + v.getLineValue()));
        
        System.out.println("Primary outputs:");

        primaryOutputs.forEach((k, v) -> System.out.println("Key: " + k + " Value: " + v.getLineValue()));

        System.out.println("Lines:");

        lines.forEach((k, v) -> System.out.println("Key: " + k + " Value: " + v.getLineValue()));

        System.out.println("Gates:");

        gates.forEach((gate) -> System.out.println("Gate: " + gate.getClass().getName() + " Value: " + gate.getOutput().getLineValue()));


    }
}
