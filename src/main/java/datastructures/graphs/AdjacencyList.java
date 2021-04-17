package datastructures.graphs;

import datastructures.graphs.v2.DoublyLinkedList;
import datastructures.graphs.v2.LabelledNeighborList;
import java.util.List;
import java.util.Set;

public interface AdjacencyList {
    
    /**
     * Get neighbors as doubly linked list.
     * 
     * @param v get the neighbors of node {@code v}
     * @return neighbors of {@code v} as doubly linked list.
     */
    DoublyLinkedList<Edge> neighborsAsDoublyLinkedList(int v);
    
    /**
     * Get neighbors of vertex v with label k that is contained in vertexSubset.
     * 
     * @param vertexSubset
     * @param v
     * @param k
     * @return 
     */
    Set<Integer> neighborsWithLabel(Set<Integer> vertexSubset, Integer v, Integer k);
    
    /**
     * Set label k for vertex v.
     * 
     * @param v 
     * @param k 
     */
    void setLabel(Integer v, Integer k);
    
    /**
     * Set label k for all vertices in vertices.
     * 
     * 
     * @param vertices 
     * @param k 
     */
    void setLabel(Set<Integer> vertices, Integer k);
    
    int numNodes();
    
    /**
     * Determine the degree d_(k-1)(u) of each u in U' in G_(k-1).
     * 
     * Time: Sum of (d_k(u)+1) for every u in U'
     * 
     * @param vertices 
     */
    void determineDegrees(Set<Integer> vertices);
    
    /**
     * Get the neighbors of vertex v. 
     * 
     * NOTE that this structure is never changed
     * after the initialization. Prioritization has no effect on this list!!!
     * 
     * Time: O(d(v))
     * 
     * @param v a vertex
     * @return a list of neighbors of v.
     */
    List<Integer> getNeighbors(int v);
    
    boolean isEdge(int i, int j);
    
    LabelledNeighborList getLabelledNeighborList(int v);
    
    /**
     * Get the degree of vertex v.
     * 
     * @param v a vertex
     * @return the degree of vertex v.
     */
    int degree(int v);
    
    /**
     * Get the vertices in vertexSubset in nonincreasing order of degree.
     * 
     * @param vertexSubset only take the vertices in this set
     * @return vertices in nonincreasing order of degree
     */
    List<Integer> getVerticesByHighestDegree(Set<Integer> vertexSubset);

    /**
     * Get the degree of vertex v in the subgraph defined by the vertices
     * in vertexSubset. Only search in layer k. v must be 
     * included in vertexSubset.
     * 
     * @param v a vertex
     * @param vertexSubset a subset of the vertices in G_k
     * @param k a label, for subgraph G_k
     * @return 
     */
    int degree(int v, Set<Integer> vertexSubset, int k);
    
    /**
     * Move vertices in prioritizedNodes that are also in G_k to the front of the 
     * adjacency list and make them layer k-1. After this operation the vertices
     * are no longer in layer k but only in layer k-1. The presupposition is 
     * that every vertex in prioritizedNodes is in G_k.
     * 
     * @param prioritizedNodes vertices to be moved at front of adjacency list
     * from layer k to layer k-1.
     */
    void prioritize(Set<Integer> prioritizedNodes);
    
    /**
     * In the adjacency list of every vertex in vertices, move vertex v to 
     * the position next to the last entry containing a vertex labelled k. This
     * presupposes that v is in the neighbor list of every vertex in vertices.
     * 
     * Time: Sum of O(d(v)) for every v in vertices
     * 
     * @param vertices
     * @param v
     * @param k 
     */
    void move(final Set<Integer> vertices, final int v, final int k);
    
}
