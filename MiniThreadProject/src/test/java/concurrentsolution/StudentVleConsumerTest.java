package concurrentsolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentVleConsumerTest {
  private StudentVleConsumer studentConsumerTest;
  private BlockingQueue<String> testSharedBuffer;
  private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> testThresholdMap;
  private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> testTotalClickPerDate;
  private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> testExpThresholdMap;
  private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> testExpTotalClickPerDate;
  private String testPoison;
  private Integer testThreshold;

  @BeforeEach
  void setUp() {
    testSharedBuffer = new LinkedBlockingQueue<>();
    testThresholdMap = new ConcurrentSkipListMap<>();
    testTotalClickPerDate = new ConcurrentSkipListMap<>();
    testThreshold = 20;
    testPoison = "PoisonPill";

    ConcurrentMap<Integer, Integer> dateMap = new ConcurrentSkipListMap<>();
    dateMap.put(-10, 25);

    testThresholdMap.put("AAA_2013B",dateMap);
    testTotalClickPerDate.put("AAA_2013B",dateMap);

    testExpThresholdMap = new ConcurrentSkipListMap<>();
    testExpThresholdMap.put("AAA_2013B",dateMap);

    testExpTotalClickPerDate = new ConcurrentSkipListMap<>();
    testExpTotalClickPerDate.put("AAA_2013B",dateMap);

    studentConsumerTest = new StudentVleConsumer(testSharedBuffer, testPoison, testThreshold, testThresholdMap, testTotalClickPerDate);
  }

  @Test
  void getThresholdTest() {
    assertEquals(20, studentConsumerTest.getThreshold());
  }

  @Test
  void testEquals_Self() {
    assertTrue(studentConsumerTest.equals(studentConsumerTest));
  }

  @Test
  void testEquals_null() {
    assertFalse(studentConsumerTest.equals(null));
  }

  @Test
  void testEquals_diffType() {
    assertFalse(studentConsumerTest.equals(new String("Test")));
  }

  @Test
  void testEquals_Similar() {
    StudentVleConsumer dupConsumer = new StudentVleConsumer(testSharedBuffer, testPoison, testThreshold, testExpThresholdMap, testExpTotalClickPerDate);
    assertTrue(studentConsumerTest.equals(dupConsumer));
  }

  @Test
  void testEquals_diffTestBuffer() {
    StudentVleConsumer dupConsumer = new StudentVleConsumer(null, testPoison, testThreshold, testExpThresholdMap, testExpTotalClickPerDate);
    assertTrue(studentConsumerTest.equals(dupConsumer));
  }

  @Test
  void testEquals_diffTestPoison() {
    StudentVleConsumer dupConsumer = new StudentVleConsumer(testSharedBuffer, null, testThreshold, testExpThresholdMap, testExpTotalClickPerDate);
    assertFalse(studentConsumerTest.equals(dupConsumer));
  }

  @Test
  void testEquals_diffTestThreshold() {
    StudentVleConsumer dupConsumer = new StudentVleConsumer(testSharedBuffer, testPoison, null, testExpThresholdMap, testExpTotalClickPerDate);
    assertFalse(studentConsumerTest.equals(dupConsumer));
  }

  @Test
  void testEquals_diffThresholdMap() {
    StudentVleConsumer dupConsumer = new StudentVleConsumer(testSharedBuffer, testPoison, testThreshold, null, testExpTotalClickPerDate);
    assertFalse(studentConsumerTest.equals(dupConsumer));
  }

  @Test
  void testEquals_diffTotalClick() {
    StudentVleConsumer dupConsumer = new StudentVleConsumer(testSharedBuffer, testPoison, testThreshold, testExpThresholdMap, null);
    assertFalse(studentConsumerTest.equals(dupConsumer));
  }

  @Test
  void testHashCode() {
    int expHash = Objects.hash(Objects.hash(testThreshold, testPoison), testExpThresholdMap, testExpTotalClickPerDate);
    assertEquals(expHash, studentConsumerTest.hashCode());
  }

  @Test
  void testToString() {
    String expString = "StudentVleConsumer{" +
        "thresholdMap=" + testExpThresholdMap +
        ", totalClickPerDate=" + testExpTotalClickPerDate +
        '}';
    assertEquals(expString, studentConsumerTest.toString());
  }
}