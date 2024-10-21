package reader;

import java.util.List;

public interface Reader {

    List<String[]> readCsvFile(String fileName);
}
