package concurrentsolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterRunnableTest {
  private WriterRunnable writerRunnableTest;
  private String directoryPath;
  private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> totalClickPerDate;
  @BeforeEach
  void setUp() {
    directoryPath = "src/test/resources/mainclass";
    ConcurrentMap<Integer, Integer> dateMap = new ConcurrentSkipListMap<>();
    dateMap.put(-10, 25);
    totalClickPerDate = new ConcurrentSkipListMap<>();
    totalClickPerDate.put("AAA_2013B",dateMap);

    writerRunnableTest = new WriterRunnable(directoryPath, totalClickPerDate);
  }

  @Test
  void testEquals_self() {
    assertTrue(writerRunnableTest.equals(writerRunnableTest));
  }

  @Test
  void testEquals_null() {
    assertFalse(writerRunnableTest.equals(null));
  }

  @Test
  void testEquals_diffType() {
    assertFalse(writerRunnableTest.equals(new String("Test")));
  }

  @Test
  void testEquals_Similar() {
    WriterRunnable dupRunnable = new WriterRunnable(directoryPath, totalClickPerDate);
    assertTrue(writerRunnableTest.equals(dupRunnable));
  }

  @Test
  void testEquals_diffPath() {
    WriterRunnable dupRunnable = new WriterRunnable("", totalClickPerDate);
    assertFalse(writerRunnableTest.equals(dupRunnable));
  }

  @Test
  void testEquals_diffClick() {
    WriterRunnable dupRunnable = new WriterRunnable(directoryPath, new ConcurrentSkipListMap<>());
    assertFalse(writerRunnableTest.equals(dupRunnable));
  }

  @Test
  void testHashCode() {
    int expHash = Objects.hash(directoryPath, totalClickPerDate);
    assertEquals(expHash, writerRunnableTest.hashCode());
  }

  @Test
  void testToString() {
    String expString = "WriterRunnable{" +
        "directoryPath='" + directoryPath + '\'' +
        ", totalClickPerDate=" + totalClickPerDate +
        '}';
    assertEquals(expString, writerRunnableTest.toString());
  }
}