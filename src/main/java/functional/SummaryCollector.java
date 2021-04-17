package functional;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import vo.Summary;

public class SummaryCollector implements Collector<Summary,SummaryCombiner,Map<Integer,Summary>> {
    
    private static final Set<Characteristics> characteristics = Collections.emptySet();
    
    private int maxAntallNaboer;
    
    public SummaryCollector(int maxAntallNaboer) {
        this.maxAntallNaboer = maxAntallNaboer;
    }

    @Override
    public Supplier<SummaryCombiner> supplier() {
        return () -> new SummaryCombiner(maxAntallNaboer);
    }

    @Override
    public BiConsumer<SummaryCombiner, Summary> accumulator() {
        return SummaryCombiner::add;
    }

    @Override
    public BinaryOperator<SummaryCombiner> combiner() {
        return SummaryCombiner::combine;
    }

    @Override
    public Function<SummaryCombiner, Map<Integer, Summary>> finisher() {
        return SummaryCombiner::toMap;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return characteristics;
    }
    
}
