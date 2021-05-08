package algoritmer;

import algorithms.TimeseriesToGraph;
import datastructures.vo.Threshold;
import datastructures.vo.ThresholdPercentage;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author erlend321
 */
public class TidsserieTilNettverkTest {

    public TidsserieTilNettverkTest() {
    }
    
    @Test
    public void areSimilarAsymmetricTest() {
        final Threshold threshold = new ThresholdPercentage(Double.valueOf("1.5"));
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 100, threshold), true);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 101, threshold), true);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 102, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 103, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 104, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 105, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 106, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 107, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 108, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 109, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 110, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 111, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 112, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 113, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 114, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 115, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 116, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 117, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 118, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 119, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 120, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 121, threshold), false);
        assertEquals(TimeseriesToGraph.areSimilarSymmetric(100, 122, threshold), false);
    }
}
