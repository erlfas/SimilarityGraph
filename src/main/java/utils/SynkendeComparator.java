package utils;

import java.util.Comparator;

public class SynkendeComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer a, Integer b) {
        Integer result = a.compareTo(b);
        if (result == 0) {
            return 0;
        } else if (result < 0) {
            return 1;
        } else {
            return -1;
        }
    }
    
}
