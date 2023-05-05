package sequentialSolution;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exceptions.InvalidInputException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class SequentialMainTest {
  private String[] args = new String[]{};
  private String basePath = System.getProperty("user.dir");
  private String testDirectoryPath = "/src/test/resources";
  @Test
  void mainNoArgs() {
    SequentialMain sequentialmain = new SequentialMain();
    assertThrows(InvalidInputException.class, () -> SequentialMain.main(args));
  }

  @Test
  void mainWithArgs() throws InvalidInputException, CsvValidationException, IOException {
    args = new String[]{basePath + testDirectoryPath};
    SequentialMain.main(args);
    assertTrue(Files.exists(Path.of(basePath + testDirectoryPath +"/AAA_2013J.csv")));
    assertTrue(Files.exists(Path.of(basePath + testDirectoryPath + "/GGG_2014J.csv")));
  }
}