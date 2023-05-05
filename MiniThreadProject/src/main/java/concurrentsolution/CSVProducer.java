package concurrentsolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * CSVProducer class implements Runnable interface and override run method to read csv file line by line with poison pill implementation.
 *
 */
public class CSVProducer implements Runnable {
  private BlockingQueue<String> sharedBuffer;
  /**
   * This poison pill is used to stop or interrupt producer and consumer thread
   */
  private final String poison;
  private String csvFilePath;

  /**
   * Constructor of CSVProducer
   *
   * @param sharedBuffer represented as BlockingQueue, shared data between producer and consumer threads
   * @param poison represented as String, This poison pill is used to stop or interrupt producer and consumer thread
   * @param csvFilePath represented as String, csv file path
   */
  public CSVProducer(BlockingQueue<String> sharedBuffer, String poison, String csvFilePath) {
    this.sharedBuffer = sharedBuffer;
    this.poison = poison;
    this.csvFilePath = csvFilePath;
  }

  /**
   * When an object implementing interface {@code Runnable} is used to create a thread, starting the
   * thread causes the object's {@code run} method to be called in that separately executing
   * thread.
   * <p>
   * The general contract of the method {@code run} is that it may take any action whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    try {
      this.processCSV();
    }
    catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
    finally {
      while (true) {
        try {
          this.sharedBuffer.put(poison);
          break;
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * This private method is used to process CSV file line by line and add in shared queue
   *
   * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied,
   * and the thread is interrupted, either before or during the activity.
   */
  private void processCSV() throws InterruptedException {
    try (BufferedReader reader = Files.newBufferedReader(Paths.get(this.csvFilePath))) {
      String csvSingleLine;
      reader.readLine();

      while((csvSingleLine = reader.readLine()) != null) {
        this.sharedBuffer.put(csvSingleLine);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CSVProducer that = (CSVProducer) obj;
    return Objects.equals(this.poison, that.poison) && Objects.equals(this.csvFilePath, that.csvFilePath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.poison, this.csvFilePath);
  }

  @Override
  public String toString() {
    return "CSVProducer{" +
        "poison='" + this.poison + '\'' +
        ", csvFilePath='" + this.csvFilePath + '\'' +
        '}';
  }


}
