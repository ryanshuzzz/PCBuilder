import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class PCPartPicker {


    public static void main(String[] args) throws IOException{
        String processorFile = "CPUDATA.txt";
        if(checkInputFile(processorFile)){
            System.out.println("CPU file read success.");
            File inputProccessorFile = new File(processorFile);
            Scanner input = new Scanner(inputProccessorFile);
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
    private void parseFile(){
        
    }
}
