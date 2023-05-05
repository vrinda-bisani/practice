package concurrentsolution;

import exceptions.InvalidInputException;
import java.io.File;
import java.util.Objects;

/**
 * CLIParser is a class used for command line arguments in concurrent solution.
 * It contains two args information directoryPath and threshold.
 *
 */
public class CLIParser {
  private final String directoryPath;
  private final Integer threshold;

  /**
   * Constructor of CLIParser
   *
   * @param args represented as array of String, Arguments
   * @throws InvalidInputException this exception is thrown when validation fails for array of arguments
   */
  public CLIParser(String... args) throws InvalidInputException {
    if(this.validateArgs(args)) {
      this.directoryPath = args[0];
      this.threshold = Integer.parseInt(args[1]);
    }
    else {
      throw new InvalidInputException("Invalid arguments provided! Existing program...");
    }
  }

  /**
   * This private method validates args array for directory path and threshold
   *
   * @param args represented as array of String, Arguments
   * @return represented as boolean, true if all validation passed else false
   * @throws InvalidInputException this exception is thrown when validation fails for array of arguments
   */
  private boolean validateArgs(String... args) throws InvalidInputException {
    return this.validateArgsLength(args) &&
        this.validateDirectory(args[0]) &&
        this.validateStudentVleFile(args[0]) &&
        this.validateThreshold(args[1]);
  }

  /**
   * This private method validates studentVle.csv file
   *
   * @param dirPath represented as String, directory path
   * @return represented as boolean, true if file validation pass else false
   */
  private boolean validateStudentVleFile(String dirPath) {
    File studentVleFile = new File(dirPath, ConcurrentConstants.STUDENT_VLE_CSV);
    return studentVleFile.exists() && studentVleFile.isFile() && studentVleFile.canRead();
  }

  /**
   * This private method validates threshold provided in args
   *
   * @param thresholdArg represented as String, threshold argument
   * @return represented as boolean, true if threshold is greater than or equal to zero else false
   * @throws InvalidInputException throws NumberFormatException if unable to convert string into long
   */
  private boolean validateThreshold(String thresholdArg) throws InvalidInputException {
    try {
      int threshold = Integer.parseInt(thresholdArg);
      return threshold >= ConcurrentConstants.MIN_THRESHOLD;
    }
    catch (NumberFormatException formatException) {
      throw new InvalidInputException(formatException.getMessage());
    }
  }

  /**
   * This private method validates directory path provided in args
   *
   * @param dirPath represented as String, directory path
   * @return represented as boolean, true if directory validation pass else false
   */
  private boolean validateDirectory(String dirPath) {
    File directory = new File(dirPath);
    return directory.exists() && directory.isDirectory() && directory.canRead();
  }

  /**
   * This private method validates arguments length as it should be two
   *
   * @param args represented as array of String, Arguments
   * @return represented as boolean, returns true if length is two else false
   */
  private boolean validateArgsLength(String... args) {
    return args.length == ConcurrentConstants.TWO_ARGS;
  }

  /**
   * This public method is used to get file path of studentVle.csv
   *
   * @return represented as String
   */
  public String getStudentVleCsv() {
    return this.directoryPath + File.separator + ConcurrentConstants.STUDENT_VLE_CSV;
  }

  /**
   * This public method is used to get file path of courses.csv
   *
   * @return represented as String
   */
  public String getCoursesVleCsv() {
    return this.directoryPath + File.separator + ConcurrentConstants.COURSES_CSV;
  }

  /**
   * This public method is used to get threshold provided in args
   *
   * @return represented as Integer
   */
  public Integer getThreshold() {
    return this.threshold;
  }

  /**
   * This public method is used to get path of directory
   *
   * @return represented as String
   */
  public String getDirectoryPath() {
    return this.directoryPath;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CLIParser cliParser = (CLIParser) obj;
    return Objects.equals(this.getDirectoryPath(), cliParser.getDirectoryPath())
        && Objects.equals(this.getThreshold(), cliParser.getThreshold());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getDirectoryPath(), this.getThreshold());
  }

  @Override
  public String toString() {
    return "CLIParser{" +
        "directoryPath='" + this.getDirectoryPath() + '\'' +
        ", threshold=" + this.getThreshold() +
        '}';
  }


}
