import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PCPartPicker {
    public static Map<String, CPU> cpuMap = new HashMap<>();
    public static Map<String, GPU> gpuMap = new HashMap<>();
    public static ArrayList<String> cpuList = new ArrayList<>();
    public static ArrayList<String> gpuList = new ArrayList<>();
    public static String processorFile = "CPUDATA.txt";
    public static String gpuFile = "GPUDATA.txt";

    public static void main(String[] args) throws IOException{
        if(checkInputFile(processorFile) && checkInputFile(gpuFile)){
            File inputProccessorFile = new File(processorFile);
            Scanner input = new Scanner(inputProccessorFile);
            parseFile(input, processorFile);
            File inputGPUFile = new File(gpuFile);
            input = new Scanner(inputGPUFile);
            parseFile(input, gpuFile);
        }else{
            System.out.println("Error reading/writing to " + processorFile + " & " + gpuFile);
        }
    }
    private static boolean checkInputFile(String inputFileName){
        Path filePath = Paths.get(inputFileName);
        if(!Files.exists(filePath)){
            fileNotFountError(inputFileName);
        }
        System.out.println(inputFileName + " read success.");
        return Files.exists(filePath);
    }
    private static void fileNotFountError(String fileName){
        System.out.println(fileName + " does not exist. Would you like to create the file? (yes/no)"); //creates the file if not exist
        Scanner key = new Scanner(System.in);
        String yesNo = key.next();
        if (yesNo.equals("yes")) {
            Path makeFile = Paths.get(fileName);
            try {
                Files.createFile(makeFile);
            } catch (IOException e) {
                System.out.println("Error creating file..");
                e.printStackTrace();
            }
        }else{
            System.out.println("Exiting...");
        }
    }
    private static void parseFile(Scanner input, String fileName){
        while(input.hasNext()) {
            String nextLine = input.nextLine();
            String[] separated = nextLine.split(",");
            if(separated.length == 3 && fileName.equals(processorFile)) {
                cpuMap.put(separated[0], new CPU(separated[0], separated[1], separated[2]));
                cpuList.add(separated[0]);
            }else if (separated.length == 3&& fileName.equals(gpuFile)){
                gpuMap.put(separated[0], new GPU(separated[0], separated[1], separated[2]));
                gpuList.add(separated[0]);
            }else{
                System.out.println("Incorrect formatting in " + fileName);
            }
        }
        input.close();
 /*       //DEBUG TO PRINT FULL CPU LIST + INFO
        for (int i = 0; i < cpuList.size(); i++ )
            System.out.println(cpuList.get(i) + " benchmark " + cpuMap.get(cpuList.get(i)).cpuBenchmark + " price " + cpuMap.get(cpuList.get(i)).cpuPrice);
*/
/*
        //DEBUG TO PRINT FULL GPU LIST + INFO
        for (int i = 0; i < gpuList.size(); i++ )
            System.out.println(gpuList.get(i) + " benchmark " + gpuMap.get(gpuList.get(i)).gpuBenchmark + " price " + gpuMap.get(gpuList.get(i)).gpuPrice);
*/
    }
}
