package datastructures.graphs;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CompleteSubgraph implements Comparable<CompleteSubgraph> {
    
    private final TreeSet<Integer> vertices;
    
    public CompleteSubgraph(Set<Integer> vertices) {
        this.vertices = new TreeSet<>(vertices);
    }
    
    public Set<Integer> getVertices() {
        return new TreeSet<>(vertices);
    }
    
    public int size() {
        return vertices.size();
    }
    
    public boolean contains(Integer... testVertices){
        for (Integer v : testVertices) {
            if (!vertices.contains(v)) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return vertices.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.vertices);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompleteSubgraph other = (CompleteSubgraph) obj;
        
        return this.vertices.containsAll(other.vertices) && other.vertices.containsAll(this.vertices);
    }

    @Override
    public int compareTo(CompleteSubgraph other) {
        return this.toString().compareTo(other.toString());
    }
    
}
