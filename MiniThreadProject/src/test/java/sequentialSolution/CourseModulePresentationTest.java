package sequentialSolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseModulePresentationTest {
  CourseModulePresentation testCmp;
  CourseModuleId id;
  @BeforeEach
  void setUp() {
    testCmp = new CourseModulePresentation();
    id = new CourseModuleId("AAA","BBB",-10);
  }

  @Test
  void add1() {
    testCmp.add(id,10L);
    testCmp.add(id,10L);
    assertTrue(testCmp.getModulePresentationList().get(id).equals(20L));
    assertEquals(1,testCmp.getModulePresentationList().size());
  }

  @Test
  void add2() {
    CourseModuleId id2 = new CourseModuleId("BBB","BBB",-10);
    testCmp.add(id,10L);
    testCmp.add(id2,10L);
    assertEquals(2,testCmp.getModulePresentationList().size());
  }

  @Test
  void testSameObj() {
    testCmp.add(id,10L);
    assertTrue(testCmp.equals(testCmp));
  }

  @Test
  void testDiffObj() {
    CourseModulePresentation testCmp2 = new CourseModulePresentation();
    testCmp2.add(id,10L);
    testCmp.add(id,10L);
    assertTrue(testCmp.equals(testCmp2));
  }

  @Test
  void testNull() {
    assertFalse(testCmp.equals(null));
  }

  @Test
  void testDiffDataType() {
    assertFalse(testCmp.equals("test"));
  }

  @Test
  void testDiffData() {
    CourseModulePresentation testCmp2 = new CourseModulePresentation();
    testCmp2.add(new CourseModuleId("BBB","BBB",-10),10L);
    assertFalse(testCmp.equals(testCmp2));
  }

  @Test
  void testHashCode() {
    CourseModulePresentation testCmp2 = new CourseModulePresentation();
    testCmp2.add(id,10L);
    testCmp.add(id,10L);
    assertEquals(testCmp.hashCode(), testCmp2.hashCode());
  }
  @Test
  void testToString() {
    HashMap<CourseModuleId, Long> testModulePresentationList = new HashMap<>();
    testModulePresentationList.put(id, 10L);
    testCmp.add(id,10L);
    assertEquals("CourseModulePresentation{" +
        "modulePresentationList=" + testModulePresentationList +
        '}',testCmp.toString());
  }
}