package datastructures.graphs;

public class Node<V> {
    
    private Integer index;
    private V value;

    public Node(Integer index, V value) {
        this.index = index;
        this.value = value;
    }

    public Integer getIndex() {
        return index;
    }

    public V getValue() {
        return value;
    }
}
