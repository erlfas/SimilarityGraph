package inout;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class MyReader {

    public static List<Integer> getTidsserie(File file) throws IOException {
        List<Integer> tidsserie = new ArrayList<>();
        Files.lines(file.toPath()).forEach((String s) -> {
            if (s != null && !StringUtils.isBlank(s)) {
                Integer i = Integer.parseInt(s);
                tidsserie.add(i);
            }
        });
        return tidsserie;
    }

}
