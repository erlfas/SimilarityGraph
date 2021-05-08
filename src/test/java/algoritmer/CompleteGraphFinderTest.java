package algoritmer;

import algorithms.CompleteGraphFinder;
import datastructures.graphs.AdjacencyList;
import datastructures.graphs.CompleteSubgraph;
import static datastructures.graphs.UndirectedGraphExamples.adjacencyList10;
import static datastructures.graphs.UndirectedGraphExamples.adjacencyList11;
import static datastructures.graphs.UndirectedGraphExamples.adjacencyList12;
import static datastructures.graphs.UndirectedGraphExamples.adjacencyList13;
import static datastructures.graphs.UndirectedGraphExamples.adjacencyList5;
import static datastructures.graphs.UndirectedGraphExamples.adjacencyList6;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;
import static datastructures.graphs.UndirectedGraphExamples.adjacencyList14;
import datastructures.graphs.v2.LayeredAdjacencyList;
import java.util.Arrays;

public class CompleteGraphFinderTest {
    
    @Test
    public void test10() {
        // arrange
        final AdjacencyList adjacencyList = LayeredAdjacencyList.make(x -> x
                .withLabel(4)
                .add(0, Arrays.asList(1,2,3))
                .add(1, Arrays.asList(0,2,3,  4))
                .add(2, Arrays.asList(0,1,3,  7, 4))
                .add(3, Arrays.asList(0,1,2))
                
                .add(4, Arrays.asList(5,6,7,  1, 2))
                .add(5, Arrays.asList(4,6,7))
                .add(6, Arrays.asList(4,5,7))
                .add(7, Arrays.asList(4,5,6,  2))
                .done()
        );
        
        // act
        final int cliqueSize = 4;
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList, cliqueSize)
                .stream()
                .collect(Collectors.toList());
        
        completeSubgraphs.stream().forEach(System.out::println);
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(2)));
        
        assertTrue(completeSubgraphs.get(0).contains(0,1,2,3));
        assertTrue(completeSubgraphs.get(1).contains(4,5,6,7));
    }
    
    @Test
    public void test9() {
        // arrange
        final AdjacencyList adjacencyList = LayeredAdjacencyList.make(x -> x
                .withLabel(4)
                .add(0, Arrays.asList(1,2,3))
                .add(1, Arrays.asList(0,2,3))
                .add(2, Arrays.asList(0,1,3))
                .add(3, Arrays.asList(0,1,2))
                
                .add(4, Arrays.asList(5,6,7))
                .add(5, Arrays.asList(4,6,7))
                .add(6, Arrays.asList(4,5,7))
                .add(7, Arrays.asList(4,5,6))
                .done()
        );
        
        // act
        final int cliqueSize = 4;
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList, cliqueSize)
                .stream()
                .collect(Collectors.toList());
        
        completeSubgraphs.stream().forEach(System.out::println);
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(2)));
        
        assertTrue(completeSubgraphs.get(0).contains(0,1,2,3));
        assertTrue(completeSubgraphs.get(1).contains(4,5,6,7));
    }
    
    @Test
    public void sizClique() {
        // arrange
        final AdjacencyList adjacencyList = LayeredAdjacencyList.make(x -> x
                .withLabel(3)
                .add(0, Arrays.asList(1,2,3,4,5))
                .add(1, Arrays.asList(0,2,3,4,5))
                .add(2, Arrays.asList(0,1,3,4,5))
                .add(3, Arrays.asList(0,1,2,4,5))
                .add(4, Arrays.asList(0,1,2,3,5))
                .add(5, Arrays.asList(0,1,2,3,4))
                .done()
        );
        
        // act
        final int cliqueSize = 3;
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList, cliqueSize)
                .stream()
                .collect(Collectors.toList());
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(20))); // 6 choose 3
    }
    
    @Test
    public void fiveClique() {
        // arrange
        final AdjacencyList adjacencyList = LayeredAdjacencyList.make(x -> x
                .withLabel(3)
                .add(0, Arrays.asList(1,2,3,4))
                .add(1, Arrays.asList(0,2,3,4))
                .add(2, Arrays.asList(0,1,3,4))
                .add(3, Arrays.asList(0,1,2,4))
                .add(4, Arrays.asList(0,1,2,3))
                .done()
        );
        
        // act
        final int cliqueSize = 3;
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList, cliqueSize)
                .stream()
                .collect(Collectors.toList());
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(10)));
        
        assertTrue(completeSubgraphs.get(0).contains(0,1,2));
        assertTrue(completeSubgraphs.get(1).contains(0,1,3));
        assertTrue(completeSubgraphs.get(2).contains(0,1,4));
        assertTrue(completeSubgraphs.get(3).contains(0,2,3));
        assertTrue(completeSubgraphs.get(4).contains(0,2,4));
        assertTrue(completeSubgraphs.get(5).contains(0,3,4));
        assertTrue(completeSubgraphs.get(6).contains(1,2,3));
        assertTrue(completeSubgraphs.get(7).contains(1,2,4));
        assertTrue(completeSubgraphs.get(8).contains(1,3,4));
        assertTrue(completeSubgraphs.get(9).contains(2,3,4));
    }
    
    @Test
    public void test8() {
        // act
        final int cliqueSize = 4;
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList14(), cliqueSize)
                .stream()
                .collect(Collectors.toList());
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(9)));
        
        for (CompleteSubgraph completeSubgraph : completeSubgraphs) {
            assertThat(completeSubgraph.size(), is(equalTo(cliqueSize)));
        }
        
        assertThat(completeSubgraphs.size(), is(equalTo(9)));
        
        assertTrue(completeSubgraphs.get(0).contains(0,1,2,3));
        assertTrue(completeSubgraphs.get(1).contains(1,3,4,6));
        assertTrue(completeSubgraphs.get(2).contains(12,13,14,15));
        assertTrue(completeSubgraphs.get(3).contains(2,3,8,9));
        assertTrue(completeSubgraphs.get(4).contains(3,6,9,12));
        assertTrue(completeSubgraphs.get(5).contains(4,5,6,7));
        assertTrue(completeSubgraphs.get(6).contains(6,7,12,13));
        assertTrue(completeSubgraphs.get(7).contains(8,9,10,11));
        assertTrue(completeSubgraphs.get(8).contains(9,11,12,14));
    }
    
    @Test
    public void test7() {
        // act
        final int cliqueSize = 4;
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList13(), cliqueSize)
                .stream()
                .collect(Collectors.toList());
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(6)));
        
        for (CompleteSubgraph completeSubgraph : completeSubgraphs) {
            assertThat(completeSubgraph.size(), is(equalTo(cliqueSize)));
        }
        
        assertTrue(completeSubgraphs.get(0).contains(0,1,2,3));
        assertTrue(completeSubgraphs.get(1).contains(12,13,14,15));
        assertTrue(completeSubgraphs.get(2).contains(3,6,9,12));
        assertTrue(completeSubgraphs.get(3).contains(4,5,6,7));
        assertTrue(completeSubgraphs.get(4).contains(8,9,10,11));
        assertTrue(completeSubgraphs.get(5).contains(9,11,12,14));
    }
    
    @Test
    public void test6() {
        // act
        final int cliqueSize = 4;
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList12(), cliqueSize)
                .stream()
                .collect(Collectors.toList());
        
        completeSubgraphs.stream().forEach(System.out::println);
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(5)));
        
        for (CompleteSubgraph completeSubgraph : completeSubgraphs) {
            assertThat(completeSubgraph.size(), is(equalTo(cliqueSize)));
        }
        
        assertTrue(completeSubgraphs.get(0).contains(0,1,2,3));
        assertTrue(completeSubgraphs.get(1).contains(12,13,14,15));
        assertTrue(completeSubgraphs.get(2).contains(3,6,9,12));
        assertTrue(completeSubgraphs.get(3).contains(4,5,6,7));
        assertTrue(completeSubgraphs.get(4).contains(8,9,10,11));
    }
    
    @Test
    public void test5() {
        // act
        final int cliqueSize = 4;
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList11(), cliqueSize)
                .stream()
                .collect(Collectors.toList());
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(4)));
        
        for (CompleteSubgraph completeSubgraph : completeSubgraphs) {
            assertThat(completeSubgraph.size(), is(equalTo(cliqueSize)));
        }
        
        assertTrue(completeSubgraphs.get(0).contains(0,1,2,3));
        assertTrue(completeSubgraphs.get(1).contains(12,13,14,15));
        assertTrue(completeSubgraphs.get(2).contains(4,5,6,7));
        assertTrue(completeSubgraphs.get(3).contains(8,9,10,11));
    }
    
    @Test
    public void test4() {
        // act
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList10(), 2)
                .stream()
                .collect(Collectors.toList());
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(16)));
        
        for (CompleteSubgraph completeSubgraph : completeSubgraphs) {
            assertThat(completeSubgraph.size(), is(equalTo(2)));
        }
        
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(0,1))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(0,2))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(0,11))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(1,2))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(1,3))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(3,4))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(3,5))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(4,5))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(4,8))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(6,7))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(6,8))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(6,10))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(7,8))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(9,10))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(9,11))));
        assertTrue(completeSubgraphs.contains(new CompleteSubgraph(set(10,11))));
    }
    
    @Test
    public void test3() {
        // act
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList10(), 3)
                .stream()
                .collect(Collectors.toList());
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(4)));
        
        assertThat(completeSubgraphs.get(0).size(), is(equalTo(3)));
        assertTrue(completeSubgraphs.get(0).contains(0,1,2));
        
        assertThat(completeSubgraphs.get(1).size(), is(equalTo(3)));
        assertTrue(completeSubgraphs.get(1).contains(3,4,5));
        
        assertThat(completeSubgraphs.get(2).size(), is(equalTo(3)));
        assertTrue(completeSubgraphs.get(2).contains(6,7,8));
        
        assertThat(completeSubgraphs.get(3).size(), is(equalTo(3)));
        assertTrue(completeSubgraphs.get(3).contains(9,10,11));
    }
    
    @Test
    public void test2() {
        // act
        final int cliqueSize = 5;
        final List<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder()
                .find(adjacencyList5(cliqueSize), cliqueSize)
                .stream()
                .collect(Collectors.toList());
        
        completeSubgraphs.stream().forEach(System.out::println);
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(1)));
        
        assertThat(completeSubgraphs.get(0).size(), is(equalTo(5)));
        assertTrue(completeSubgraphs.get(0).contains(4,5,6,7,8));
        assertTrue(completeSubgraphs.get(0).getVertices().contains(4));
        assertTrue(completeSubgraphs.get(0).getVertices().contains(5));
        assertTrue(completeSubgraphs.get(0).getVertices().contains(6));
        assertTrue(completeSubgraphs.get(0).getVertices().contains(7));
        assertTrue(completeSubgraphs.get(0).getVertices().contains(8));
    }

    @Test
    public void test() {
        // act
        final int cliqueSize = 3;
        final Set<CompleteSubgraph> completeSubgraphs = new CompleteGraphFinder().find(adjacencyList6(cliqueSize), cliqueSize);
        final List<CompleteSubgraph> list = completeSubgraphs.stream().collect(Collectors.toList());
        
        // assert
        assertThat(completeSubgraphs.size(), is(equalTo(2)));
        
        assertThat(list.get(0).size(), is(equalTo(3)));
        assertTrue(list.get(0).getVertices().contains(1));
        assertTrue(list.get(0).getVertices().contains(2));
        assertTrue(list.get(0).getVertices().contains(3));
        
        assertThat(list.get(1).size(), is(equalTo(3)));
        assertTrue(list.get(1).getVertices().contains(6));
        assertTrue(list.get(1).getVertices().contains(7));
        assertTrue(list.get(1).getVertices().contains(8));
    }
    
    private Set<Integer> set(Integer... nodes) {
        final Set<Integer> set = new HashSet<>();

        for (Integer node : nodes) {
            set.add(node);
        }

        return set;
    }
    
}
