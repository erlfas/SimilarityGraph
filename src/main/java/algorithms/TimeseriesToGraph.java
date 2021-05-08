package algorithms;

import datastructures.graphs.AdjacencyList;
import datastructures.graphs.v2.LayeredAdjacencyList;
import datastructures.vo.Threshold;
import datastructures.vo.ThresholdAbsoluteValue;
import datastructures.vo.ThresholdPercentage;
import datastructures.vo.ThresholdType;
import functional.EdgeCollector;
import functional.SummaryCollector;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static utils.GraphSummaryUtils.summaryMapper;
import utils.SimilarityDefinitions;
import vo.Summary;
import static utils.GraphSummaryUtils.getMaxNumNeigbhors;

public class TimeseriesToGraph {
    
    private final Threshold threshold;
    private Double average;
    private final Integer numNodes;
    private final List<Integer> numSimilarNeighbors;
    private Double totalNumSimilarNeighbors;
    private final List<Integer> series;
    private final int numNeighborsOneWay;
    private final List<List<Integer>> graph;
    private Integer numConnectedComponents;
    private Integer numBridges;
    private final List<BigDecimal> localClusteringCoefficients;
    private Integer numMissingDirectNeighborRelationships;
    private final SimilarityDefinitions similarityDefinition;
    
    public TimeseriesToGraph(List<Integer> tidsserie, Threshold threshold, int numNeighborsOneWay, SimilarityDefinitions similarityDefinition) {
        this.similarityDefinition = similarityDefinition;
        this.threshold = threshold;
        this.series = tidsserie;
        this.numNodes = tidsserie.size();
        this.numNeighborsOneWay = numNeighborsOneWay;
        this.numSimilarNeighbors = new ArrayList<>();
        this.graph = new ArrayList<>();
        for (int i = 0; i < numNodes; ++i) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < numNeighborsOneWay; ++i) { // dummies
            numSimilarNeighbors.add(i, -1);
        }
        this.totalNumSimilarNeighbors = 0.0;
        for (int i = numNeighborsOneWay; i < tidsserie.size() - numNeighborsOneWay; ++i) {
            Integer ant = TimeseriesToGraph.this.getNumSimilarNeighbors(i);
            totalNumSimilarNeighbors = totalNumSimilarNeighbors + ant;
            numSimilarNeighbors.add(i, ant);
        }
        for (int i = tidsserie.size() - numNeighborsOneWay; i < tidsserie.size(); ++i) { // dummies
            numSimilarNeighbors.add(i, -1);
        }

        this.average = 0.0;
        this.average = totalNumSimilarNeighbors / (numNodes - 2 * numNeighborsOneWay);
        this.numConnectedComponents = null;
        this.numBridges = null;
        this.localClusteringCoefficients = new ArrayList<>();
        for (int i = 0; i < numNodes; ++i) {
            localClusteringCoefficients.add(i, getLocalClusteringCoefficientOf(i));
        }

        numMissingDirectNeighborRelationships = 0;
        for (int i = 0; i < numNodes - 1; ++i) {
            if (!graph.get(i).contains(i + 1)) {
                numMissingDirectNeighborRelationships += 1;
            }
        }
    }
    
    public AdjacencyList getAdjacencyList(int cliqueSize) {
        return LayeredAdjacencyList.make(configuration -> configuration
                .withLabel(cliqueSize)
                .add(graph)
                .done()
        );
    }
    
    private Integer getNumSimilarNeighbors(int i) {
        Integer sentrum = series.get(i);
        int antallLikeNaboer = 0;
        for (int j = i - numNeighborsOneWay; j < i; ++j) {
            final Integer neighbor = series.get(j);
            if (areSimilar(sentrum, neighbor)) {
                graph.get(i).add(j);
                ++antallLikeNaboer;
            }
        }
        for (int j = i + 1; j <= i + numNeighborsOneWay; ++j) {
            final Integer neighbor = series.get(j);
            if (areSimilar(sentrum, neighbor)) {
                graph.get(i).add(j);
                ++antallLikeNaboer;
            }
        }
        return antallLikeNaboer;
    }
    
    public boolean areSimilar(Integer sentrum, Integer nabo) {
        if (similarityDefinition == SimilarityDefinitions.SYMMETRIC) {
            return areSimilarSymmetric(sentrum, nabo);
        } else {
            throw new IllegalStateException();
        }
    }
    
    public boolean areSimilarSymmetric(Integer sentrum, Integer nabo) {
        if (this.threshold.getThresholdType() == ThresholdType.ABSOLUTE_VALUE) {
            return Math.abs(sentrum - nabo) < ((ThresholdAbsoluteValue)this.threshold).getAbsoluteValue();
        } else {
            if (sentrum != null && sentrum == 0 && nabo != null && nabo == 0) {
                return true;
            }
            
            double num = Math.max(sentrum, nabo);
            double denum = Math.min(sentrum, nabo);
            double threshold = 1 + ((ThresholdPercentage)this.threshold).getPercentage(); // 1 + 0.20 = 1.2
            double toCompare = num / denum;
            return toCompare < threshold;
        }
    }
    
    public static boolean areSimilarSymmetric(Integer sentrum, Integer nabo, Threshold thresholdInput) {
        if (thresholdInput.getThresholdType() == ThresholdType.ABSOLUTE_VALUE) {
            return Math.abs(sentrum - nabo) < ((ThresholdAbsoluteValue)thresholdInput).getAbsoluteValue();
        } else {
            double num = Math.max(sentrum, nabo);
            double denum = Math.min(sentrum, nabo);
            double threshold = 1 + ((ThresholdPercentage)thresholdInput).getPercentage(); // 1 + 0.20 = 1.2
            double toCompare = num / denum;
            return toCompare < threshold;
        }
    }
    
    public Double getAverage() {
        return new Double(average.toString());
    }
    
    public Integer getNumNodes() {
        return new Integer(numNodes.toString());
    }
    
    public List<Integer> getNumSimilarNeighbors() {
        List<Integer> res = new ArrayList<>();
        for (Integer i : numSimilarNeighbors)
            res.add(new Integer(i.toString()));
        return res;
    }
    
    public Integer getNumConnectedComponents() {
        if (numConnectedComponents == null) {
            numConnectedComponents = new ConnectedComponents(graph).getNumConnectedComponents();
        }
        return numConnectedComponents;
    }
    
    public Map<Integer,Summary> getNumNeighborsToSummary() {
        return graph.stream().map(summaryMapper).collect(new SummaryCollector(getMaxNumNeigbhors.apply(graph)));
    }
    
    public Map<Integer,Integer> getAntKanterTilAntNoder() {
        return graph.stream().collect(new EdgeCollector());
    }
    
    public Integer getNumBridges() {
        if (numBridges == null) {
            numBridges = new BridgeFinder(graph).getNumBridges();
        }
        
        return numBridges;
    }
    
    public List<BigDecimal> getLocalClusteringCoefficients() {
        return Collections.unmodifiableList(localClusteringCoefficients);
    }

    public Integer getNumMissingDirectNeighborRelationships() {
        return numMissingDirectNeighborRelationships;
    }
    
    private BigDecimal getLocalClusteringCoefficientOf(int i) {
        int numNeighborEdges = 0;
        
        for (int j = 0; j < graph.get(i).size(); ++j) {
            for (int k = j + 1; k < graph.get(i).size(); ++k) {
                if (isEdge(graph.get(i).get(j), graph.get(i).get(k))) {
                    ++numNeighborEdges;
                }
            }
        }
        
        int degreeOfI = graph.get(i).size();
        
        BigDecimal num = new BigDecimal("2").multiply(new BigDecimal(numNeighborEdges));
        BigDecimal denom = new BigDecimal(degreeOfI).multiply(new BigDecimal(degreeOfI - 1));
        
        try {
            BigDecimal coefficient = num.divide(denom, 2, RoundingMode.HALF_UP);
            return coefficient;
        } catch (Exception e) {
            return new BigDecimal("-1");
        }
    }
    
    private boolean isEdge(int i, int j) {
        return graph.get(i).stream().anyMatch((k) -> (k == j));
    }
}
