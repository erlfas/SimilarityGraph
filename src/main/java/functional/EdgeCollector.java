package functional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class EdgeCollector implements Collector<List<Integer>, NeighborListCombiner, Map<Integer,Integer>> {

    private static final Set<Characteristics> characteristics = Collections.emptySet();
    
    @Override
    public Supplier<NeighborListCombiner> supplier() {
        return () -> new NeighborListCombiner();
    }

    @Override
    public BiConsumer<NeighborListCombiner, List<Integer>> accumulator() {
        return NeighborListCombiner::add;
    }

    @Override
    public BinaryOperator<NeighborListCombiner> combiner() {
        return NeighborListCombiner::combine;
    }

    @Override
    public Function<NeighborListCombiner, Map<Integer, Integer>> finisher() {
        return NeighborListCombiner::toMap;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return characteristics;
    }
    
}
