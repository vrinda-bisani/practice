package sequentialSolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exceptions.InvalidInputException;
import java.io.File;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {
  private String testPath;
  CommandLineParser cli;
  String basePath = System.getProperty("user.dir");

  @Test
  void CLIConstructorTest1(){
    assertThrows(InvalidInputException.class, () -> new CommandLineParser("testDirectory"));
  }

  @Test
  void CLIConstructorTest2(){
    assertThrows(InvalidInputException.class, () -> new CommandLineParser(basePath + "/src/test/resources/studentVle.csv"));
  }

  @Test
  void testGetDirectoryPath() throws InvalidInputException {
    testPath = basePath + File.separator + "/src/test/resources";
    cli = new CommandLineParser(testPath);
    assertEquals(testPath, cli.getDirectoryPath());
  }
}