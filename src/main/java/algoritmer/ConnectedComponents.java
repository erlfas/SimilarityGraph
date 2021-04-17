package algoritmer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConnectedComponents {
    
    private List<List<Integer>> graph;
    private Integer numNodes;
    
    public ConnectedComponents(List<List<Integer>> graph) {
        this.graph = graph;
        if (graph != null) {
            this.numNodes = graph.size();
        } else {
            this.numNodes = 0;
        }
    }
    
    public Integer getNumConnectedComponents() {
        boolean[] visited = new boolean[numNodes];
        int[] colors = new int[numNodes];
        int[] color = {-1};
        
        for (int i = 0; i < numNodes; ++i) {
            colors[i] = -1;
        }
        
        for (int i = 0; i < numNodes; ++i) {
            if (!visited[i]) {
                ++color[0];
                explore(i, visited, colors, color);
            }
        }
        
        Set<Integer> setOfcolors = new HashSet<>();
        
        for (int i = 0; i < numNodes; ++i) {
            setOfcolors.add(colors[i]);
        }
        
        return setOfcolors.size();
    }
    
    private void explore(int i, boolean[] visited, int[] colors, int[] color) {
        visited[i] = true;
        colors[i] = color[0];
        
        for (Integer neighbor : graph.get(i)) {
            if (!visited[neighbor]) {
                explore(neighbor, visited, colors, color);
            }
        }
    }
    
}
