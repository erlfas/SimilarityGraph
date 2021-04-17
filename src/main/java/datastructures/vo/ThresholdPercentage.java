package datastructures.vo;

public class ThresholdPercentage implements Threshold {
    
    private final Double value;
    
    // ThresholdType.PERCENTAGE
    // value / 100.0
    // percentage / 100.0
    // e.g.: 5 / 100.0

    public ThresholdPercentage(Double value) {
        this.value = value;
    }

    @Override
    public ThresholdType getThresholdType() {
        return ThresholdType.PERCENTAGE;
    }

    public Double getPercentage() {
        return value / 100.0;
    }

    @Override
    public Integer getRawInput() {
        return value.intValue();
    }
    
}
