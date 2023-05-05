package concurrentsolution;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * AbstractConsumer is an abstract class implementing Runnable interface to consume CSV file line by line
 *
 */
public abstract class AbstractConsumer implements Runnable {
  private final Integer threshold;
  private BlockingQueue<String> sharedBuffer;
  private final String poison;

  /**
   * Constructor of AbstractConsumer
   *
   * @param sharedBuffer represented as BlockingQueue of String, shared csv lines between producer
   *                    and consumer thread
   * @param poison      represented as String, poison pill is used to stop or interrupt producer and
   *                    consumer thread
   * @param threshold represented as Integer, threshold provided in args
   */
  protected AbstractConsumer(BlockingQueue<String> sharedBuffer, String poison, Integer threshold) {
    this.sharedBuffer = sharedBuffer;
    this.poison = poison;
    this.threshold = threshold;
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
    while (true) {
      try {
        String line = this.sharedBuffer.take();
        if (line.equals(poison)) {
          this.sharedBuffer.add(poison);
          break;
        }
        consumeCSVFile(line);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * This method is used to process csv line for total clicks and store in map
   *
   * @param line represented as String, CSV line
   */
  protected abstract void consumeCSVFile(String line);

  /**
   * protected Getter method for threshold
   *
   * @return represented as Integer
   */
  protected Integer getThreshold() {
    return this.threshold;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    AbstractConsumer that = (AbstractConsumer) obj;
    return Objects.equals(this.getThreshold(), that.getThreshold()) && Objects.equals(this.poison, that.poison);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getThreshold(), this.poison);
  }
}
