package sequentialSolution;

import exceptions.InvalidInputException;
import java.io.File;

/**
 * Validates directory path and starts the process to calculate clicks
 */
public class CommandLineParser {
  private final String directoryPath;

  /**
   * Constructs command line parser
   * @param directoryPath path to directory
   * @throws InvalidInputException throws exception if directory path does not exist
   */
  public CommandLineParser(String directoryPath) throws InvalidInputException {
    if(validation(directoryPath)){
      this.directoryPath = directoryPath;
    }
    else{
      throw new InvalidInputException("Invalid directory path provided");
    }
  }

  /**
   * Returns directory path
   * @return directory oath string
   */
  public String getDirectoryPath() {
    return this.directoryPath;
  }

  /**
   * Validates if directory path exists, is directory and is readable
   * @param path directory path string
   * @return False if directory path is invalid
   */
  private boolean validation(String path){
      File directory = new File(path);
      return directory.exists() && directory.isDirectory();
  }

}
