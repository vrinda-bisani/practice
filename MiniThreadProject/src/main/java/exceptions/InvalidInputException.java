package exceptions;

/**
 * Exception thrown is invalid input is provides by user
 */
public class InvalidInputException extends RuntimeException {

  /**
   * Returns error message
   * @param errorMessage error message
   */
  public InvalidInputException(String errorMessage) {
    super(errorMessage);
  }
}
