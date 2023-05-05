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
 * WriterRunnable is a class implements Runnable interface used to write each course_mode_course_presentation.csv
 *
 */
public class WriterRunnable implements Runnable {
  /**
   * Headers for code_mode_code_presentation.csv file
   */
  final transient private String[] courseHeaders = {"date", "total_clicks"};
  private final String directoryPath;
  private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> totalClickPerDate;

  /**
   * Constructor of WriterRunnable
   * @param directoryPath represented as String, directory path
   * @param totalClickPerDate represented as a ConcurrentMap where key is String, value is ConcurrentMap in which key is Integer, value is integer
   */
  public WriterRunnable(String directoryPath, ConcurrentMap<String, ConcurrentMap<Integer, Integer>> totalClickPerDate) {
    this.directoryPath = directoryPath;
    this.totalClickPerDate = totalClickPerDate;
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
      this.writeFilePerCourse();
    }
    catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * This private method is used to write code_mode_code_presentation.csv in directory provided in args, for each course present in studentVle.csv
   * There are two columns added with header named date and total_clicks
   */
  private void writeFilePerCourse() throws InterruptedException {
    for(Entry<String, ConcurrentMap<Integer, Integer>> course : this.totalClickPerDate.entrySet()) {
      String fileName = course.getKey();
      ConcurrentMap<Integer, Integer> courseDetails = course.getValue();

      List<String[]> csvRows = new ArrayList<>();
      csvRows.add(this.courseHeaders);

      courseDetails.forEach((date, totalClick) -> csvRows.add(new String[]{
          String.valueOf(date),
          String.valueOf(totalClick)
      }));

      try (FileWriter fileWriter = new FileWriter(this.directoryPath + File.separator + fileName + ConcurrentConstants.CSV_EXTENSION, Boolean.FALSE);
          CSVWriter csvWriter = new CSVWriter(fileWriter)) {
        csvWriter.writeAll(csvRows);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
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
    WriterRunnable that = (WriterRunnable) obj;
    return Objects.equals(this.directoryPath, that.directoryPath) && Objects.equals(
        this.totalClickPerDate, that.totalClickPerDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.directoryPath, this.totalClickPerDate);
  }

  @Override
  public String toString() {
    return "WriterRunnable{" +
        "directoryPath='" + this.directoryPath + '\'' +
        ", totalClickPerDate=" + this.totalClickPerDate +
        '}';
  }
}
