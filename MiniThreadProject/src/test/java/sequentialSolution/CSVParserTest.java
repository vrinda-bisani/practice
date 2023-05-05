package sequentialSolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CSVParserTest {
  private static String basePath = System.getProperty("user.dir");
  private static String testDirectoryPath;

  @BeforeAll
  static void setUp() throws CsvValidationException, IOException {
    testDirectoryPath = basePath + "/src/test/resources";
    new CSVParser(testDirectoryPath);
  }

  @Test
  void testFilesExists(){
    assertTrue(Files.exists(Path.of(testDirectoryPath + "/AAA_2013J.csv")));
    assertTrue(Files.exists(Path.of(testDirectoryPath + "/GGG_2014J.csv")));
  }

  @Test
  void testFilesContents() throws IOException, CsvException {
    List<String[]> csvContentActual = testReader(testDirectoryPath + "/AAA_2013J.csv");
    assertEquals(3, csvContentActual.size());
  }

  private List<String[]> testReader(String fileName) throws IOException, CsvException {
    try (Reader reader = Files.newBufferedReader(Path.of(fileName))){
      try (com.opencsv.CSVReader csvReader = new com.opencsv.CSVReader(reader)) {
        return csvReader.readAll();
      }
    }
  }


}