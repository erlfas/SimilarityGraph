package datastructures.graphs.v2;

import datastructures.graphs.MandatoryStep;
import datastructures.graphs.BuildStep;
import datastructures.graphs.OptionalStep;
import datastructures.graphs.AdjacencyList;
import datastructures.graphs.Edge;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LayeredAdjacencyList implements AdjacencyList {

    private final static Comparator<? super Pair<Integer, List<Edge>>> HIGHEST_DEGREES_FIRST = highestDegreesFirst();
    
    private final Map<Integer, LabelledNeighborList> vertex2Neigbhors;
    private final Map<Integer, DoublyLinkedList<Edge>> adjacencyList;
    private final Map<Integer, Integer> vertex2Label;
    private final Map<Integer, Integer> vertex2Degree;

    private LayeredAdjacencyList(Map<Integer, LabelledNeighborList> vertex2Neigbhors, int initialLabel) {
        this.vertex2Neigbhors = vertex2Neigbhors;
        this.vertex2Label = new HashMap<>();
        for (Integer v : vertex2Neigbhors.keySet()) {
            vertex2Label.put(v, initialLabel);
        }
        this.adjacencyList = new HashMap<>();
        this.vertex2Degree = new HashMap<>();
        for (Entry<Integer, LabelledNeighborList> entry : vertex2Neigbhors.entrySet()) {
            adjacencyList.put(entry.getKey(), entry.getValue().neighborsAsDoublyLinkedList());
            vertex2Degree.put(entry.getKey(), entry.getValue().numNeighbors());
        }
    }

    public static AdjacencyList make(Function<MandatoryStep, BuildStep> configuration) {
        return configuration.andThen(BuildStep::build).apply(new AdjacencyListBuilder());
    }
    
    @Override
    public DoublyLinkedList<Edge> neighborsAsDoublyLinkedList(int v) {
        return adjacencyList.get(v);
    }

    @Override
    public boolean isEdge(int i, int j) {
        return vertex2Neigbhors
                .get(i)
                //.getNeighborsOfLastLayer()
                .allNeighbors()
                .contains(new Edge(i, j));
    }

    public List<Edge> getNeighborsOfLastLayer(int v) {
        return vertex2Neigbhors
                .get(v)
                .getNeighborsOfLastLayer();
    }

    @Override
    public int degree(final int v, final Set<Integer> vertexSubset, final int k) {
        if (!vertexSubset.contains(v)) {
            throw new IllegalArgumentException(String.format("Vertex %d must be in vertexSubset.", v));
        }

        final List<Edge> neighbors = vertex2Neigbhors.get(v).getNeighborsByLabel(k);
        final int degreeInGk = neighbors.size();

        int degree = 0;
        for (int i = 0; i < neighbors.size() && i < degreeInGk; i++) {
            final Edge neighbor = neighbors.get(i);
            final int other = neighbor.getSource() != v ? neighbor.getSource() : neighbor.getTarget();
            if (vertexSubset.contains(other)) {
                ++degree;
            }
        }

        return degree;
    }

    @Override
    public int degree(int v) {
        return vertex2Degree.get(v);
    }
    
    // Time: O(n) where n = |vertices|
    /**
     * 
     * @param v a node in vertices
     * @param neighbors neighbors of v
     * @param vertices vertices inducing a subgraph
     * @return edges incident on v that are inside the subgrpah incuded by vertices
     */
    private Set<Edge> makeEdges(final Integer v, DoublyLinkedList<Edge> neighbors, Set<Integer> vertices) {
        final Set<Edge> edges = new HashSet<>();
        
        // O(d(v))
        final Iterator<Node<Edge>> it = neighbors.forwardIterator();
        while (it.hasNext()) {
            final Node<Edge> node = it.next();
            final int other = node.getElement().getOtherThan(v);
            if (vertices.contains(other)) {
                edges.add(node.getElement());
            }
        }

        return edges;
    }

    @Override
    public void prioritize(final Set<Integer> prioritizedVertices) {
        // TODO
        // Time: Sum of (d_k(u)+1) for every u in U'
        // In the adjacency list of each u in U’, choose the vertices in U’ 
        // (labeled "k-1") among the first d_k(u) entries; and move them to 
        // the first part of the list.

        // Q: Is vertex2Degree.get(v) correct?
        // A: Yes, it is the degree of v in G_k, and we are now going to find 
        // since next step in CompleteGraphFinder is to determine degrees
        
        for (Integer v : prioritizedVertices) {
            final DoublyLinkedList<Edge> neighbors = adjacencyList.get(v);
            final Set<Edge> edges = makeEdges(v, neighbors, prioritizedVertices);
            neighbors.moveToFront(edges, vertex2Degree.get(v));
        }

    }

    @Override
    public void move(final Set<Integer> vertices, final int v, final int k) {
        for (Integer vertex : vertices) {
            final LabelledNeighborList labelledNeighborList = vertex2Neigbhors.get(vertex);
            labelledNeighborList.putLastOnLevel(v, k);
        }
    }

    @Override
    public int numNodes() {
        return vertex2Neigbhors.size();
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        return vertex2Neigbhors
                .get(v)
                .getNeighborIndices();
    }

    @Override
    public LabelledNeighborList getLabelledNeighborList(int v) {
        return vertex2Neigbhors.get(v);
    }

    @Override
    public Set<Integer> neighborsWithLabel(Set<Integer> vertexSubset, Integer v, Integer k) {
        if (v == null) {
            throw new IllegalArgumentException();
        }

        if (k == null) {
            throw new IllegalArgumentException();
        }

        return getNeighbors(v)
                .stream()
                .filter(x -> vertexSubset.contains(x))
                .filter(neighbor -> k.equals(vertex2Label.get(neighbor)))
                .collect(Collectors.toSet());
    }

    @Override
    public void setLabel(Integer v, Integer k) {
        if (v == null) {
            throw new IllegalArgumentException();
        }

        if (k == null) {
            throw new IllegalArgumentException();
        }

        vertex2Label.put(v, k);
    }

    @Override
    public void setLabel(Set<Integer> vertices, Integer k) {
        if (vertices == null) {
            throw new IllegalArgumentException();
        }
        
        if (k == null) {
            throw new IllegalArgumentException();
        }
        
        for (Integer v : vertices) {
            setLabel(v, k);
        }
    }

    @Override
    public void determineDegrees(Set<Integer> subgraphVertices) {
        // Time: Sum of (d_k(u)+1) for every u in U'
        // vertices = U'
        
        for (Integer v : subgraphVertices) {
            final DoublyLinkedList<Edge> neighbors = adjacencyList.get(v);
            
            int newDegree = 0;
            final Iterator<Node<Edge>> it = neighbors.forwardIterator();
            while (it.hasNext()) {
                final Node<Edge> node = it.next();
                final int otherVertex = node.getElement().getOtherThan(v);
                if (subgraphVertices.contains(otherVertex)) {
                    newDegree++;
                }
            }
            
            vertex2Degree.put(v, newDegree);
        }
    }

    private class Pair<X, Y> {

        private final X key;
        private final Y value;

        public Pair(X key, Y value) {
            this.key = key;
            this.value = value;
        }

        public X getKey() {
            return key;
        }

        public Y getValue() {
            return value;
        }

    }

    @Override
    public List<Integer> getVerticesByHighestDegree(Set<Integer> vertexSubset) {
        return vertex2Neigbhors
                .entrySet()
                .stream()
                .filter(entry -> vertexSubset.contains(entry.getKey()))
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue().inducedSubgraph(vertexSubset)))
                .sorted(HIGHEST_DEGREES_FIRST)
                .map(pair -> pair.getKey())
                .collect(Collectors.toList());
    }

    private static Comparator<? super Pair<Integer, List<Edge>>> highestDegreesFirst() {
        return (a, b) -> {
            if (a.getValue().size() > b.getValue().size()) {
                return -1;
            } else if (a.getValue().size() < b.getValue().size()) {
                return 1;
            } else {
                return 0;
            }
        };
    }

    private static class AdjacencyListBuilder implements MandatoryStep, OptionalStep, BuildStep {

        private final Map<Integer, LabelledNeighborList> vertex2Neigbhors;
        private int label;

        private AdjacencyListBuilder() {
            vertex2Neigbhors = new HashMap<>();
        }

        @Override
        public AdjacencyList build() {
            return new LayeredAdjacencyList(vertex2Neigbhors, label);
        }

        @Override
        public OptionalStep withLabel(int label) {
            this.label = label;
            return this;
        }

        @Override
        public AdjacencyListBuilder add(int v, List<Integer> neigbhors) {
            vertex2Neigbhors.put(v, buildLabelledNeighborList(v, label, neigbhors));
            return this;
        }

        @Override
        public OptionalStep add(List<List<Integer>> rawAdjacencyList) {
            for (int nodeIndex = 0; nodeIndex < rawAdjacencyList.size(); nodeIndex++) {
                final List<Edge> edges = new ArrayList<>();
                for (int j = 0; j < rawAdjacencyList.get(nodeIndex).size(); j++) {
                    edges.add(new Edge(nodeIndex, rawAdjacencyList.get(nodeIndex).get(j)));
                }
                final Map<Integer, List<Edge>> label2Neighbors = new HashMap<>();
                label2Neighbors.put(label, edges);
                vertex2Neigbhors.put(nodeIndex, new LabelledNeighborList(nodeIndex, label2Neighbors));
            }

            return this;
        }

        @Override
        public BuildStep done() {
            return this;
        }

        private LabelledNeighborList buildLabelledNeighborList(int v, int label, List<Integer> neighbors) {
            final Map<Integer, List<Edge>> label2Neighbors = new HashMap<>();

            label2Neighbors.put(label, neighbors
                    .stream()
                    .map(neighbor -> new Edge(v, neighbor))
                    .collect(Collectors.toList())
            );

            return new LabelledNeighborList(v, label2Neighbors);
        }

    }

}
