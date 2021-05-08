package algoritmer;

import algorithms.BridgeFinder;
import datastructures.graphs.Edge;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;

public class BridgeFinderTest {
    
    @Test  
    public void test3() throws Exception {
        /**
         *     3
         *    /
         * 0-1-2
         *    \|
         *     4
         */
        // arrange
        final List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            graph.add(new ArrayList<>());
        }
        graph.get(0).add(1);
        graph.get(1).add(0);
        graph.get(1).add(2);
        graph.get(1).add(3);
        graph.get(1).add(4);
        graph.get(2).add(1);
        graph.get(2).add(4);
        graph.get(3).add(1);
        graph.get(4).add(1);
        graph.get(4).add(2);
        
        // act
        final BridgeFinder finder = new BridgeFinder(graph);
        
        // assert
        for (Edge e : finder.getBridges()) {
            System.out.println(e);
        }
    }
    
    @Test  
    public void test2() throws Exception {
        /**
         *     3
         *    /
         * 0-1-2
         *    \
         *     4
         */
        // arrange
        final List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            graph.add(new ArrayList<>());
        }
        graph.get(0).add(1);
        graph.get(1).add(0);
        graph.get(1).add(2);
        graph.get(1).add(3);
        graph.get(1).add(4);
        graph.get(2).add(1);
        graph.get(3).add(1);
        graph.get(4).add(1);
        
        // act
        final BridgeFinder finder = new BridgeFinder(graph);
        
        // assert
        for (Edge e : finder.getBridges()) {
            System.out.println(e);
        }
    }

    @Test  
    public void test() throws Exception {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            graph.add(new ArrayList<>());
        }
        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(0);
        graph.get(1).add(3);
        graph.get(2).add(0);
        graph.get(2).add(3);
        graph.get(3).add(1);
        graph.get(3).add(2);
        graph.get(3).add(4);
        graph.get(4).add(5);
        graph.get(4).add(6);
        graph.get(5).add(4);
        graph.get(5).add(6);
        graph.get(6).add(4);
        graph.get(6).add(5);
        
        BridgeFinder finder = new BridgeFinder(graph);
        for (Edge e : finder.getBridges()) {
            System.out.println(e);
        }
    }
    
}
