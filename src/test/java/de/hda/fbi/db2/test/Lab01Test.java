package de.hda.fbi.db2.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.controller.Controller;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by l.koehler on 05.08.2019.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Lab01Test {

  private static Controller controller;

  /**
   * Lab01Test init.
   */
  @BeforeClass
  public static void init() {
    controller = Controller.getInstance();
    Lab01Data impl = controller.getLab01Data();
    if (impl == null) {
      fail("Could not find Lab01Data implementation");
    }

    String expectedPackage = "de.hda.fbi.db2.stud.impl";
    // Check startsWith to also allow subpackages
    if (!impl.getClass().getName().startsWith(expectedPackage + '.')) {
      fail("Implementation class should be in package " + expectedPackage);
    }

    if (!controller.isCsvRead()) {
      controller.readCsv();
    }
  }

  @Test
  public void test1CategorySize() {
    List<?> categories = controller.getLab01Data().getCategories();
    assertNotNull("Categories must not be null", categories);
    assertEquals("There should be 51 categories", 51, categories.size());
  }

  @Test
  public void test2QuestionSize() {
    List<?> questions = controller.getLab01Data().getQuestions();
    assertNotNull("Questions must not be null", questions);
    assertEquals("There should be 200 questions", 200, questions.size());
  }

  @Test
  public void test3CategoryObject() {
    List<?> categories = controller.getLab01Data().getCategories();
    assertNotNull("Categories must not be null", categories);
    assertFalse("Categories must not be empty", categories.isEmpty());

    Object testObject = categories.get(0);
    assertEquals("Category class should be named 'Category'", "Category",
        testObject.getClass().getSimpleName());
    assertEquals("Category class should be in correct package", "de.hda.fbi.db2.stud.entity",
        testObject.getClass().getPackageName());
  }

  @Test
  public void test4QuestionObject() {
    List<?> questions = controller.getLab01Data().getQuestions();
    assertNotNull("Questions must not be null", questions);
    assertFalse("Questions must not be empty", questions.isEmpty());

    Object testObject = questions.get(0);
    assertEquals("Question class should be named 'Question'", "Question",
        testObject.getClass().getSimpleName());
    assertEquals("Question class should be in correct package", "de.hda.fbi.db2.stud.entity",
        testObject.getClass().getPackageName());
  }
}
