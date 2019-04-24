

public class GPU {
    public String gpuName;
    public Double gpuBenchmark;
    public Double gpuPrice;

    public GPU(String name, String benchmark, String price){
        gpuName = name;
        try {
            gpuBenchmark = Double.valueOf(benchmark);
        }catch(NumberFormatException e){
            System.out.println("********************ERROR*************************");
            System.out.println("Benchmark value for " + gpuName +" is not a number.\n\n");

        }
        try {
            gpuPrice = Double.valueOf(price);
        }catch(NumberFormatException e){
            System.out.println("********************ERROR*************************");
            System.out.println("Price value for " + gpuName +" is not a number.\n\n");
        }
    }
}
