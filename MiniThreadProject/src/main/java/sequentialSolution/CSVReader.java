package sequentialSolution;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to read CSV file
 */
public class CSVReader {
  private final List<String[]> csvContent;

  /**
   * Constructs csv content
   */
  public CSVReader() {
    this.csvContent = new ArrayList<>();
  }

  /**
   * Reads CSV file
   * @param csvFile path the CSV file
   * @throws IOException if CSV file is invalid
   * @throws CsvValidationException if CSV file is invalid
   */
  public void readFile(String csvFile) throws IOException, CsvValidationException {
    try (Reader reader = Files.newBufferedReader(Path.of(csvFile))) {
      try (com.opencsv.CSVReader csvReader = new com.opencsv.CSVReader(reader)) {
        String[] line;
        csvReader.readNext();
        while ((line = csvReader.readNext()) != null) {
          this.csvContent.add(line);
        }
      }
    }
  }

  /**
   * Returns csv content
   * @return csv content
   */
  public List<String[]> getCsvContent() {
    return this.csvContent;
  }
}
