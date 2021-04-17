package datastructures.graphs;

import java.util.List;

public interface OptionalStep {
    
    /**
     * 
     * @param v a vertex 
     * @param neigbhors neighbors of vertex v
     * @return 
     */
    OptionalStep add(int v, List<Integer> neigbhors);
    
    OptionalStep add(List<List<Integer>> rawAdjacencyList);
    
    /**
     * Done adding relationships.
     * 
     * @return 
     */
    BuildStep done();
    
}
