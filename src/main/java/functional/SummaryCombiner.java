package functional;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import utils.SynkendeComparator;
import vo.Summary;

public class SummaryCombiner {
    
    private final Map<Integer,Summary> map;
    
    public SummaryCombiner(int maxAntallNaboer) {
        this.map = new TreeMap<>(new SynkendeComparator());
        for (int antNaboer = 0; antNaboer <= maxAntallNaboer; ++antNaboer) {
            final Summary summary = new Summary(antNaboer, 0, 0);
            map.put(antNaboer, summary);
        }
    }
    
    public SummaryCombiner add(Summary newSummary) {
        final Integer antallNaboer = newSummary.getAntallNaboer();
        final Summary currentSummary = map.get(antallNaboer);
        
        map.put(antallNaboer, currentSummary.add(newSummary));
        
        return this;
    }
    
    public SummaryCombiner combine(SummaryCombiner other) {
        
        for (Entry<Integer,Summary> entry : other.toMap().entrySet()) {
            final Integer antNaboer = entry.getKey();
            final Summary otherSummary = entry.getValue();
            final Summary thisSummary = map.get(antNaboer);
            
            map.put(antNaboer, thisSummary.add(otherSummary));
        }
        
        return this;
    }
    
    public Map<Integer, Summary> toMap() {
        final Map<Integer,Summary> copy = new TreeMap<>(new SynkendeComparator());
        
        Integer sum = 0;
        // synkende antall naboer: ... 5, 4, 3, 2, 1
        for (Entry<Integer,Summary> entry : map.entrySet()) {
            final Integer antallNaboer = entry.getKey();
            final Summary summary = entry.getValue();
            sum = sum + summary.getAntallNoder();
            final Summary newSummary = new Summary(antallNaboer, summary.getAntallNoder(), sum);
            
            copy.put(antallNaboer, newSummary);
        }
        
        return copy;
    }
    
    private int getMaxAntallNaboer() {
        return map.keySet().stream().mapToInt(x -> x).max().orElse(0);
    }
}
