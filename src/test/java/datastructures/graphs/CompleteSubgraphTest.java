package datastructures.graphs;

import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.testng.annotations.Test;

public class CompleteSubgraphTest {
    
    @Test
    public void toStringTest() {
        assertThat(subgraph(1,3,7,9,12).toString(), is(equalTo("1,3,7,9,12")));
        assertThat(subgraph(13,7912).toString(), is(equalTo("13,7912")));
    }
    
    @Test
    public void equalsTest() {
        assertTrue(subgraph(1,2,3).equals(subgraph(1,2,3)));
        assertTrue(subgraph(1,2,3).equals(subgraph(3,2,1)));
        
        assertFalse(subgraph(1,2,3).equals(subgraph(1)));
        assertFalse(subgraph(1,2,3).equals(subgraph(2)));
        assertFalse(subgraph(1,2,3).equals(subgraph(3)));
        assertFalse(subgraph(1,2,3).equals(subgraph(1,2)));
        assertFalse(subgraph(1,2,3).equals(subgraph(1,2,4)));
    }

    @Test
    public void compareToTest() {
        assertThat(subgraph(1,2,3).compareTo(subgraph(1,2,3)), is(equalTo(0)));
        assertThat(subgraph(1,2,3).compareTo(subgraph(3,2,1)), is(equalTo(0)));
        assertThat(subgraph(1,2,3).compareTo(subgraph(1,2,3,4)), is(equalTo(-2)));
        assertThat(subgraph(1,2,3,4).compareTo(subgraph(1,2,3)), is(equalTo(2)));
        assertThat(subgraph(1,2,3).compareTo(subgraph(4,5,6)), is(equalTo(-3)));
        assertThat(subgraph(4,5,6).compareTo(subgraph(1,2,3)), is(equalTo(3)));
    }
    
    private CompleteSubgraph subgraph(Integer... vertices) {
        return new CompleteSubgraph(withVertices(vertices));
    }
    
    private Set<Integer> withVertices(Integer... vertices) {
        final Set<Integer> set = new HashSet<>();
        
        for (Integer v : vertices) {
            set.add(v);
        }
        
        return set;
    }
    
}
