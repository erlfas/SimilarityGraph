package functional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NeighborListCombiner {
    
    private final Map<Integer,Integer> antNaboerTilAntNoder;
    
    public NeighborListCombiner() {
        antNaboerTilAntNoder = new HashMap<>();
    }
    
    public NeighborListCombiner add(List<Integer> naboliste) {
        int antNaboer = naboliste.size();
        Integer naavarendeAntall = antNaboerTilAntNoder.get(antNaboer);
        if (naavarendeAntall == null) {
            antNaboerTilAntNoder.put(antNaboer, 1);
        } else {
            antNaboerTilAntNoder.put(antNaboer, naavarendeAntall + 1);
        }
        
        return this;
    }
    
    public NeighborListCombiner combine(NeighborListCombiner other) {
        for (Entry<Integer,Integer> entry : other.antNaboerTilAntNoder.entrySet()) {
            Integer otherAntNaboer = entry.getKey();
            Integer otherAntNoder = entry.getValue();
            
            Integer thisAntNoder = antNaboerTilAntNoder.get(otherAntNaboer);
            if (thisAntNoder == null) {
                antNaboerTilAntNoder.put(otherAntNaboer, otherAntNoder);
            } else {
                antNaboerTilAntNoder.put(otherAntNaboer, otherAntNoder + thisAntNoder);
            }
        }
        
        return this;
    }
    
    public Map<Integer,Integer> toMap() {
        return antNaboerTilAntNoder;
    }
    
}
