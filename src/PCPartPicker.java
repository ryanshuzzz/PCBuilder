import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PCPartPicker {
    public static Map<String, CPU> cpuMap = new HashMap<>();
    public static ArrayList<String> cpuList = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        String processorFile = "CPUDATA.txt";


        if(checkInputFile(processorFile)){
            System.out.println("CPU file read success.");
            File inputProccessorFile = new File(processorFile);
            Scanner input = new Scanner(inputProccessorFile);
            parseFile(input, processorFile);
        }else{
            System.out.println("File does not exist."); //creates the file if not exist
            Path makeFile = Paths.get(processorFile);
            Files.createFile(makeFile);

        }

    }
    private static boolean checkInputFile(String inputFileName){
        Path filePath = Paths.get(inputFileName);
        return Files.exists(filePath);
    }
    private static void parseFile(Scanner input, String processorFile){
        while(input.hasNext()) {
            String nextLine = input.nextLine();
            String[] separated = nextLine.split(",");
            if(separated.length == 3) {
                cpuMap.put(separated[0], new CPU(separated[0], separated[1], separated[2]));
                cpuList.add(separated[0]);
            }else{
                System.out.println("Incorrect formatting in " + processorFile);
            }
        }
        input.close();
        for (int i = 0; i < cpuList.size(); i++ ){
            String name = cpuList.get(i);
            System.out.println(cpuList.get(i) +" benchmark "+ cpuMap.get(cpuList.get(i)).cpuBenchmark + " price " + cpuMap.get(cpuList.get(i)).cpuPrice);
        }
    }
}
