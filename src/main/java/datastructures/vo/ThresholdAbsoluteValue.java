
package datastructures.vo;

public class ThresholdAbsoluteValue implements Threshold {

    // ThresholdType.MILLISECONDS
    // | 98 - 103 | =  5     <     7 milliseconds = absolute value
    
    private final Integer value;
    
    public ThresholdAbsoluteValue(Integer value) {
        this.value = value;
    }

    @Override
    public ThresholdType getThresholdType() {
        return ThresholdType.ABSOLUTE_VALUE;
    }

    public Integer getAbsoluteValue() {
        return value;
    }

    @Override
    public Integer getRawInput() {
        return value;
    }
    
}
