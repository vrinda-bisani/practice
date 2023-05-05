package sequentialSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class represents a hashmap where key is the CourseModuleID and value is the number of clicks
 */
public class CourseModulePresentation {
  private Map<CourseModuleId, Long> modulePresentationList;

  /**
   * Constructs hashmap where key is the CourseModuleID and value is the number of clicks
   */
  public CourseModulePresentation() {
    this.modulePresentationList = new HashMap<>();
  }

  /**
   * Adds data to hashmap
   * @param id CourseModuleId
   * @param clicks no of clicks
   */
  public void add(CourseModuleId id, Long clicks){
    if(this.modulePresentationList.get(id) != null){
      clicks += this.modulePresentationList.get(id);
    }
    this.modulePresentationList.put(id, clicks);
  }

  /**
   * Returns hashmap where key is the CourseModuleID and value is the number of clicks
   * @return Module Presentation List
   */
  public Map<CourseModuleId, Long> getModulePresentationList() {
    return this.modulePresentationList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CourseModulePresentation that)) {
      return false;
    }
    return Objects.equals(getModulePresentationList(), that.getModulePresentationList());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getModulePresentationList());
  }

  @Override
  public String toString() {
    return "CourseModulePresentation{" +
        "modulePresentationList=" + modulePresentationList +
        '}';
  }
}
