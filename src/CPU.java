

public class CPU {
    public String cpuName;
    public Double cpuBenchmark;
    public Double cpuPrice;

    public CPU(String name, String benchmark, String price){
        cpuName = name;
        cpuBenchmark = Double.valueOf(benchmark);
        cpuPrice = Double.valueOf(price);
    }
}
