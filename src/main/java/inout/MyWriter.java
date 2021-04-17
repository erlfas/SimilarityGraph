package inout;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import vo.Summary;

public class MyWriter {
    
    public static boolean writeMultigraphSummary(String fileName, Map<Integer,Summary> antNaboerTilSummary, List<Integer> numSimilarNeighborsPerNode) {
        final Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("Antall naboer\tAntall noder\tAkkumulert sum\n");
            
            for (Entry<Integer,Summary> entry : antNaboerTilSummary.entrySet()) {
                final Summary summary = entry.getValue();
                writer.write(summary.getAntallNaboer() + "\t" + summary.getAntallNoder() + "\t" + summary.getAkkumulertSum() + "\n");
            }
            
            writer.write("\n");
            writer.write("# similar neighbors for each node\n");
            for (int i = 0; i < numSimilarNeighborsPerNode.size(); i++) {
                writer.write(i + "\t" + numSimilarNeighborsPerNode.get(i) + "\n");
            }
            
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean writeShortSummary(String fileName, Map<Integer,Summary> antNaboerTilSummary, List<BigDecimal> localClusteringCoefficients) {
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("Antall naboer\tAntall noder\tAkkumulert sum\n");
            
            for (Entry<Integer,Summary> entry : antNaboerTilSummary.entrySet()) {
                Summary summary = entry.getValue();
                writer.write(summary.getAntallNaboer() + "\t" + summary.getAntallNoder() + "\t" + summary.getAkkumulertSum() + "\n");
            }
            
            writer.write("\n");
            writer.write("Node\tLocal clustering coefficient\n");
            for (int i = 0; i < localClusteringCoefficients.size(); ++i) {
                writer.write(i + "\t" + localClusteringCoefficients.get(i) + "\n");
            }
            
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean writeSummary(String fileName, List<Integer> antallNaboerListe, Map<Integer,Summary> antNaboerTilSummary) {
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {   
            for (int i = 0; i < antallNaboerListe.size(); ++i) {
                writer.write(antallNaboerListe.get(i) + "\n");
            }
            
            writer.write("\n");
            
            writer.write("Antall naboer\tAntall noder\tAkkumulert sum\n");
            
            for (Entry<Integer,Summary> entry : antNaboerTilSummary.entrySet()) {
                Summary summary = entry.getValue();
                writer.write(summary.getAntallNaboer() + "\t" + summary.getAntallNoder() + "\t" + summary.getAkkumulertSum() + "\n");
            }
            
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }
    
    public static <T extends Number> boolean writeStierMap(String fileName, Map<Integer, List<T>> numEquals, List<Integer> tidsserie) {
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) { 
            
            writer.write("Pst");
            writer.write(";");
            for (int i = 0; i < tidsserie.size() - 1; ++i) {
                writer.write(String.valueOf(tidsserie.get(i)));
                writer.write(";");
            }
            writer.write(String.valueOf(tidsserie.get(tidsserie.size()-1)));
            writer.write("\n");
            
            for (Entry<Integer, List<T>> entry : numEquals.entrySet().stream().sorted((x,y) -> x.getKey().compareTo(y.getKey())).collect(Collectors.toList())) {
                final Integer pst = entry.getKey();
                final List<T> counts = entry.getValue();
                
                writer.write(String.valueOf(pst));
                writer.write(";");
                for (int i = 0; i < counts.size() - 1; ++i) {
                    writer.write(String.valueOf(counts.get(i)));
                    writer.write(";");
                }
                writer.write(String.valueOf(counts.get(counts.size()-1)));
                writer.write("\n");
            }
            
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean writeDirectedNeighborhoodOutput(String fileName, 
            List<Integer> posSums, List<Integer> negSums, Double gjsnSumPos, Double gjsnSumNeg,
            Integer minSum, Integer maxSum, Integer minAbsoluteSum, Integer maxAbsoluteSum) {
        
        
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {  
            writer.write("Absolutte summer: \n");
            for (int i = 0; i < posSums.size(); ++i) {
                writer.write(posSums.get(i) + "\n");
            }
            writer.write("Gjennomsnitt: " + gjsnSumPos + "\n");
            writer.write("Minimumssum:" + minAbsoluteSum + "\n");
            writer.write("Maksimumssum:" + maxAbsoluteSum + "\n");
            
            writer.write("\n");
            
            writer.write("Summer (som kan være negative): \n");
            for (int i = 0; i < negSums.size(); ++i) {
                writer.write(negSums.get(i) + "\n");
            }
            writer.write("Gjennomsnitt: " + gjsnSumNeg + "\n");
            writer.write("Minimumssum:" + minSum + "\n");
            writer.write("Maksimumssum:" + maxSum + "\n");
            
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean writeList(String fileName, List<Integer> tall) {
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {   
            for (int i = 0; i < tall.size(); ++i) {
                writer.write(tall.get(i) + "\n");
            }
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean writeIntArray(String fileName, int[] tall) {
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {   
            for (int i = 0; i < tall.length; ++i) {
                writer.write(tall[i] + "\n");
            }
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }

    public static boolean write(String fileName, List<List<Integer>> tidsserier) {
        Path path = Paths.get(fileName);
        StringBuilder sb = new StringBuilder("");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {   
            int antallTall = tidsserier.get(1).size();
            int antallTidsserier = tidsserier.size();
            
            for (int prs = 1; prs <= 100; ++prs) {
                sb.append("Prs" + prs + "\t");
            }
            sb.append("\n");
            
            for (int tallIndex = 0; tallIndex < antallTall; ++tallIndex) {
                for (int i = 1; i < antallTidsserier; ++i) {
                    sb.append(tidsserier.get(i).get(tallIndex) + "\t");
                }
                sb.append("\n");
            }
            writer.write(sb.toString());
        } catch (IOException ex) {
            return false;
        }
        
        return true;
    }

}
