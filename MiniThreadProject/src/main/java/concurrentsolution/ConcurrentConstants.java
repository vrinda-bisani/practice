package concurrentsolution;

/**
 * ConcurrentConstants is a class for constants used in mainclass solution
 *
 */
public class ConcurrentConstants {
  /**
   * Constant for two args length validation
   */
  public static final int TWO_ARGS = 2;

  /**
   * Constant for minimum threshold validation
   */
  public static final int MIN_THRESHOLD = 0;

  /**
   * Constant for studentVle.csv file name
   */
  public static final String STUDENT_VLE_CSV = "studentVle.csv";

  /**
   * Constant for courses.csv file name
   */
  public static final String COURSES_CSV = "courses.csv";

  /**
   * Constant for poison pill to stop or interrupt producer and consumer thread
   */
  public static final String POISON_PILL = "END_OF_FILE";

  /**
   * Regex for comma in csv line
   */
  public static final String COMMA_REGEX = ",";

  /**
   * Regex for slash in csv line
   */
  public static final String QUOTE_REGEX = "\"";

  /**
   * Constant for no space replacement
   */
  public static final String NO_SPACE = "";

  /**
   * Constant for activity prefix for file name
   */
  public static final String ACTIVITY_PREFIX = "activity-";

  /**
   * constant for csv final extension
   */
  public static final String CSV_EXTENSION = ".csv";

  /**
   * Private constructor of ConcurrentConstants
   */
  private ConcurrentConstants() { }
}
