package datastructures.graphs;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Edge {

    private final Integer source;
    private final Integer target;
    private final Set<Integer> vertices;

    public Edge(Integer a, Integer b) {
        if (a == null || a < 0) {
            throw new IllegalArgumentException();
        }

        if (b == null || b < 0) {
            throw new IllegalArgumentException();
        }

        this.vertices = new TreeSet<>();
        this.vertices.add(a);
        this.vertices.add(b);
        
        final List<Integer> list = this.vertices.stream().collect(Collectors.toList());

        this.source = list.get(0);
        this.target = list.get(1);
    }

    public Set<Integer> getVertices() {
        return new TreeSet<>(vertices);
    }

    /**
     * The node with the least index
     * 
     * @return 
     */
    public Integer getSource() {
        return source;
    }

    /**
     * The node with the highest index.
     * 
     * @return 
     */
    public Integer getTarget() {
        return target;
    }
    
    public boolean hasNode(int v) {
        return source.equals(v) || target.equals(v);
    }
    
    /**
     * Given that v is equal to either source or target, return the other one.
     * 
     * @param v
     * @return 
     */
    public Integer getOtherThan(Integer v) {
        return !source.equals(v) ? source : target;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (!Edge.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        
        final Edge other = (Edge) obj;
        
        return this.source.equals(other.getSource()) && this.target.equals(other.getTarget());
    }

    @Override
    public int hashCode() {
        final List<Integer> list = this.vertices.stream().collect(Collectors.toList());
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(list.get(0));
        hash = 83 * hash + Objects.hashCode(list.get(1));
        return hash;
    }

    @Override
    public String toString() {
        return "(" + source + "," + target + ")";
    }

}
