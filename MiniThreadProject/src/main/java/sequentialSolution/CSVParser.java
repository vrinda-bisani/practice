package sequentialSolution;

import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Parses studentVle file and calculates total no of clicks
 */
public class CSVParser {

 private static final String STUDENT_VLE_FILE = "studentVle.csv";

 /**
  * Starts process to calculate and generate csv files
  * @param directoryPath path to directory
  * @throws IOException if CSV file is invalid
  * @throws CsvValidationException if CSV file is invalid
  */
 public CSVParser(String directoryPath) throws IOException, CsvValidationException {
  this.process(directoryPath);
 }

 /**
  * Starts process to calculate and generate csv files
  * @param directoryPath path to directory
  * @throws IOException if CSV file is invalid
  * @throws CsvValidationException if CSV file is invalid
  */
 private void process(String directoryPath) throws IOException, CsvValidationException {
  List<String[]> studentVleCSV = getCSVFile(directoryPath + File.separator + STUDENT_VLE_FILE);
  CourseModulePresentation cmp = this.countClicks(studentVleCSV);
  Writer writer = new Writer(cmp, directoryPath);
  writer.write();
 }

 /**
  * Returns CSV files
  * @param path directory path
  * @return CSV content
  * @throws IOException if CSV file is invalid
  * @throws CsvValidationException if CSV file is invalid
  */
 private List<String[]> getCSVFile(String path) throws IOException, CsvValidationException {
  CSVReader reader = new CSVReader();
  reader.readFile(path);
  return reader.getCsvContent();
 }

 /**
  * Adds data to hashmap where key is the CourseModuleID and value is the number of clicks
  * @param studentVle represents student data
  * @return Course Module Presentation
  */
 private CourseModulePresentation countClicks(List<String[]> studentVle) {
  CourseModulePresentation cmp = new CourseModulePresentation();
  for(String[] row : studentVle) {
   CourseModuleId id = new CourseModuleId(row[0], row[1], Integer.parseInt(row[4]));
   cmp.add(id, Long.parseLong(row[5]));
  }
  return cmp;
 }
}
