package datastructures.graphs.v2;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class VertexPair {

    private final Set<Integer> set;

    public VertexPair(int i, int j) {
        this.set = new HashSet<>();
        this.set.add(i);
        this.set.add(j);
    }

    public List<Integer> getVertices() {
        return this.set.stream().collect(Collectors.toList());
    }

    @Override
    public String toString() {
        final List<Integer> vertices = getVertices();
        return "VertexPair{" + vertices + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.set.hashCode();
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
        final VertexPair other = (VertexPair) obj;
        if (!Objects.equals(this.set, other.set)) {
            return false;
        }
        return true;
    }

}
