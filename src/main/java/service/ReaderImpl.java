package service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import reader.Reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReaderImpl implements Reader {

    private static final String BASE_PATH = "src/main/resources/data/";
    public List<String[]> readCsvFile(String fileName) {

        List<String[]> rows = new ArrayList<>();

        Path filePath = Paths.get(BASE_PATH + fileName);

        try(CSVReader csvReader = new CSVReader(Files.newBufferedReader(filePath)))  {
            String[] row;
            while ((row = csvReader.readNext()) != null){
                rows.add(row);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
