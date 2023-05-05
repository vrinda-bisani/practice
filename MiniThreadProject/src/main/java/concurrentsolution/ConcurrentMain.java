package concurrentsolution;

import exceptions.InvalidInputException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ConcurrentMain class is an entry point for mainclass solution
 *
 */
public class ConcurrentMain {
  /**
   * Main method to start the program
   *
   * @param args Array of String
   * @throws InvalidInputException this exception is thrown when validation fails for array of arguments
   * @throws InterruptedException this exception is thrown when a thread is interrupted in between any state
   */
  public static void main(String[] args) throws InvalidInputException, InterruptedException {
    CLIParser parser = new CLIParser(args);
    BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<>();

    ConcurrentMap<String, ConcurrentMap<Integer, Integer>> thresholdMap = new ConcurrentSkipListMap<>();
    ConcurrentMap<String, ConcurrentMap<Integer, Integer>> totalClickPerDate = new ConcurrentSkipListMap<>();

    Thread csvLineProducer = new Thread(new CSVProducer(sharedQueue, ConcurrentConstants.POISON_PILL, parser.getStudentVleCsv()));
    Thread csvLineConsumer = new Thread(new StudentVleConsumer(sharedQueue, ConcurrentConstants.POISON_PILL, parser.getThreshold(), thresholdMap, totalClickPerDate));
    Thread clickWriter = new Thread(new WriterRunnable(parser.getDirectoryPath(), totalClickPerDate));
    Thread thresholdWriter = new Thread(new ThresholdRunnable(thresholdMap, parser.getDirectoryPath(), parser.getThreshold()));

    csvLineProducer.start();
    csvLineConsumer.start();
    csvLineConsumer.join();
    thresholdWriter.start();
    clickWriter.start();
  }
}