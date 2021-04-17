package datastructures.graphs;

public class DirectedEdge {

    private final Integer neighborNodeIndex;
    private final Integer neighborNodeValue;
    private final Integer edgeWeight;
    
    public DirectedEdge(Integer neighborNodeIndex, Integer neighborNodeValue, Integer edgeWeight) {
        this.neighborNodeIndex = neighborNodeIndex;
        this.neighborNodeValue = neighborNodeValue;
        this.edgeWeight = edgeWeight;
    }

    public Integer getNeighborNodeIndex() {
        return neighborNodeIndex;
    }

    public Integer getNeighborNodeValue() {
        return neighborNodeValue;
    }

    public Integer getEdgeWeight() {
        return edgeWeight;
    }
    
    @Override
    public String toString() {
        return String.format("(--%d--> %d", edgeWeight, neighborNodeIndex);
    }
    
}
