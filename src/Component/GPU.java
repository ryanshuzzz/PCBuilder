package Component;


public class GPU extends Component {
    private String name;
    private Double benchmark;
    private Double price;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setBenchmark(Double benchmark) {
        this.benchmark = benchmark;
    }

    @Override
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getBenchmark() {
        return this.benchmark;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }
/*    public String gpuName;
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
    }*/
}
