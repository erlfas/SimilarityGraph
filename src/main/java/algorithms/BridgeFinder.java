package algorithms;

import datastructures.graphs.Edge;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * http://algs4.cs.princeton.edu/41graph/Bridge.java.html
 * 
 */
public class BridgeFinder {
    
    private int counter;
    private int[] pre;
    private int[] low;
    private int numNodes;
    private List<List<Integer>> graph;
    private int numBridges;
    private List<Edge> bridges;
    
    public BridgeFinder(List<List<Integer>> graph) {
        if (graph != null) {
            this.graph = graph;
            numNodes = graph.size();
        } else {
            this.graph = null;
            numNodes = 0;
        }
        
        numBridges = 0;      
        bridges = new ArrayList<>();
        pre = new int[numNodes];
        low = new int[numNodes];
        
        for (int i = 0; i < numNodes; ++i) {
            pre[i] = -1;
            low[i] = -1;
        }
        
        for (int i = 0; i < numNodes; ++i) {
            if (pre[i] == -1) {
                explore(i,i);
            }
        }
        
        for (int i = 0; i < numNodes; ++i) {
            System.out.println(String.format("pre[%d] = %d",i,pre[i]));
            System.out.println(String.format("low[%d] = %d\n",i,low[i]));
        }
    }
    
    public List<Edge> getBridges() {
        return bridges.stream().collect(toList());
    }
    
    public Integer getNumBridges() {
        return numBridges;
    }
    
    // u -> v -> w
    private void explore(int u, int v) {
        System.out.println(String.format("Explore (%d,%d)", u,v));
        pre[v] = counter++;
        low[v] = pre[v];
        
        for (Integer w : graph.get(v)) {
            System.out.println(String.format("%d -> %d -> %d", u, v, w));
            if (pre[w] == -1) {
                explore(v,w);
                System.out.println(String.format("low[%d] := %d (min of low[%d] = %d and low[%d] = %d)", v, Math.min(low[v], low[w]), v, low[v], w, low[w]));
                low[v] = Math.min(low[v], low[w]);
                if (low[w] == pre[w]) {
                    System.out.print(" ");
                    System.out.println(String.format("(%d,%d) is bridge", v, w));
                    ++numBridges;
                    bridges.add(new Edge(v,w));
                }
            } else if (w != u) {
                System.out.print(" ");
                System.out.println(String.format("w != u * low[%d] := %d (min of low[%d] = %d and pre[%d] = %d)", v, Math.min(low[v], pre[w]), v, low[v], w, pre[w]));
                low[v] = Math.min(low[v], pre[w]);
            }
        }
        
        System.out.println();
    }
    
}
