package vo;

public class Summary {

    private final Integer antallNaboer;
    private final Integer antallNoder;
    private final Integer akkumulertSum;
    
    public Summary() {
        this.antallNaboer = 0;
        this.antallNoder = 0;
        this.akkumulertSum = 0;
    }
    
    public Summary(int antallNaboer, int antallNoder, int akkumulertSum) {
        this.antallNaboer = antallNaboer;
        this.antallNoder = antallNoder;
        this.akkumulertSum = akkumulertSum;
    }

    public Integer getAntallNaboer() {
        return antallNaboer;
    }

    public Integer getAntallNoder() {
        return antallNoder;
    }

    public Integer getAkkumulertSum() {
        return akkumulertSum;
    }
    
    public Summary add(Summary other) {
        return new Summary(this.antallNaboer, this.antallNoder + other.antallNoder, this.akkumulertSum + other.akkumulertSum);
    }
    
    public Summary copy() {
        return new Summary(this.antallNaboer, this.antallNoder, this.akkumulertSum);
    }
    
}
