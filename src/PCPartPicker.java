import Component.CPU;
import Component.Component;
import Component.GPU;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PCPartPicker {
    private static Map<String, Component> cpuMap = new HashMap<>();
    private static Map<String, Component> gpuMap = new HashMap<>();
    private static ArrayList<String> cpuList = new ArrayList<>();
    private static ArrayList<String> gpuList = new ArrayList<>();
    private static String processorFile = "CPUDATA.txt";
    private static String gpuFile = "GPUDATA.txt";

    public static void main(String[] args) throws IOException{
        if(checkInputFile(processorFile) && checkInputFile(gpuFile)){
            File inputProccessorFile = new File(processorFile);
            Scanner input = new Scanner(inputProccessorFile);
            parseFile(input, processorFile);
            File inputGPUFile = new File(gpuFile);
            input = new Scanner(inputGPUFile);
            parseFile(input, gpuFile);
        }
        sortList("price", cpuList, cpuMap);
        sortList("price", gpuList, gpuMap);
        Scanner key = new Scanner(System.in);
        System.out.println("Please enter your budget. ");
        String budget = key.nextLine();
        Double maxBudget = Double.valueOf(budget);
        int possibleCPUS = findPossible(maxBudget, cpuList, cpuMap);
        int possibleGPUS = findPossible(maxBudget, gpuList, gpuMap);
        findBestCombination(possibleCPUS, possibleGPUS, maxBudget);
    }
    private static boolean checkInputFile(String inputFileName){
        Path filePath = Paths.get(inputFileName);
        if(!Files.exists(filePath)){
            fileNotFountError(inputFileName);
        }
        if(Files.exists(filePath)) {
            System.out.println(inputFileName + " read success.");
        } else{
            System.out.println(inputFileName + " does not exist.");
        }
        return Files.exists(filePath);
    }
    private static void fileNotFountError(String fileName){
        System.out.println(fileName + " does not exist. Would you like to create the file? (yes/no)"); //creates the file if not exist
        Scanner key = new Scanner(System.in);
        String yesNo = key.nextLine();
        if (yesNo.equals("yes")) {
            Path makeFile = Paths.get(fileName);
            try {
                Files.createFile(makeFile);
            } catch (IOException e) {
                System.err.println("Error creating file..");
                e.printStackTrace();
            }
        }else{
            System.out.println("Exiting...");
        }
    }
    private static void parseFile(Scanner input, String fileName) {
        while (input.hasNext()) {
            String nextLine = input.nextLine();
            String[] separated = nextLine.split(",");
            if (separated.length == 3 && fileName.equals(processorFile)) {
                CPU newCPU = new CPU();
                newCPU.setName(separated[0]);
                try {
                    double benchmark = Double.valueOf(separated[1]);
                    newCPU.setBenchmark(benchmark);
                } catch (NumberFormatException e) {
                    System.err.println("********************ERROR*************************");
                    System.err.println("Benchmark value for " + separated[0] + " is not a number.\n\n");
                }
                try {
                    double price = Double.valueOf(separated[2]);
                    newCPU.setPrice(price);
                } catch (NumberFormatException e) {
                    System.err.println("********************ERROR*************************");
                    System.err.println("Price value for " + separated[0] + " is not a number.\n\n");
                }
                cpuMap.put(separated[0], newCPU);
                cpuList.add(separated[0]);
            } else if (separated.length == 3 && fileName.equals(gpuFile)) {
                GPU newGPU = new GPU();
                newGPU.setName(separated[0]);
                try {
                    double benchmark = Double.valueOf(separated[1]);
                    newGPU.setBenchmark(benchmark);
                } catch (NumberFormatException e) {
                    System.err.println("********************ERROR*************************");
                    System.err.println("Benchmark value for " + separated[0] + " is not a number.\n\n");
                }
                try {
                    double price = Double.valueOf(separated[2]);
                    newGPU.setPrice(price);
                } catch (NumberFormatException e) {
                    System.err.println("********************ERROR*************************");
                    System.err.println("Price value for " + separated[0] + " is not a number.\n\n");
                }
                gpuMap.put(separated[0], newGPU);
                gpuList.add(separated[0]);
            } else {
                System.out.println("Incorrect formatting in " + fileName);
            }
        }
        input.close();
    }
    private static void printDebug(){
     //DEBUG TO PRINT FULL Component.CPU LIST + INFO
        for (int i = 0; i < cpuList.size(); i++ ) {
            System.err.printf("%-30s benchmark: %-10s price: %-10s \n", cpuList.get(i), cpuMap.get(cpuList.get(i)).getBenchmark(), cpuMap.get(cpuList.get(i)).getPrice());
        }
        System.out.println("\n\n");
        //DEBUG TO PRINT FULL Component.GPU LIST + INFO

        for (int i = 0; i < gpuList.size(); i++ )
            System.err.printf("%-30s benchmark: %-10s price: %-10s \n", gpuList.get(i), gpuMap.get(gpuList.get(i)).getBenchmark(), gpuMap.get(gpuList.get(i)).getPrice());
    }

    //insertion sort the list of strings based on 'TYPE' given either benchmark or price
    private static void sortList(String Type, ArrayList list, Map<String, Component> map ){

        for(int i=1; i < list.size(); i++){
            for (int j = i; j>0; j--){
                Double firstVal = map.get(list.get(j-1)).getField(Type);
                Double secVal = map.get(list.get(j)).getField(Type);
                if (firstVal > secVal){
                    Collections.swap(list, j, j-1);
                }

            }
        }
    }
    //do modified binary search of possible components in list
    private static int findPossible(Double budget, ArrayList list, Map<String, Component> map){
        int length = list.size();

        if (budget <= map.get(list.get(0)).getPrice()){
            System.out.println("This budget is too low for any possible builds.");
        }
        if (budget >= map.get(list.get(length-1)).getPrice()){
            return length-1;
        }
        int i = 0 , j = length-1,midPoint;
        while(i<=j){
            midPoint = (i+j)/2;
            if((map.get(list.get(midPoint)).getPrice()) == budget){
                return midPoint;
            }else if(budget < (map.get(list.get(midPoint)).getPrice())){
                j = midPoint -1;
            }else{
                i = midPoint +1;
            }

        }
        return i-1;
    }
    private static String[] findBestCombination(int possibleCPUS, int possibleGPUS, Double maxBudget){
//        ArrayList<Component> bestCombination = new ArrayList<>();
        String [] bestCombination = new String[1];
        Double max = 0.0;
        for(int i =0; i < possibleCPUS; i++){
            for (int j = 0; j < possibleGPUS; i++){
                if(cpuMap.get(cpuList.get(i)).getPrice()+gpuMap.get(gpuList.get(j)).getPrice() < maxBudget){
                    Double cpuRatio = cpuMap.get(cpuList.get(i)).getBenchmark()/cpuMap.get(cpuList.size()-1).getBenchmark();
                    Double gpuRatio = gpuMap.get(gpuList.get(j)).getBenchmark()/gpuMap.get(gpuList.size()-1).getBenchmark();
                    if(cpuRatio + gpuRatio > max){
                        max = cpuRatio + gpuRatio;
                        bestCombination[0] = cpuList.get(i);
                        bestCombination[1] = gpuList.get(j);
                     }
                }
            }
        }
        return bestCombination;
    }

}
