package sequentialSolution;

import exceptions.InvalidInputException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;

/**
 * Entry point of the program
 */
public class SequentialMain {

  /**
   * Entry point of the program
   * @param args user input arg (directory path expected)
   * @throws InvalidInputException if user provides ivalid directory path
   * @throws IOException if CSV file is invalid
   * @throws CsvValidationException if CSV file is invalid
   */
  public static void main(String[] args)
      throws InvalidInputException, IOException, CsvValidationException {
    if(args.length == 0) {
      throw new InvalidInputException("Directory path not given");
    }
    CommandLineParser commandLineParser = new CommandLineParser(args[0]);
    new CSVParser(commandLineParser.getDirectoryPath());
  }
}
