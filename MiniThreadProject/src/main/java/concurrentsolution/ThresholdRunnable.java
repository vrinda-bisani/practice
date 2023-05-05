package concurrentsolution;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/**
 * ThresholdRunnable is a class implements Runnable interface used to write activity-threshold.csv in given directory
 *
 */
public class ThresholdRunnable implements Runnable {
  /**
   * Headers for activity-threshold.csv file
   */
  final transient private String[] activityHeaders = {"module_presentation", "date", "total_clicks"};
  private final ConcurrentMap<String, ConcurrentMap<Integer, Integer>> thresholdTtlClick;
  private final String directoryPath;
  private final Integer threshold;

  /**
   * Constructor of ThresholdRunnable
   * @param thresholdTtlClick represented as a ConcurrentMap where key is String, value is ConcurrentMap in which key is Integer, value is integer
   * @param directoryPath represented as String, directory path
   * @param threshold represented as Integer, threshold provided in args
   */
  public ThresholdRunnable(ConcurrentMap<String, ConcurrentMap<Integer, Integer>> thresholdTtlClick,
      String directoryPath, Integer threshold) {
    this.thresholdTtlClick = thresholdTtlClick;
    this.directoryPath = directoryPath;
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
    try {
      this.writeActivityThreshold();
    }
    catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * This private method is used to write activity-threshold.csv in directory provided in args.
   * This csv file contains summary of all courses along with date and clicks are more than or equal to threshold.
   */
  private void writeActivityThreshold() throws InterruptedException {
    List<String[]> activityRows = new ArrayList<>();
    activityRows.add(this.activityHeaders);

    for(Entry<String, ConcurrentMap<Integer, Integer>> course : this.thresholdTtlClick.entrySet()) {
      ConcurrentMap<Integer, Integer> courseInfo = course.getValue();
      String courseName = course.getKey();
      courseInfo.forEach((date, totalClick) -> activityRows.add(new String[]{
          courseName,
          String.valueOf(date),
          String.valueOf(totalClick)
      }));
    }

    try (FileWriter fileWriter = new FileWriter(this.directoryPath + File.separator + ConcurrentConstants.ACTIVITY_PREFIX
        + this.threshold + ConcurrentConstants.CSV_EXTENSION, Boolean.FALSE); CSVWriter activityWriter = new CSVWriter(fileWriter)) {
      activityWriter.writeAll(activityRows);
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
    ThresholdRunnable that = (ThresholdRunnable) obj;
    return Objects.equals(this.thresholdTtlClick, that.thresholdTtlClick)
        && Objects.equals(this.directoryPath, that.directoryPath) && Objects.equals(
        this.threshold, that.threshold);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.thresholdTtlClick, this.directoryPath, this.threshold);
  }

  @Override
  public String toString() {
    return "ThresholdRunnable{" +
        "thresholdTtlClick=" + this.thresholdTtlClick +
        ", directoryPath='" + this.directoryPath + '\'' +
        ", threshold=" + this.threshold +
        '}';
  }
}
