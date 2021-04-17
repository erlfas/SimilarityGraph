package interfaces;

import java.io.File;
import java.util.List;

@FunctionalInterface
public interface FileReader {

    List<Integer> read(File file);
    
}
