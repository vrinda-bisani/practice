package concurrentsolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exceptions.InvalidInputException;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CLIParserTest {
  private CLIParser parserTest;
  private String[] expArgs;
  @BeforeEach
  void setUp() {
    expArgs = new String[] {"src/test/resources/mainclass",String.valueOf(20)};
    parserTest = new CLIParser(new String[] {"src/test/resources/mainclass",String.valueOf(20)});
  }

  @Test
  void getStudentVleCsvTest() {
    assertEquals("src/test/resources/mainclass/studentVle.csv", parserTest.getStudentVleCsv());
  }

  @Test
  void getCoursesVleCsvTest() {
    assertEquals("src/test/resources/mainclass/courses.csv", parserTest.getCoursesVleCsv());
  }

  @Test
  void getThresholdTest() {
    assertEquals(20, parserTest.getThreshold());
  }

  @Test
  void getDirectoryPathTest() {
    assertEquals("src/test/resources/mainclass", parserTest.getDirectoryPath());
  }

  @Test
  void testEquals_null() {
    assertFalse(parserTest.equals(null));
  }

  @Test
  void testEquals_diffType() {
    assertFalse(parserTest.equals(new String("test")));
  }

  @Test
  void testEquals_similar() {
    CLIParser dupParser = new CLIParser(expArgs);
    assertTrue(parserTest.equals(dupParser));
  }

  @Test
  void testEquals_diffThreshold() {
    CLIParser diffParser = new CLIParser(new String[] {"src/test/resources/mainclass",String.valueOf(30)});
    assertFalse(parserTest.equals(diffParser));
  }

  @Test
  void testEquals_diffDir() {
    CLIParser diffParser = new CLIParser(new String[] {"src/test/resources/writerclass",String.valueOf(20)});
    assertFalse(parserTest.equals(diffParser));
  }

  @Test
  void testEquals_self() {
    assertTrue(parserTest.equals(parserTest));
  }

  @Test
  void testHashCode() {
    int expHash = Objects.hash("src/test/resources/mainclass",20);
    assertEquals(expHash, parserTest.hashCode());
  }

  @Test
  void testInvalidInputException() {
    assertThrows(InvalidInputException.class, ()->{
      CLIParser dupParserTest = new CLIParser(new String[] {"src/test/resources/main",String.valueOf(20)});
    });
  }

  @Test
  void testInvalidInputExceptionNumber() {
    assertThrows(InvalidInputException.class, ()->{
      CLIParser dupParserTest = new CLIParser(new String[] {"src/test/resources/mainclass","a"});
    });
  }

  @Test
  void testInvalidInputExceptionLessArg() {
    assertThrows(InvalidInputException.class, ()->{
      CLIParser dupParserTest = new CLIParser(new String[] {"src/test/resources/mainclass"});
    });
  }

  @Test
  void testInvalidInputExceptionNegThresh() {
    assertThrows(InvalidInputException.class, ()->{
      CLIParser dupParserTest = new CLIParser(new String[] {"src/test/resources/mainclass", "-20"});
    });
  }

  @Test
  void testToString() {
    String expString = "CLIParser{" +
        "directoryPath='" + "src/test/resources/mainclass" + '\'' +
        ", threshold=" + 20 +
        '}';

    assertEquals(expString, parserTest.toString());
  }
}