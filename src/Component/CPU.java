package Component;

import Component.Component;

public class CPU extends Component {
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

    @Override
    public Double getField(String type) {
        if (type.equals("price")) {
            return getPrice();
        } else if (type.equals("benchmark")) {
            return getBenchmark();
        }
        return null;
    }

/*    public String cpuName;
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
    }*/
}
