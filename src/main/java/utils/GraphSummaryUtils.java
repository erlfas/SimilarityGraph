package utils;

import functional.EdgeCollector;
import functional.SummaryCollector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import vo.Summary;

public class GraphSummaryUtils {

    public static final Function<String,Optional<Integer>> stringToInt = (maybeInt) -> {
        try {
            Integer i = Integer.parseInt(maybeInt);
            return Optional.of(i);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    };
    
    public static Map<Integer,Summary> getAntNaboerTilSummary(Map<Integer, Map<Integer, Integer>> graph, int antallNaboerHverVei) {
        
        final List<Map.Entry<Integer, Map<Integer, Integer>>> entries = graph.entrySet().stream().collect(Collectors.toList());
        final List<Map<Integer, Integer>> neighborMaps = new ArrayList<>();
        for (int i = antallNaboerHverVei; i < entries.size() - antallNaboerHverVei; i++) {
            final Map.Entry<Integer, Map<Integer, Integer>> entry = entries.get(i);
            neighborMaps.add(entry.getValue());
        }
        
        return neighborMaps
                .stream()
                .map(x -> new Summary(x.size(), 1, 0))
                .collect(new SummaryCollector(
                        getMaxNumNeighborsMultigraph.apply(graph.values().stream().collect(Collectors.toList()))
                ));
    }
    
    public static final Function<Map.Entry<Integer, Map<Integer, Integer>>,Summary> summaryMultigraphMapper = (entry) -> {
        return new Summary(entry.getValue().size(), 1, 0);
    };
    
    public static final Function<List<Map<Integer, Integer>>,Integer> getMaxNumNeighborsMultigraph = (list) -> {
        return list.stream().mapToInt(x -> x.size()).max().orElse(0);
    };
    
    public static final Function<List<Integer>,Summary> summaryMapper = (naboliste) -> {
        return new Summary(naboliste.size(), 1, 0);
    };
    
    public static final Function<List<List<Integer>>,Integer> getMaxNumNeigbhors = (graph) -> {
        return graph.stream().mapToInt(naboliste -> naboliste.size()).max().orElse(0);
    };
    
    public static final Function<List<List<Integer>>,Integer> getMaxGradtall = (graph) -> {
        return graph.stream().mapToInt(naboliste -> naboliste.size()).max().orElse(0);
    };
    
    public static final Function<List<List<Integer>>,Map<Integer,Summary>> mapAntNaboerTilSummary = (graph) -> {
        return graph.stream().map(summaryMapper).collect(new SummaryCollector(getMaxGradtall.apply(graph)));
    };
    
    public static final Function<List<List<Integer>>,Map<Integer,Integer>> mapAntKanterTilAntNoder = (graph) -> {
        return graph.stream().collect(new EdgeCollector());
    };
}
