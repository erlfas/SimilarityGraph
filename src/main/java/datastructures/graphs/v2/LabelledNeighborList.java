package datastructures.graphs.v2;

import datastructures.graphs.Edge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LabelledNeighborList {

    private final int nodeIndex;
    private final Map<Integer, List<Edge>> label2Neighbors;

    public LabelledNeighborList(int nodeIndex, Map<Integer, List<Edge>> label2Neighbors) {
        this.nodeIndex = nodeIndex;
        this.label2Neighbors = new HashMap<>(label2Neighbors);
    }
    
    public DoublyLinkedList<Edge> neighborsAsDoublyLinkedList() {
        final DoublyLinkedList<Edge> neighbors = new DoublyLinkedList<>();
        
        for (Edge edge : allNeighbors()) {
            neighbors.addFirst(edge);
        }
        
        return neighbors;
    }
    
    public List<Edge> inducedSubgraph(Set<Integer> vertexSubset) {
        if (!vertexSubset.contains(nodeIndex)) {
            return new ArrayList<>();
        }
        
        return allNeighbors()
                .stream()
                .filter(x -> vertexSubset.contains(x.getOtherThan(nodeIndex)))
                .collect(Collectors.toList());
    }
    
    public List<Edge> getNeighborsOfLastLayer() {
        return label2Neighbors.get(lastLayer());
    }
    
    public int lastLayer() {
        return label2Neighbors
                .keySet()
                .stream()
                .mapToInt(x -> x)
                .max()
                .orElseThrow(RuntimeException::new);
    }
    
    /**
     * Associate edges with layer k
     * 
     * @param k
     * @param edges 
     */
    public void put(final int k, final List<Edge> edges) {
        label2Neighbors.put(k, edges);
    }

    /**
     * 
     * Time: O(1)
     * 
     * @param k a label
     * @return the number of neighbors of this node with label k
     */
    public int degree(int k) {
        return label2Neighbors.get(k).size();
    }
    
    /**
     * Get the layer of the given neighbor. -1 is returned if neighbor is not
     * found.
     * 
     * @param neighbor
     * @return 
     */
    public int layer(int neighbor) {
        return label2Neighbors
                .entrySet()
                .stream()
                .filter(entry -> entry
                        .getValue()
                        .stream()
                        .anyMatch(edge -> edge.hasNode(neighbor))
                )
                .map(x -> x.getKey())
                .findFirst()
                .orElse(-1);
    }

    /**
     * 
     * Time: O(d(v)) where d(v) is the degree of this node v
     * 
     * @return 
     */
    public int numNeighbors() {
        return (int) label2Neighbors
                .values()
                .stream()
                .flatMap(x -> x.stream())
                .count();
    }

    /**
     * 
     * Time: O(d(v)) where d(v) is the degree of this node v
     * 
     * @return 
     */
    public List<Integer> getNeighborIndices() {
        return label2Neighbors
                .values()
                .stream()
                .flatMap(x -> x.stream())
                .map(x -> x.getOtherThan(nodeIndex))
                .collect(Collectors.toList());
    }
    
    /**
     * Get neighbors with label k.
     * 
     * Time: O(1)
     * 
     * @param k
     * @return 
     */
    public List<Edge> getNeighborsByLabel(int k) {
        return label2Neighbors.get(k);
    }

    /**
     * 
     * Time: O(d(v)) where d(v) is the degree of this node v
     * 
     * @return 
     */
    public List<Edge> allNeighbors() {
        return label2Neighbors
                .values()
                .stream()
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
    }

    /**
     * Move edges belonging to label K one level down, that is, to label K-1.
     *
     * Time: Bounded by the number of edges on level k, less than O(d(v))
     *
     * @param edgesToBeMoved
     * @param k
     */
    public void moveOneLevelDown(final Set<Edge> edgesToBeMoved, final int k) {
        final List<Edge> edges = label2Neighbors.get(k);
        final List<Edge> edgesKMinus1 = new ArrayList<>();

        for (Edge edge : edges) {
            if (edgesToBeMoved.contains(edge)) {
                edgesKMinus1.add(edge);
            }
        }

        edges.removeAll(edgesKMinus1);
        label2Neighbors.put(k - 1, edgesKMinus1);
    }

    /**
     *
     * Time: O(d_k(u)) where d_k(u) is the degree of this node u in G_k.
     *
     * @param v a vertex
     * @param k a label
     */
    public void putLastOnLevel(final int v, final int k) {
        final List<Edge> edges = label2Neighbors.get(k);
        if (edges != null) {
            final Iterator<Edge> iterator = edges.iterator();
            while (iterator.hasNext()) {
                final Edge edge = iterator.next();
                if (edge.hasNode(v)) {
                    iterator.remove();
                }
            }
            
            edges.add(new Edge(nodeIndex, v));
        } else {
            final List<Edge> newEdgeList = new ArrayList<>();
            newEdgeList.add(new Edge(nodeIndex, v));
            
            label2Neighbors.put(k, newEdgeList);
        }
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public Map<Integer, List<Edge>> getLabel2Neighbors() {
        return label2Neighbors;
    }

}
