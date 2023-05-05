package sequentialSolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseModuleIdTest {
  CourseModuleId testId;
  String testCourseModule;
  String testCoursePresentation;
  Integer testDate;


  @BeforeEach
  void setUp() {
    testCourseModule = "ABC";
    testCoursePresentation = "1234J";
    testDate = -10;
    testId = new CourseModuleId(testCourseModule, testCoursePresentation, testDate);
  }

  @Test
  void getCodeModule() {
    assertEquals(testCourseModule, testId.getCodeModule());
  }

  @Test
  void getCodePresentation() {
    assertEquals(testCoursePresentation, testId.getCodePresentation());
  }

  @Test
  void getDate() {
    assertEquals(testDate, testId.getDate());
  }

  @Test
  void testSame() {
    assertTrue(testId.equals(testId));
  }

  @Test
  void testDiffDataType() {
    assertFalse(testId.equals("testId"));
  }

  @Test
  void testDiffObject() {
    CourseModuleId testId2= new CourseModuleId(testCourseModule, testCoursePresentation, testDate);
    assertTrue(testId.equals(testId2));
  }

  @Test
  void testDiffCourseModule() {
    CourseModuleId testId2= new CourseModuleId("AAA", testCoursePresentation, testDate);
    assertFalse(testId.equals(testId2));
  }

  @Test
  void testDiffCoursePresentation() {
    CourseModuleId testId2= new CourseModuleId(testCourseModule,"AAA", testDate);
    assertFalse(testId.equals(testId2));
  }

  @Test
  void testNull() {
    assertFalse(testId.equals(null));
  }

  @Test
  void testDiffDate() {
    CourseModuleId testId2= new CourseModuleId(testCourseModule,testCoursePresentation, 0);
    assertFalse(testId.equals(testId2));
  }

  @Test
  void testHashCode() {
    CourseModuleId testId2= new CourseModuleId(testCourseModule, testCoursePresentation, testDate);
    assertEquals(testId.hashCode(), testId2.hashCode());
  }

  @Test
  void testToString() {
    assertEquals("CourseModuleId{" +
        "codeModule='" + testCourseModule + '\'' +
        ", codePresentation='" + testCoursePresentation + '\'' +
        ", date=" + testDate +
        '}', testId.toString());
  }
}