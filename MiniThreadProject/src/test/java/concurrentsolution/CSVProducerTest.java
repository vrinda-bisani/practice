package concurrentsolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVProducerTest {
  private CSVProducer producerTest;
  private BlockingQueue<String> sharedBufferTest;
  private String poisonTest;
  private String  csvFilePathTest;
  @BeforeEach
  void setUp() {
    sharedBufferTest = new LinkedBlockingQueue<>();
    csvFilePathTest = "src/test/resources/consumerclass/studentVle.csv";
    poisonTest = "PoisonPill";
    producerTest = new CSVProducer(sharedBufferTest, poisonTest, csvFilePathTest);
  }

  @Test
  void testEquals_Self() {
    assertTrue(producerTest.equals(producerTest));
  }

  @Test
  void testEquals_null() {
    assertFalse(producerTest.equals(null));
  }

  @Test
  void testEquals_diffType() {
    assertFalse(producerTest.equals(new String("test")));
  }

  @Test
  void testEquals_similarObj() {
    CSVProducer dupProducerTest = new CSVProducer(sharedBufferTest, poisonTest, csvFilePathTest);
    assertTrue(producerTest.equals(dupProducerTest));
  }

  @Test
  void testEquals_diffPoison() {
    CSVProducer dupProducerTest = new CSVProducer(sharedBufferTest, null, csvFilePathTest);
    assertFalse(producerTest.equals(dupProducerTest));
  }

  @Test
  void testEquals_diffPath() {
    CSVProducer dupProducerTest = new CSVProducer(sharedBufferTest, poisonTest, null);
    assertFalse(producerTest.equals(dupProducerTest));
  }

  @Test
  void testHashCode() {
    int expHash = Objects.hash(poisonTest, csvFilePathTest);
    assertEquals(expHash, producerTest.hashCode());
  }

  @Test
  void testToString() {
    String expString =  "CSVProducer{" +
        "poison='" + poisonTest + '\'' +
        ", csvFilePath='" + csvFilePathTest + '\'' +
        '}';
    assertEquals(expString, producerTest.toString());
  }
}