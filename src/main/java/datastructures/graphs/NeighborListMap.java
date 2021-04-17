package datastructures.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NeighborListMap {

    private final Map<Integer, NeighborList> neighborLists;
    
    public NeighborListMap(NeighborListMap otherNeighborListMap) {
        this.neighborLists = new HashMap<>();
        
        for (Integer node : otherNeighborListMap.getNeighborLists().keySet()) {
            final NeighborList otherNeighborList = otherNeighborListMap.getNeighborLists().get(node);
            this.neighborLists.put(node, otherNeighborList.copy());
        }
    }
    
    public NeighborListMap(Map<Integer, NeighborList> neighborLists) {
        this.neighborLists = new HashMap<>();
        
        for (Integer node : neighborLists.keySet()) {
            this.neighborLists.put(node, neighborLists.get(node).copy());
        }
    }
    
    public NeighborListMap copy() {
        final Map<Integer, NeighborList> copy = new HashMap<>();
        
        for (Integer node : copy.keySet()) {
            copy.put(node, copy.get(node).copy());
        }
        
        return new NeighborListMap(copy);
    }

    public Map<Integer, NeighborList> getNeighborLists() {
        return neighborLists;
    }
    
    public int numNodes() {
        return this.neighborLists.size();
    }
    
    public NeighborList getNeighborList(int v) {
        return neighborLists.get(v);
    }
    
    /**
     * Obtain a new NeighborListMap with vertex v removed.
     * 
     * @param v a vertex
     * @return 
     */
    public NeighborListMap remove(int v) {
        final Map<Integer, NeighborList> neighborListsSubset = new HashMap<>(neighborLists);
        neighborListsSubset.remove(v);
        return new NeighborListMap(neighborListsSubset);
    }
    
    /**
     * Obtain a new NeighborListMap where the NeighborList of every node in 
     * prioritizedNodes is changed such that the neighbors that is present in
     * prioritizedNodes is moved first in the NeighborList.
     * 
     * @param prioritizedNodes
     * @param vertexDegree
     */
    public void prioritize(Set<Integer> prioritizedNodes, Map<Integer, Integer> vertexDegree) {
        
        for (Integer node : prioritizedNodes) {
            final NeighborList neighborList = neighborLists.get(node);
            neighborList.prioritize(prioritizedNodes, vertexDegree);
        }
        
    }
    
}
