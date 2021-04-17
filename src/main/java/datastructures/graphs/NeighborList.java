package datastructures.graphs;

import java.util.ArrayList;
    import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The neighbor list of a node.
 */
public class NeighborList {
    
    private final int nodeIndex;
    private final List<Integer> neighbors;

    /**
     * 
     * @param nodeIndex the index of the node of this neighbor list
     * @param neighbors the neighbors of the node with index nodeIndex.
     */
    public NeighborList(int nodeIndex, List<Integer> neighbors) {
        if (nodeIndex < 0) {
            throw new IllegalArgumentException();
        }

        if (neighbors == null) {
            throw new IllegalArgumentException();
        }

        this.nodeIndex = nodeIndex;
        this.neighbors = new ArrayList<>(neighbors);
    }
    
    /**
     * Make an immutable copy of this class.
     * 
     * @return 
     */
    public NeighborList copy() {
        return new NeighborList(nodeIndex, getNeighbors());
    }

    /**
     * The index of the node of this neighbor list.
     * 
     * @return 
     */
    public int getNodeIndex() {
        return nodeIndex;
    }

    /**
     * This neighbors in this neighbor list as a list.
     * 
     * @return 
     */
    public List<Integer> getNeighbors() {
        return neighbors;
    }
    
    public int numNeighbors() {
        return neighbors.size();
    }
    
    /**
     * The neighbors in this neighbor list as a set.
     * 
     * @return 
     */
    public Set<Integer> getNeighborSet() {
        return neighbors.stream().collect(Collectors.toSet());
    }
    
    /**
     * Make a new NeighborList only retaining the nodes in the set nodes
     * in the neighbor list.
     * 
     * @param retainNodeSet node indexes to retain in the new neighbor list
     * @return a new NeighborList only retaining the nodes in retainNodeSet
     */
    public NeighborList retain(Set<Integer> retainNodeSet) {
        return new NeighborList(nodeIndex, neighbors.stream().filter(retainNodeSet::contains).collect(Collectors.toList()));
    }
    
    /**
     * Obtain a new List of neighbors where prioritizedNodes are put first
     * in the list if they were already in the list. Nodes in prioritizedNodes that
     * were not previously in the neighbor list will not be in the new list.
     * 
     * @param vertexDegree
     * @param prioritizedNodes nodes to put first in the list if they are already
     * in the list
     */
    public void prioritize(Set<Integer> prioritizedNodes, Map<Integer, Integer> vertexDegree) {
        final int degreeInGk = vertexDegree.get(nodeIndex);
        final List<Integer> first = new ArrayList<>();
        final List<Integer> next = new ArrayList<>();
        for (int i = 0; i < degreeInGk && i < neighbors.size(); i++) {
            final Integer v = neighbors.get(i);
            if (prioritizedNodes.contains(v)) {
                first.add(v);
            } else {
                next.add(v);
            }
        }
        
        final List<Integer> last = degreeInGk <= neighbors.size() ? neighbors.subList(degreeInGk, neighbors.size()) : new ArrayList<>();
        
        neighbors.clear();
        neighbors.addAll(first);
        neighbors.addAll(next);
        neighbors.addAll(last);
    }
    
}
