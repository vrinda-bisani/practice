package concurrentsolution;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * StudentVleConsumer class extends AbstractConsumer runnable class for consume csv line and
 * process clicks per course total clicks and per course per date total clicks
 *
 */
public class StudentVleConsumer extends AbstractConsumer {
  /**
   * Constant used to underscore in file name
   */
  public static final String UNDERSCORE_STRING = "_";
  private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> thresholdMap;
  private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> totalClickPerDate;

  /**
   * Constructor of StudentVleConsumer class
   *
   * @param sharedBuffer represented as BlockingQueue of String, shared csv lines between producer and consumer thread
   * @param poison represented as String, poison pill is used to stop or interrupt producer and consumer thread
   * @param threshold represented as threshold, threshold provided in args
   * @param thresholdMap represented as ConcurrentMap where key as String, code_mode_code_presentation and value as Integer, totalClicks
   * @param totalClickPerDate represented as ConcurrentMap where key as String, code_mode_code_presentation and
   *                                value as ConcurrentMap, where key is Integer, data and value is Integer, totalClicksPerDate
   */
  public StudentVleConsumer(BlockingQueue<String> sharedBuffer, String poison, Integer threshold,
      ConcurrentMap<String, ConcurrentMap<Integer, Integer>> thresholdMap,
      ConcurrentMap<String, ConcurrentMap<Integer, Integer>> totalClickPerDate) {
    super(sharedBuffer, poison, threshold);
    this.thresholdMap = thresholdMap;
    this.totalClickPerDate = totalClickPerDate;
  }

  /**
   * This method is used to process csv line for total clicks and store in mainclass map
   *
   * @param line represented as String, CSV line
   */
  @Override
  protected void consumeCSVFile(String line) {
    String[] csvLine = line.replaceAll(ConcurrentConstants.QUOTE_REGEX, ConcurrentConstants.NO_SPACE).split(
        ConcurrentConstants.COMMA_REGEX);
    String codeModule = csvLine[0];
    String codePresentation = csvLine[1];
    Integer date = Integer.parseInt(csvLine[4]);
    Integer currentClick = Integer.parseInt(csvLine[5]);
    String modePresentation = codeModule + UNDERSCORE_STRING + codePresentation;

    processClicks(modePresentation, date, currentClick);
  }

  /**
   * This private method process clicks and update totalClicksPerCourse and totalClicksPerDate mainclass map
   *
   * @param modePresentation represented as String, code_mode_code_presentation
   * @param date represented as Integer, date from csv
   * @param currentClick represented as Integer, click from csv
   */
  private void processClicks(String modePresentation, Integer date, Integer currentClick) {
    int previousClick;
    int newTotalClick;

    ConcurrentMap<Integer, Integer> dateClicksMap = this.totalClickPerDate.computeIfAbsent(modePresentation, key -> new ConcurrentSkipListMap<>());

    do {
      previousClick = dateClicksMap.computeIfAbsent(date, key -> 0);
      newTotalClick = currentClick + previousClick;
    } while(!dateClicksMap.replace(date, previousClick, newTotalClick));

    processActivityThreshold(modePresentation, date);
  }

  /**
   * This private method processes totalClickPerDate map and if totalClick is greater than or equal to threshold than add to thresholdMap map
   *
   * @param modePresentation represented as String, code_mode_code_presentation
   * @param date represented as Integer, date from csv
   */
  private void processActivityThreshold(String modePresentation, Integer date) {
    ConcurrentMap<Integer, Integer> dateClickMap = this.totalClickPerDate.get(modePresentation);
    Integer totalClicks = dateClickMap.get(date);
    if(totalClicks >= this.getThreshold()) {
      ConcurrentMap<Integer, Integer> thresholdPerDate = this.thresholdMap.computeIfAbsent(modePresentation, key -> new ConcurrentSkipListMap<>());
      thresholdPerDate.put(date, totalClicks);
      this.thresholdMap.put(modePresentation, thresholdPerDate);
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
    if (!super.equals(obj)) {
      return false;
    }
    StudentVleConsumer that = (StudentVleConsumer) obj;
    return Objects.equals(this.thresholdMap, that.thresholdMap) && Objects.equals(
        this.totalClickPerDate, that.totalClickPerDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.thresholdMap, this.totalClickPerDate);
  }

  @Override
  public String toString() {
    return "StudentVleConsumer{" +
        "thresholdMap=" + this.thresholdMap +
        ", totalClickPerDate=" + this.totalClickPerDate +
        '}';
  }
}
