package datastructures.graphs.v2;

import datastructures.graphs.AdjacencyList;
import datastructures.graphs.Edge;
import static datastructures.graphs.UndirectedGraphExamples.adjacencyList10;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.testng.annotations.Test;

public class LayeredAdjacencyListTest {

    @Test
    public void determineDegreeTest() {
        // arrange
        final AdjacencyList adjacencyList = LayeredAdjacencyList.make(x -> x
                .withLabel(5)
                .add(0, Arrays.asList(1,2,3,4,5)) 
                .add(1, Arrays.asList(0,2,3))
                .add(2, Arrays.asList(0,1,3,4))
                .add(3, Arrays.asList(0,1,2,4))
                .add(4, Arrays.asList(0,2,3))
                .add(5, Arrays.asList(0,4))
                .done()
        );
        
        // pre assert
        assertThat(adjacencyList.degree(0), is(equalTo(5)));
        assertThat(adjacencyList.degree(1), is(equalTo(3)));
        assertThat(adjacencyList.degree(2), is(equalTo(4)));
        assertThat(adjacencyList.degree(3), is(equalTo(4)));
        assertThat(adjacencyList.degree(4), is(equalTo(3)));
        assertThat(adjacencyList.degree(5), is(equalTo(2)));
        
        // act
        adjacencyList.determineDegrees(set(0,1,2,3,4,5));
        
        // post assert
        assertThat(adjacencyList.degree(0), is(equalTo(5)));
        assertThat(adjacencyList.degree(1), is(equalTo(3)));
        assertThat(adjacencyList.degree(2), is(equalTo(4)));
        assertThat(adjacencyList.degree(3), is(equalTo(4)));
        assertThat(adjacencyList.degree(4), is(equalTo(3)));
        assertThat(adjacencyList.degree(5), is(equalTo(2)));
        
        // act 2
        adjacencyList.determineDegrees(set(0,1,2,3));
        
        // assert 2
        assertThat(adjacencyList.degree(0), is(equalTo(3)));
        assertThat(adjacencyList.degree(1), is(equalTo(3)));
        assertThat(adjacencyList.degree(2), is(equalTo(3)));
        assertThat(adjacencyList.degree(3), is(equalTo(3)));
        assertThat(adjacencyList.degree(4), is(equalTo(3))); // unchanged
        assertThat(adjacencyList.degree(5), is(equalTo(2))); // unchanged
        
        // act 3
        adjacencyList.determineDegrees(set(0,1,2));
        
        // assert 3
        assertThat(adjacencyList.degree(0), is(equalTo(2)));
        assertThat(adjacencyList.degree(1), is(equalTo(2)));
        assertThat(adjacencyList.degree(2), is(equalTo(2)));
        assertThat(adjacencyList.degree(3), is(equalTo(3))); // unchanged from last step
        assertThat(adjacencyList.degree(4), is(equalTo(3))); // unchanged from last step
        assertThat(adjacencyList.degree(5), is(equalTo(2))); // unchanged from last step
    }
    
    @Test
    public void prioritizeTest2() {
        // arrange
        final AdjacencyList adjacencyList = adjacencyList10();
        final Set<Integer> vertexSubset = set(3,4,5);
        
        // act
        adjacencyList.prioritize(vertexSubset);
        
        // assert
        final List<Edge> edges1 = adjacencyList.neighborsAsDoublyLinkedList(1).getListOfElements();
        final List<Edge> edges3 = adjacencyList.neighborsAsDoublyLinkedList(3).getListOfElements();
        final List<Edge> edges4 = adjacencyList.neighborsAsDoublyLinkedList(4).getListOfElements();
        final List<Edge> edges5 = adjacencyList.neighborsAsDoublyLinkedList(5).getListOfElements();
        final List<Edge> edges8 = adjacencyList.neighborsAsDoublyLinkedList(8).getListOfElements();
        
        System.out.println(edges1);
        System.out.println(edges3);
        System.out.println(edges4);
        System.out.println(edges5);
        System.out.println(edges8);
     
        assertThat(edges1.get(0).getOtherThan(1), is(equalTo(3)));
        assertThat(edges1.get(1).getOtherThan(1), is(equalTo(2)));
        assertThat(edges1.get(2).getOtherThan(1), is(equalTo(0)));
        
        // [1,4,5] was changed to [4,5,1]
        assertThat(edges3.get(0).getOtherThan(3), is(equalTo(4)));
        assertThat(edges3.get(1).getOtherThan(3), is(equalTo(5)));
        assertThat(edges3.get(2).getOtherThan(3), is(equalTo(1)));
        
        assertThat(edges4.get(0).getOtherThan(4), is(equalTo(3)));
        assertThat(edges4.get(1).getOtherThan(4), is(equalTo(5)));
        assertThat(edges4.get(2).getOtherThan(4), is(equalTo(8)));
        
        assertThat(edges5.get(0).getOtherThan(5), is(equalTo(3)));
        assertThat(edges5.get(1).getOtherThan(5), is(equalTo(4)));
        
        // don't touch 8 since 8 is not in U', cf. paper by Chiba
        assertThat(edges8.get(0).getOtherThan(8), is(equalTo(7)));
        assertThat(edges8.get(1).getOtherThan(8), is(equalTo(6)));
        assertThat(edges8.get(2).getOtherThan(8), is(equalTo(4)));
    }

    @Test
    public void prioritizeTest() {
        // arrange
        final AdjacencyList adjacencyList = LayeredAdjacencyList.make(x -> x
                .withLabel(5)
                .add(0, Arrays.asList(1, 2, 5, 7)) // 5, 7, 1, 2
                .add(1, Arrays.asList(0, 2, 3, 5))
                .add(2, Arrays.asList(0, 1, 4))
                .add(3, Arrays.asList(1, 4, 6))
                .add(4, Arrays.asList(2, 3))
                .add(5, Arrays.asList(0, 1, 6, 7))
                .add(6, Arrays.asList(3, 5, 8))
                .add(7, Arrays.asList(0, 5, 8))
                .add(8, Arrays.asList(6, 7))
                .done()
        );

        // act
        adjacencyList.prioritize(set(0, 5, 6, 7, 8));

        // assert
        final List<Integer> neighbors0 = adjacencyList.getNeighbors(0);
        final List<Integer> neighbors5 = adjacencyList.getNeighbors(5);
        final List<Integer> neighbors6 = adjacencyList.getNeighbors(6);
        final List<Integer> neighbors7 = adjacencyList.getNeighbors(7);
        final List<Integer> neighbors8 = adjacencyList.getNeighbors(8);
        
        assertThat(adjacencyList.numNodes(), is(equalTo(9)));
        
        // NODE 0
        
        final LabelledNeighborList labelledNeighborList0 = adjacencyList.getLabelledNeighborList(0);
        final DoublyLinkedList<Edge> doublyLinkedList0 = adjacencyList.neighborsAsDoublyLinkedList(0);
        final List<Edge> edges0 = doublyLinkedList0.getListOfElements();
        
        assertThat(edges0.size(), is(equalTo(4)));
        assertThat(edges0.get(0).getOtherThan(0), is(equalTo(5)));
        assertThat(edges0.get(1).getOtherThan(0), is(equalTo(7)));
        assertThat(edges0.get(2).getOtherThan(0), is(equalTo(2)));
        assertThat(edges0.get(3).getOtherThan(0), is(equalTo(1)));
        
        assertThat(labelledNeighborList0.layer(5), is(equalTo(5)));
        assertThat(labelledNeighborList0.layer(7), is(equalTo(5)));
        assertThat(labelledNeighborList0.layer(1), is(equalTo(5)));
        assertThat(labelledNeighborList0.layer(2), is(equalTo(5)));
        
        assertThat(neighbors0.size(), is(equalTo(4)));
        assertThat(neighbors0.size(), is(equalTo(adjacencyList.degree(0))));
        assertThat(neighbors0.get(0), is(equalTo(1)));
        assertThat(neighbors0.get(1), is(equalTo(2)));
        assertThat(neighbors0.get(2), is(equalTo(5)));
        assertThat(neighbors0.get(3), is(equalTo(7)));
        
        // NODE 5
        final LabelledNeighborList labelledNeighborList5 = adjacencyList.getLabelledNeighborList(5);
        final DoublyLinkedList<Edge> doublyLinkedList5 = adjacencyList.neighborsAsDoublyLinkedList(5);
        final List<Edge> edges5 = doublyLinkedList5.getListOfElements();
        
        assertThat(edges5.size(), is(equalTo(4)));
        assertThat(edges5.get(0).getOtherThan(5), is(equalTo(0))); // prioritized
        assertThat(edges5.get(1).getOtherThan(5), is(equalTo(6))); // prioritized
        assertThat(edges5.get(2).getOtherThan(5), is(equalTo(7))); // prioritized
        assertThat(edges5.get(3).getOtherThan(5), is(equalTo(1)));
        
        assertThat(labelledNeighborList5.layer(0), is(equalTo(5)));
        assertThat(labelledNeighborList5.layer(6), is(equalTo(5)));
        assertThat(labelledNeighborList5.layer(7), is(equalTo(5)));
        assertThat(labelledNeighborList5.layer(1), is(equalTo(5)));
        
        assertThat(neighbors5.size(), is(equalTo(4)));
        assertThat(neighbors5.size(), is(equalTo(adjacencyList.degree(5))));
        assertThat(neighbors5.get(0), is(equalTo(0)));
        assertThat(neighbors5.get(1), is(equalTo(1)));
        assertThat(neighbors5.get(2), is(equalTo(6)));
        assertThat(neighbors5.get(3), is(equalTo(7)));
        
        // NODE 6
        final LabelledNeighborList labelledNeighborList6 = adjacencyList.getLabelledNeighborList(6);
        final DoublyLinkedList<Edge> doublyLinkedList6 = adjacencyList.neighborsAsDoublyLinkedList(6);
        final List<Edge> edges6 = doublyLinkedList6.getListOfElements();
        
        assertThat(edges6.size(), is(equalTo(3)));
        assertThat(edges6.get(0).getOtherThan(6), is(equalTo(5))); // prioritized
        assertThat(edges6.get(1).getOtherThan(6), is(equalTo(8)));
        assertThat(edges6.get(2).getOtherThan(6), is(equalTo(3)));
        
        assertThat(labelledNeighborList6.layer(5), is(equalTo(5)));
        assertThat(labelledNeighborList6.layer(8), is(equalTo(5)));
        assertThat(labelledNeighborList6.layer(3), is(equalTo(5)));
        
        assertThat(neighbors6.size(), is(equalTo(3)));
        assertThat(neighbors6.size(), is(equalTo(adjacencyList.degree(6))));
        assertThat(neighbors6.get(0), is(equalTo(3)));
        assertThat(neighbors6.get(1), is(equalTo(5)));
        assertThat(neighbors6.get(2), is(equalTo(8)));
        
        // NODE 7
        final LabelledNeighborList labelledNeighborList7 = adjacencyList.getLabelledNeighborList(7);
        final DoublyLinkedList<Edge> doublyLinkedList7 = adjacencyList.neighborsAsDoublyLinkedList(7);
        final List<Edge> edges7 = doublyLinkedList7.getListOfElements();
        
        assertThat(edges7.size(), is(equalTo(3)));
        assertThat(edges7.get(0).getOtherThan(7), is(equalTo(0))); // prioritized
        assertThat(edges7.get(1).getOtherThan(7), is(equalTo(5))); // prioritized
        assertThat(edges7.get(2).getOtherThan(7), is(equalTo(8))); // prioritized
        
        assertThat(labelledNeighborList7.layer(0), is(equalTo(5)));
        assertThat(labelledNeighborList7.layer(5), is(equalTo(5)));
        assertThat(labelledNeighborList7.layer(8), is(equalTo(5)));
        
        assertThat(neighbors7.size(), is(equalTo(3)));
        assertThat(neighbors7.size(), is(equalTo(adjacencyList.degree(7))));
        assertThat(neighbors7.get(0), is(equalTo(0)));
        assertThat(neighbors7.get(1), is(equalTo(5)));
        assertThat(neighbors7.get(2), is(equalTo(8)));
        
        // NODE 8
        final LabelledNeighborList labelledNeighborList8 = adjacencyList.getLabelledNeighborList(8);
        final DoublyLinkedList<Edge> doublyLinkedList8 = adjacencyList.neighborsAsDoublyLinkedList(8);
        final List<Edge> edges8 = doublyLinkedList8.getListOfElements();
        
        assertThat(edges8.size(), is(equalTo(2)));
        assertThat(edges8.get(0).getOtherThan(8), is(equalTo(6))); // prioritized
        assertThat(edges8.get(1).getOtherThan(8), is(equalTo(7))); // prioritized
        
        assertThat(labelledNeighborList8.layer(6), is(equalTo(5)));
        assertThat(labelledNeighborList8.layer(7), is(equalTo(5)));
        
        assertThat(neighbors8.size(), is(equalTo(2)));
        assertThat(neighbors8.size(), is(equalTo(adjacencyList.degree(8))));
        assertThat(neighbors8.get(0), is(equalTo(6)));
        assertThat(neighbors8.get(1), is(equalTo(7)));
    }
    
    @Test
    public void moveTest() {
         // arrange
        final AdjacencyList adjacencyList = LayeredAdjacencyList.make(x -> x
                .withLabel(5)
                .add(0, Arrays.asList(1, 2, 5, 7))
                .add(1, Arrays.asList(0, 2, 3, 5))
                .add(2, Arrays.asList(0, 1, 4))
                .add(3, Arrays.asList(1, 4, 6))
                .add(4, Arrays.asList(2, 3))
                .add(5, Arrays.asList(0, 1, 6, 7))
                .add(6, Arrays.asList(3, 5, 8))
                .add(7, Arrays.asList(0, 5, 8))
                .add(8, Arrays.asList(6, 7))
                .done()
        );

        // act
        adjacencyList.move(set(1,2,5,7), 0, 5);
        
        // assert
        final List<Integer> neighbors1 = adjacencyList.getNeighbors(1);
        final List<Integer> neighbors2 = adjacencyList.getNeighbors(2);
        final List<Integer> neighbors5 = adjacencyList.getNeighbors(5);
        final List<Integer> neighbors7 = adjacencyList.getNeighbors(7);
        
        assertThat(neighbors1.get(0), is(equalTo(2)));
        assertThat(neighbors1.get(1), is(equalTo(3)));
        assertThat(neighbors1.get(2), is(equalTo(5)));
        assertThat(neighbors1.get(3), is(equalTo(0)));
        
        assertThat(neighbors2.get(0), is(equalTo(1)));
        assertThat(neighbors2.get(1), is(equalTo(4)));
        assertThat(neighbors2.get(2), is(equalTo(0)));
        
        assertThat(neighbors5.get(0), is(equalTo(1)));
        assertThat(neighbors5.get(1), is(equalTo(6)));
        assertThat(neighbors5.get(2), is(equalTo(7)));
        assertThat(neighbors5.get(3), is(equalTo(0)));
        
        assertThat(neighbors7.get(0), is(equalTo(5)));
        assertThat(neighbors7.get(1), is(equalTo(8)));
        assertThat(neighbors7.get(2), is(equalTo(0)));
    }

    private Set<Integer> set(Integer... nodes) {
        final Set<Integer> set = new HashSet<>();

        for (Integer node : nodes) {
            set.add(node);
        }

        return set;
    }

}
