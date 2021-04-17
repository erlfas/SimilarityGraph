package datastructures.graphs;

import datastructures.graphs.v2.LayeredAdjacencyList;
import java.util.Arrays;
import java.util.List;

public class UndirectedGraphExamples {

    public static List<List<Integer>> adjacencyList2() {
        final List<List<Integer>> adjacencyList = Arrays.asList(Arrays.asList(1, 2, 3), // 0
        Arrays.asList(0, 2, 3), // 1
        Arrays.asList(0, 1, 3), // 2
        Arrays.asList(0, 1, 2) // 3
        );
        return adjacencyList;
    }

    public static List<List<Integer>> adjacencyList3() {
        final List<List<Integer>> adjacencyList = Arrays.asList(Arrays.asList(1), // 0
        Arrays.asList(0, 2), // 1
        Arrays.asList(1, 3), // 2
        Arrays.asList(2) // 3
        );
        return adjacencyList;
    }

    public static List<List<Integer>> adjacencyList4() {
        /**
         * 0---3   5---6
         * |   |   |\ /|
         * |   |   | 8 |
         * |   |   |/ \|
         * 1---2---4---7
         */
        final List<List<Integer>> adjacencyList = Arrays.asList(Arrays.asList(1, 3), // 0
        Arrays.asList(0, 2), // 1
        Arrays.asList(1, 3, 4), // 2
        Arrays.asList(0, 2), // 3
        Arrays.asList(2, 5, 7, 8), // 4
        Arrays.asList(4, 6, 8), // 5
        Arrays.asList(5, 7, 8), // 6
        Arrays.asList(4, 6, 8), // 7
        Arrays.asList(4, 5, 6, 7) // 8
        );
        return adjacencyList;
    }
    
    public static AdjacencyList adjacencyList5(int cliqueSize) {
        return LayeredAdjacencyList.make(x -> x
                .withLabel(cliqueSize)
                .add(adjacencyList5Raw())
                .done()
        );
    }

    public static List<List<Integer>> adjacencyList5Raw() {
        /**
         * 0---3   5---6
         * |   |   |\ /|
         * |   |   | 8 |
         * |   |   |/ \|
         * 1---2---4---7
         *
         * Plus edges:
         * 5-7
         * 4-6
         *
         * such that {4,5,6,7,8} is a clique
         *
         */
        final List<List<Integer>> adjacencyList = Arrays.asList(Arrays.asList(1, 3), // 0
            Arrays.asList(0, 2), // 1
            Arrays.asList(1, 3, 4), // 2
            Arrays.asList(0, 2), // 3
            Arrays.asList(2, 5, 6, 7, 8), // 4
            Arrays.asList(4, 6, 7, 8), // 5
            Arrays.asList(4, 5, 7, 8), // 6
            Arrays.asList(4, 5, 6, 8), // 7
            Arrays.asList(4, 5, 6, 7) // 8
        );
        return adjacencyList;
    }
    
    public static AdjacencyList adjacencyList6(int cliqueSize) {
        return LayeredAdjacencyList.make(x -> x
                .withLabel(cliqueSize)
                .add(adjacencyList6Raw())
                .done()
        );
    }
    
    public static List<List<Integer>> adjacencyList6Raw() {
        /**
         * 0 3
         * |/|
         * 1-2-4
         *     |
         *     5
         *     |
         *     6
         *    / \
         *   7---8
         */
        final List<List<Integer>> adjacencyList = Arrays.asList(
            Arrays.asList(1), // 0
            Arrays.asList(0, 2, 3), // 1
            Arrays.asList(1, 3, 4), // 2
            Arrays.asList(1, 2), // 3
            Arrays.asList(2, 5), // 4
            Arrays.asList(4, 6), // 5
            Arrays.asList(5, 7, 8), // 6
            Arrays.asList(6, 8), // 7
            Arrays.asList(6, 7) // 8
        );
        return adjacencyList;
    }
    
    public static List<List<Integer>> adjacencyList7() {
        final List<List<Integer>> adjacencyList = Arrays.asList(
            Arrays.asList(1,2,3,4,5,6,7,8,9), // 0
            Arrays.asList(0), // 1
            Arrays.asList(0), // 2
            Arrays.asList(0), // 3
            Arrays.asList(0), // 4
            Arrays.asList(0), // 5
            Arrays.asList(0), // 6
            Arrays.asList(0), // 7
            Arrays.asList(0), // 8
            Arrays.asList(0) // 9
        );
        return adjacencyList;
    }
    
    public static List<List<Integer>> adjacencyList8() {
        final List<List<Integer>> adjacencyList = Arrays.asList(
            Arrays.asList(1,2,3,4,5), // 0
            Arrays.asList(0,6), // 1
            Arrays.asList(0,6), // 2
            Arrays.asList(0,6), // 3
            Arrays.asList(0,6), // 4
            Arrays.asList(0,6), // 5
            Arrays.asList(1,2,3,4,5) // 6
        );
        return adjacencyList;
    }

    public static List<List<Integer>> adjacencyList() {
        final List<List<Integer>> adjacencyList = Arrays.asList(Arrays.asList(1, 2), // 0
        Arrays.asList(0, 2, 3), // 1
        Arrays.asList(0, 1, 3), // 2
        Arrays.asList(1, 2, 4), // 3
        Arrays.asList(3, 5), // 4
        Arrays.asList(4, 6), // 5
        Arrays.asList(5) // 6
        );
        return adjacencyList;
    }
    
    public static AdjacencyList adjacencyList10() {
        /**
         *     2       5
         *    / \     / \
         *   0---1---3---4
         *   |           |
         *   |           |
         *   |           |
         *   11          8
         *  /  \        / \
         * 9---10------6---7
         * 
         */
        return LayeredAdjacencyList.make(x -> x
                .withLabel(3)
                .add(0, Arrays.asList(1,2,11))
                .add(1, Arrays.asList(0,2,3))
                .add(2, Arrays.asList(0,1))
                .add(3, Arrays.asList(1,4,5))
                .add(4, Arrays.asList(3,5,8))
                .add(5, Arrays.asList(3,4))
                .add(6, Arrays.asList(7,8,10))
                .add(7, Arrays.asList(6,8))
                .add(8, Arrays.asList(4,6,7))
                .add(9, Arrays.asList(10,11))
                .add(10, Arrays.asList(6,9,11))
                .add(11, Arrays.asList(0,9,10))
                .done()
        );
    }
    
    public static AdjacencyList adjacencyList11() {
        return LayeredAdjacencyList.make(x -> x
                .withLabel(4)
                
                .add(0, Arrays.asList(1,2,3))
                .add(1, Arrays.asList(0,2,3, 4))
                .add(2, Arrays.asList(0,1,3, 8))
                .add(3, Arrays.asList(0,1,2, 6,9))
                
                .add(4, Arrays.asList(5,6,7, 1))
                .add(5, Arrays.asList(4,6,7))
                .add(6, Arrays.asList(4,5,7, 3,12))
                .add(7, Arrays.asList(4,5,6, 13))
                
                .add(8, Arrays.asList(9,10,11, 2))
                .add(9, Arrays.asList(8,10,11, 3,12))
                .add(10, Arrays.asList(8,9,11))
                .add(11, Arrays.asList(8,9,10, 14))
                
                .add(12, Arrays.asList(13,14,15, 6,9))
                .add(13, Arrays.asList(12,14,15, 7))
                .add(14, Arrays.asList(12,13,15, 11))
                .add(15, Arrays.asList(12,13,14))
                
                .done()
        );
    }
    
    public static AdjacencyList adjacencyList12() {
        return LayeredAdjacencyList.make(x -> x
                .withLabel(4)
                
                .add(0, Arrays.asList(1,2,3))
                .add(1, Arrays.asList(0,2,3, 4))
                .add(2, Arrays.asList(0,1,3, 8))
                .add(3, Arrays.asList(0,1,2, 6,9,12))
                
                .add(4, Arrays.asList(5,6,7, 1))
                .add(5, Arrays.asList(4,6,7))
                .add(6, Arrays.asList(4,5,7, 3,9,12))
                .add(7, Arrays.asList(4,5,6, 13))
                
                .add(8, Arrays.asList(9,10,11, 2))
                .add(9, Arrays.asList(8,10,11, 3,6,12))
                .add(10, Arrays.asList(8,9,11))
                .add(11, Arrays.asList(8,9,10, 14))
                
                .add(12, Arrays.asList(13,14,15, 3,6,9))
                .add(13, Arrays.asList(12,14,15, 7))
                .add(14, Arrays.asList(12,13,15, 11))
                .add(15, Arrays.asList(12,13,14))
                
                .done()
        );
    }
    
    public static AdjacencyList adjacencyList13() {
        return LayeredAdjacencyList.make(x -> x
                .withLabel(4)
                
                .add(0, Arrays.asList(1,2,3))
                .add(1, Arrays.asList(0,2,3, 4))
                .add(2, Arrays.asList(0,1,3, 8))
                .add(3, Arrays.asList(0,1,2, 6,9,12))
                
                .add(4, Arrays.asList(5,6,7, 1))
                .add(5, Arrays.asList(4,6,7))
                .add(6, Arrays.asList(4,5,7, 3,9,12))
                .add(7, Arrays.asList(4,5,6, 13))
                
                .add(8, Arrays.asList(9,10,11, 2))
                .add(9, Arrays.asList(8,10,11, 3,6,12,14))
                .add(10, Arrays.asList(8,9,11))
                .add(11, Arrays.asList(8,9,10, 12,14))
                
                .add(12, Arrays.asList(13,14,15, 3,6,9,11))
                .add(13, Arrays.asList(12,14,15, 7))
                .add(14, Arrays.asList(12,13,15, 9,11))
                .add(15, Arrays.asList(12,13,14))
                
                .done()
        );
    }
    
    public static AdjacencyList adjacencyList14() {
        return LayeredAdjacencyList.make(x -> x
                .withLabel(4)
                
                .add(0, Arrays.asList(1,2,3))
                .add(1, Arrays.asList(0,2,3, 4,6))
                .add(2, Arrays.asList(0,1,3, 8,9))
                .add(3, Arrays.asList(0,1,2, 4,6,8,9,12))
                
                .add(4, Arrays.asList(5,6,7, 1,3))
                .add(5, Arrays.asList(4,6,7))
                .add(6, Arrays.asList(4,5,7, 1,3,9,12,13))
                .add(7, Arrays.asList(4,5,6, 12,13))
                
                .add(8, Arrays.asList(9,10,11, 2,3))
                .add(9, Arrays.asList(8,10,11, 2,3,6,12,14))
                .add(10, Arrays.asList(8,9,11))
                .add(11, Arrays.asList(8,9,10, 12,14))
                
                .add(12, Arrays.asList(13,14,15, 3,6,7,9,11))
                .add(13, Arrays.asList(12,14,15, 6,7))
                .add(14, Arrays.asList(12,13,15, 9,11))
                .add(15, Arrays.asList(12,13,14))
                
                .done()
        );
    }
    
}
