

public class CPU {
    public String cpuName;
    public Double cpuBenchmark;
    public Double cpuPrice;

    public CPU(String name, String benchmark, String price){
        cpuName = name;
        try {
            cpuBenchmark = Double.valueOf(benchmark);
        }catch(NumberFormatException e){
            System.out.println("********************ERROR*************************");
            System.out.println("Benchmark value for " + cpuName +" is not a number.\n\n");

        }
        try {
            cpuPrice = Double.valueOf(price);
        }catch(NumberFormatException e){
        System.out.println("********************ERROR*************************");
        System.out.println("Price value for " + cpuName +" is not a number.\n\n");
    }
    }
}
