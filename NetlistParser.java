import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URL;
import java.util.Scanner;

public class NetlistParser {

    public static void main(String[] args) {
        List<String> primaryInputs = new ArrayList<>();
        List<String> primaryOutputs = new ArrayList<>();
        Map<String, Line> lines = new HashMap<>();
        List<Gate> gates = new ArrayList<>();
        Map<String, Integer> inputCounts = new HashMap<>(); // Keep track of how many times each line is used as an input
        
        long startTime = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL("https://www.pld.ttu.ee/~maksim/benchmarks/iscas85/bench/c7552.bench").openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("INPUT")) {
                    String lineId = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                    lines.put(lineId, new Line( 0, "input"));
                    primaryInputs.add(lineId);
                } else if (line.startsWith("OUTPUT")) {
                    String lineId = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                    lines.put(lineId, new Line(0, "output"));
                    primaryOutputs.add(lineId);
                } else if (line.contains("=")) { 
                    String[] parts = line.split(" = ");
                    String outputId = parts[0];
                    String gateType = parts[1].substring(0, parts[1].indexOf("("));

                    String inputIds[] = new String[2];

                    if(gateType != "BUFF" && gateType != "NOT"){
                        inputIds = parts[1].substring(parts[1].indexOf("(") + 1, parts[1].indexOf(")")).split(", ");
                    }
                    else{
                        inputIds[0] = parts[1].substring(parts[1].indexOf("(") + 1, parts[1].indexOf(")"));
                        inputIds[1] = null;
                    }       

                    System.out.println(outputId + " " + gateType + " ");

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
                        case "BUFF":
                            gate = new BUFF();
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown gate type: " + gateType);
                    }
                    for (String inputId : inputIds) {
                        if(inputId != null){
                           inputCounts.put(inputId, inputCounts.getOrDefault(inputId, 0) + 1);
                        }
                         // Increment the count for this input line
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

                    for(int i=0; i< inputIds.length; i++){
                        if(inputIds[i] != null){
                            lines.get(inputIds[i]).addConnectedGate(gate);
                        }
                    }
                   
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

        // System.out.println("Primary inputs:");

        // primaryInputs.forEach((k, v) -> System.out.println("Key: " + k + " Value: " + v.getLineValue()));
        
        // System.out.println("Primary outputs:");

        // primaryOutputs.forEach((k, v) -> System.out.println("Key: " + k + " Value: " + v.getLineValue()));

        // System.out.println("Lines:");

        // lines.forEach((k, v) -> System.out.println("Key: " + k + " Value: " + v.getLineValue()));

        // System.out.println("Gates:");

        // gates.forEach((gate) -> System.out.println("Gate: " + gate.getClass().getName() + " Value: " + gate.getOutput().getLineValue()));

        Scanner scan = new Scanner(System.in);

        for(String s: primaryInputs){
            System.out.println("Primary input " + s);
            int value = scan.nextInt();
            lines.get(s).setLineValue(value);
        }

        scan.close();

        for(String s : primaryInputs){   
            if(lines.get(s).getConnectedLines().size()!=0){
                for(Line l : lines.get(s).getConnectedLines()){
                    l.setLineValue(lines.get(s).getLineValue());
                }
            }
        }

        for(Gate g : gates){
            g.compute();
            Line out = g.getOutput();
            
            if(out.getConnectedLines().size()!= 0){
                for(Line l : out.getConnectedLines()){
                                
                    l.setLineValue(out.getLineValue());
                }
            }
        }

        for(String s : primaryOutputs){
            System.out.println(lines.get(s).getLineValue());
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime) + "ms");

        // System.out.println(lines.get("22").getLineValue());
    }
}
