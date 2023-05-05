package sequentialSolution;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Class to generate CSV files
 */
public class Writer {
  private static final String[] HEADER = new String[]{"date", "total_clicks"};
  private static final String CSV_EXTENSION = ".csv";
  private static final String FILENAME_DELIMITER = "_";
  private CourseModulePresentation cmp;
  private String basePath;

  /**
   * Construct writer class
   * @param cmp object that holds number of clicks of a unique class, module and date
   * @param basePath path to directory
   */
  public Writer(CourseModulePresentation cmp, String basePath) {
    this.cmp = cmp;
    this.basePath = basePath;
  }

  /**
   * Writes csv file for each course
   * @throws IOException throws exception if directory is not present
   */
  public void write() throws IOException {
    for(Map.Entry<CourseModuleId, Long> set : this.cmp.getModulePresentationList().entrySet()){
      String fileName = set.getKey().getCodeModule() + FILENAME_DELIMITER + set.getKey().getCodePresentation() + CSV_EXTENSION;
      String[] data = {set.getKey().getDate().toString(), set.getValue().toString()};
      String filePath = this.basePath + File.separator + fileName;
      boolean isNewFile = this.checkFileNew(filePath);
      try(CSVWriter writer = new CSVWriter(new FileWriter(filePath, Boolean.TRUE));) {
        if(isNewFile) {
          writer.writeNext(HEADER, Boolean.TRUE);
        }
        writer.writeNext(data, Boolean.TRUE);
      }
    }
  }

  /**
   * Private method to check if file is new or not
   * @param filePath represented in String, file path
   * @return boolean, true if file not exist or false if exist
   */
  private boolean checkFileNew(String filePath) {
    return !new File(filePath).exists();
  }
}
