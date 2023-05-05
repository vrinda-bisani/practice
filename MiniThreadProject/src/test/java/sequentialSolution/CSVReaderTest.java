package sequentialSolution;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVReaderTest {
  CSVReader testReader;
  String basePath = System.getProperty("user.dir");
  String testFile;

  @BeforeEach
  void setUp() {
    testFile = basePath +"/src/test/resources/studentVle.csv";
    testReader = new CSVReader();
  }

  @Test
  void readFile() throws CsvValidationException, IOException {
    testReader.readFile(testFile);
    assertEquals(4,testReader.getCsvContent().size());
  }

}
