package concurrentsolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThresholdRunnableTest {
  private ThresholdRunnable runnableTest;
  private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> thresholdTtlClick;
  private String directoryPath;
  private Integer threshold;
  @BeforeEach
  void setUp() {
    threshold = 10;
    directoryPath = "src/test/resources/mainclass";
    ConcurrentMap<Integer, Integer> dateMap = new ConcurrentSkipListMap<>();
    dateMap.put(-10, 25);
    thresholdTtlClick = new ConcurrentSkipListMap<>();
    thresholdTtlClick.put("AAA_2013B", dateMap);
    runnableTest = new ThresholdRunnable(thresholdTtlClick, directoryPath, threshold);
  }
  @Test
  void testEquals_self() {
    assertTrue(runnableTest.equals(runnableTest));
  }

  @Test
  void testEquals_null() {
    assertFalse(runnableTest.equals(null));
  }

  @Test
  void testEquals_diffType() {
    assertFalse(runnableTest.equals(new String("Test")));
  }

  @Test
  void testEquals_Similar() {
    ThresholdRunnable dupRunnable = new ThresholdRunnable(thresholdTtlClick, directoryPath, threshold);
    assertTrue(runnableTest.equals(dupRunnable));
  }

  @Test
  void testEquals_diffClick() {
    ThresholdRunnable dupRunnable = new ThresholdRunnable(new ConcurrentSkipListMap<>(), directoryPath, threshold);
    assertFalse(runnableTest.equals(dupRunnable));
  }

  @Test
  void testEquals_diffPath() {
    ThresholdRunnable dupRunnable = new ThresholdRunnable(thresholdTtlClick, "", threshold);
    assertFalse(runnableTest.equals(dupRunnable));
  }

  @Test
  void testEquals_diffThreshold() {
    ThresholdRunnable dupRunnable = new ThresholdRunnable(thresholdTtlClick, directoryPath, 300);
    assertFalse(runnableTest.equals(dupRunnable));
  }

  @Test
  void testHashCode() {
    int expHash = Objects.hash(thresholdTtlClick, directoryPath, threshold);
    assertEquals(expHash, runnableTest.hashCode());
  }

  @Test
  void testToString() {
    String expString = "ThresholdRunnable{" +
        "thresholdTtlClick=" + thresholdTtlClick +
        ", directoryPath='" + directoryPath + '\'' +
        ", threshold=" + threshold +
        '}';
    assertEquals(expString, runnableTest.toString());
  }
}