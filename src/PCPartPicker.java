import Component.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;


public class PCPartPicker {
    private static Map<String, Component> cpuMap = new HashMap<>();
    private static Map<String, Component> gpuMap = new HashMap<>();
    private static ArrayList<String> cpuList = new ArrayList<>();
    private static ArrayList<String> gpuList = new ArrayList<>();
    private static String processorFile = "CPUDATA.txt";
    private static String gpuFile = "GPUDATA.txt";

    public static void main(String[] args) throws IOException {
        if(checkInputFile(processorFile) && checkInputFile(gpuFile)){
            File inputProccessorFile = new File(processorFile);
            Scanner input = new Scanner(inputProccessorFile);
            parseFile(input, processorFile);
            File inputGPUFile = new File(gpuFile);
            input = new Scanner(inputGPUFile);
            parseFile(input, gpuFile);
        }
        Heapsort cpuSort = new Heapsort();
        Heapsort gpuSort = new Heapsort();
        cpuSort.heapSort("price", cpuList, cpuMap);
        gpuSort.heapSort("price", gpuList, gpuMap);

        while(true) {
            Scanner key = new Scanner(System.in);
            System.out.println("Please enter your budget. Type 'exit' to exit.");
            String budget = key.nextLine();
            if(budget.equals("exit")) {
                break;
            }else if (budget.equals("c")){
                final String os = System.getProperty("os.name");
                try{
                if(os.contains("Windows")){
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else
                {
                    Runtime.getRuntime().exec("clear");
                }}catch(IOException | InterruptedException ex){

                }

            }else{
            Double maxBudget = Double.valueOf(budget);
            int possibleCPUS = findPossible(maxBudget, cpuList, cpuMap);
            int possibleGPUS = findPossible(maxBudget, gpuList, gpuMap);
            String[] bestCombination = findBestCombination(possibleCPUS, possibleGPUS, maxBudget);
            if (bestCombination != null) {
                Component finalCPU = cpuMap.get(bestCombination[0]);
                Component finalGPU = gpuMap.get(bestCombination[1]);
                System.out.printf("Best Build:   %-10s %-10s%nBenchmark:    %-10s %-10s%nPrice:        %-10s %-10s%n", finalCPU.getName()
                        , finalGPU.getName(), finalCPU.getBenchmark(), finalGPU.getBenchmark(), finalCPU.getPrice(), finalGPU.getPrice());
                System.out.printf("----------------------------%nTotal Cost:  $%-10s%n", finalCPU.getPrice() + finalGPU.getPrice());
            } else {
                System.out.println("This budget is too low for any possible builds.");
            }}
        }
    }

    //checks if files exist
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

    //generic error message for not being able to open file
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

    //parses files and checks for errors in formatting within files && closes input file
    private static void parseFile(Scanner input, String fileName) {
        int i=0;
        while (input.hasNext()) {
            i++;
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
                System.out.println("Incorrect formatting in " + fileName + "on line #" + i);
            }
        }
        input.close();
    }

    //finds the max benchmark in entire list, using heapsort
    private static Component findMaxBenchmark(ArrayList<String> list, Map<String, Component> map ){
        Heapsort newSort = new Heapsort();
        newSort.heapSort("benchmark", list, map);
        Component returnVal = map.get(list.get(list.size()-1));
        newSort.heapSort("price", list, map);
        return returnVal;
    }

    //do modified binary search of possible components in list
    private static int findPossible(Double budget, ArrayList<String> list, Map<String, Component> map){
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


    //this method finds the best combination of components (knapsack implementation
    private static String[] findBestCombination(int possibleCPUS, int possibleGPUS, Double maxBudget){
        String [] bestCombination = new String[2];
        Double maxCPUBenchmark = findMaxBenchmark(cpuList, cpuMap).getBenchmark();
        Double maxGPUBenchmark = findMaxBenchmark(gpuList, gpuMap).getBenchmark();
        Double max = 0.0;


        //iterate through all possibilities and find best benchmark ratio
        for(int i =0; i <= possibleCPUS; i++){
            for (int j = 0; j <= possibleGPUS; j++){
                if(cpuMap.get(cpuList.get(i)).getPrice()+gpuMap.get(gpuList.get(j)).getPrice() <= maxBudget){

                    Double cpuRatio = cpuMap.get(cpuList.get(i)).getBenchmark()/maxCPUBenchmark;
                    Double gpuRatio = gpuMap.get(gpuList.get(j)).getBenchmark()/maxGPUBenchmark;
                    if(cpuRatio + gpuRatio > max){
                        max = cpuRatio + gpuRatio;
                        bestCombination[0] = cpuList.get(i);
                        bestCombination[1] = gpuList.get(j);
                     }
                }
            }
        }
        if (max == 0.0)
            return null;

        return bestCombination;
    }

}
