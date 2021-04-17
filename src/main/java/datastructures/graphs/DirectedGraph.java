package datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

public class DirectedGraph {

    private Integer numNodes;
    private Integer numEdges;
    private List<List<Integer>> graph;
    private List<Integer> indegree;
    
    public DirectedGraph(List<List<Integer>> graph) {
        this.graph = graph;
        this.numNodes = graph.size();
        this.numEdges = 0;
        for (int i = 0; i < graph.size(); ++i) {
            this.numEdges += graph.get(i).size();
        }
    }
    
    public DirectedGraph(Integer numNodes) {
        if (numNodes == null || numNodes < 0 || numNodes > Integer.MAX_VALUE - 1) {
            throw new IllegalArgumentException();
        }
        
        this.numNodes = numNodes;
        this.numEdges = 0;
        
        this.graph = new ArrayList<>();
        for (int i = 0; i < numNodes; ++i) {
            this.graph.add(new ArrayList<>());
        }
        
        this.indegree = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; ++i) {
            this.indegree.add(i, 0);
        }
    }

    public Integer getNumNodes() {
        return numNodes;
    }

    public Integer getNumEdges() {
        return numEdges;
    }
    
    public DirectedGraph transpose() {
        final DirectedGraph transpose = new DirectedGraph(numNodes);
        
        for (int u = 0; u < numNodes; ++u) {
            for (Integer v : graph.get(u)) {
                transpose.addEdge(v, u);
            }
        }
        
        return transpose;
    }
    
    /**
     * Adds the directed edge (u,v).
     * 
     * @param u
     * @param v 
     */
    public void addEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        
        graph.get(u).add(v);
        
        Integer currentIndegree = indegree.get(v);
        if (currentIndegree == null || currentIndegree == 0) {
            indegree.set(v, 1);
        } else {
            indegree.set(v, currentIndegree + 1);
        }
        
        numEdges += 1;
    }
    
    public List<Integer> neighborhood(int v) {
        validateVertex(v);
        
        return new ArrayList<>(graph.get(v));
    }
    
    public Integer indegree(int v) {
        validateVertex(v);
        
        return indegree.get(v);
    }
    
    public Integer outdegree(int v) {
        validateVertex(v);
        
        return graph.get(v).size();
    }
    
    private void validateVertex(int v) {
        if (v < 0 || v > numNodes - 1) {
            throw new IllegalArgumentException("Invalid vertex index.");
        }
    }
    
}
