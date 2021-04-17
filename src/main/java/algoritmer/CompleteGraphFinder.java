package algoritmer;

import datastructures.graphs.CompleteSubgraph;
import datastructures.graphs.AdjacencyList;
import datastructures.graphs.v2.VertexPair;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CompleteGraphFinder {

    private final Deque<Integer> vertexStack; // stack of nodes currently considered as a potential complete subgraph
    private final TreeSet<CompleteSubgraph> completeSubgraphs;
    private boolean done;

    public CompleteGraphFinder() {
        this.vertexStack = new ArrayDeque<>();
        this.completeSubgraphs = new TreeSet<>();
        this.done = false;
    }

    public Set<CompleteSubgraph> find(final AdjacencyList adjacencyList, int l) {
        if (done) {
            throw new IllegalStateException("CompleteGraphFinder can only be used once for each call to find");
        }

        final Set<Integer> vertexSet = new HashSet<>();
        for (int i = 0; i < adjacencyList.numNodes(); ++i) {
            vertexSet.add(i);
        }
        
        System.out.println("U at start: " + vertexSet);

        find(adjacencyList, vertexSet, l);
        done = true;

        return completeSubgraphs;
    }

    private void find(final AdjacencyList adjacencyList, Set<Integer> vertexSubset, int k) {
        if (k == 2) {

            System.out.print(String.format("C: %s *** U: %s", vertexStack, vertexSubset));

            if (vertexSubset.size() >= k) {
                System.out.println(" OK ");
                for (VertexPair vertexPair : edges(adjacencyList, vertexSubset.stream().collect(Collectors.toList()))) {
                    final Set<Integer> completeSubgraph = new HashSet<>();
                    for (Integer i : vertexStack) {
                        completeSubgraph.add(i);
                    }
                    completeSubgraph.addAll(vertexPair.getVertices());

                    completeSubgraphs.add(new CompleteSubgraph(completeSubgraph));
                }
            } else {
                System.out.println("");
            }

        } else {
            final List<Integer> sortedVertices = adjacencyList.getVerticesByHighestDegree(vertexSubset);
            
            System.out.println("Sorted: " + sortedVertices);

            for (Integer v : sortedVertices) {
                System.out.print(String.format("V: %d * ", v));
                // d_k(v) is the degree of vertex v in G_k
                // G_(k-1) is a subset of G_k
                final Set<Integer> verticesKminus1 = adjacencyList.neighborsWithLabel(vertexSubset, v, k); // also calld U'
                adjacencyList.setLabel(verticesKminus1, k - 1);

                // Time: Sum of O(d(u)) for u in verticesKminus1
                adjacencyList.prioritize(verticesKminus1);

                // determine the degree d_(k-1)(v) of each vertex v in U' in G_(k-1)
                // Time: Sum of (d_k(u)+1) for every u in U'
                adjacencyList.determineDegrees(verticesKminus1);
                
                vertexStack.add(v);
                System.out.println("After add stack: " + vertexStack);
                find(adjacencyList, verticesKminus1, k - 1);
                vertexStack.removeLast();
                System.out.println("After pop stack: " + vertexStack);
                
                adjacencyList.setLabel(verticesKminus1, k); // O(d_k(u) + 1)
                adjacencyList.setLabel(v, k + 1);// logical deletion of v from G_k
                adjacencyList.move(verticesKminus1, v, k);
            }
        }
    }

    // Each each of subgraph induced by vertices
    private Set<VertexPair> edges(AdjacencyList adjacencyList, List<Integer> vertices) {
        final Set<VertexPair> set = new HashSet<>();

        for (int i = 0; i < vertices.size(); ++i) {
            final Integer vertexI = vertices.get(i);
            for (int j = 0; j < vertices.size(); ++j) {
                final Integer vertexJ = vertices.get(j);
                if (!vertexI.equals(vertexJ)) {
                    if (adjacencyList.isEdge(vertexI, vertexJ)) {
                        set.add(new VertexPair(vertexI, vertexJ));
                    }
                }
            }
        }

        return set;
    }

}
