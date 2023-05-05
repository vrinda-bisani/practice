package sequentialSolution;

import java.util.Objects;

/**
 * Class to represent code module, code representation and date
 */
public class CourseModuleId {
  private String codeModule;
  private String codePresentation;
  private Integer date;

  /**
   * Constructs course module id
   * @param codeModule course module
   * @param codePresentation course presentation
   * @param date date
   */
  public CourseModuleId(String codeModule, String codePresentation, Integer date) {
    this.codeModule = codeModule;
    this.codePresentation = codePresentation;
    this.date = date;
  }

  /**
   * returns course module
   * @return course module
   */
  public String getCodeModule() {
    return this.codeModule;
  }

  /**
   * returns course presentation
   * @return course presentation
   */
  public String getCodePresentation() {
    return this.codePresentation;
  }

  /**
   * returns date
   * @return date
   */
  public Integer getDate() {
    return this.date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CourseModuleId that = (CourseModuleId) o;
    return Objects.equals(this.codeModule, that.codeModule) && Objects.equals(
        this.codePresentation, that.codePresentation) && Objects.equals(this.date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.codeModule, this.codePresentation, this.date);
  }

  @Override
  public String toString() {
    return "CourseModuleId{" +
        "codeModule='" + codeModule + '\'' +
        ", codePresentation='" + codePresentation + '\'' +
        ", date=" + date +
        '}';
  }
}
