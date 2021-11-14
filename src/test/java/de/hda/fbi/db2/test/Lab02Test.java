package de.hda.fbi.db2.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.controller.Controller;
import java.util.List;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.eclipse.persistence.internal.jpa.metamodel.AttributeImpl;
import org.eclipse.persistence.internal.jpa.metamodel.SingularAttributeImpl;
import org.eclipse.persistence.mappings.AggregateCollectionMapping;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectCollectionMapping;
import org.eclipse.persistence.mappings.DirectMapMapping;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by l.koehler on 05.08.2019.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Lab02Test {

  private static Metamodel metaData;

  private static EntityType<?> categoryEntity;

  private static EntityType<?> questionEntity;

  private static EntityType<?> answerEntity;

  private static Controller controller;

  private static synchronized void setMetaData(Metamodel metaData) {
    Lab02Test.metaData = metaData;
  }

  private static synchronized void setCategoryEntity(EntityType<?> categoryEntity) {
    Lab02Test.categoryEntity = categoryEntity;
  }

  private static synchronized void setQuestionEntity(EntityType<?> questionEntity) {
    Lab02Test.questionEntity = questionEntity;
  }

  private static synchronized void setAnswerEntity(EntityType<?> answerEntity) {
    Lab02Test.answerEntity = answerEntity;
  }

  /**
   * Lab02Test init.
   */
  @BeforeClass
  public static void init() {
    controller = Controller.getInstance();
    // Skip test class if students have not implemented lab01 yet
    assumeNotNull(controller.getLab01Data());

    Lab02EntityManager impl = controller.getLab02EntityManager();
    if (impl == null) {
      fail("Could not find Lab02EntityManager implementation");
    }

    String expectedPackage = "de.hda.fbi.db2.stud.impl";
    // Check startsWith to also allow subpackages
    if (!impl.getClass().getName().startsWith(expectedPackage + '.')) {
      fail("Implementation class should be in package " + expectedPackage);
    }

    if (!controller.isCsvRead()) {
      controller.readCsv();
    }

    if (!controller.isPersisted()) {
      controller.persistData();
    }
  }

  @Test
  public void test1EntityManager() {
    try {
      if (controller.getLab02EntityManager().getEntityManager() == null) {
        fail("Lab02EntityManager.getEntityManager() returns null");
      }
      setMetaData(controller.getLab02EntityManager().getEntityManager().getMetamodel());
    } catch (Exception e) {
      fail("Exception during entityManager creation");
    }
  }

  @Test
  public void test2FindCategory() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    for (EntityType<?> current : metaData.getEntities()) {
      if (current.getName().equalsIgnoreCase("category")) {
        setCategoryEntity(current);

        List<?> categories = controller.getLab01Data().getCategories();
        if (categories != null && !categories.isEmpty()) {
          Class<?> javaType = current.getJavaType();
          assertSame("Category class used by Lab01Data should be same as entity type",
              categories.get(0).getClass(), javaType);
        }

        return;
      }
    }
    fail("Could not find Category entity");
  }

  @Test
  public void test3FindQuestion() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    for (EntityType<?> current : metaData.getEntities()) {
      if (current.getName().equalsIgnoreCase("question")) {
        setQuestionEntity(current);

        List<?> questions = controller.getLab01Data().getQuestions();
        if (questions != null && !questions.isEmpty()) {
          Class<?> javaType = current.getJavaType();
          assertSame("Question class used by Lab01Data should be same as entity type",
              questions.get(0).getClass(), javaType);
        }

        return;
      }
    }
    fail("Could not find Question entity");
  }

  @Test
  public void test4FindAnswer() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    if (questionEntity == null) {
      fail("Could not find questionEntity");
    }

    for (Attribute<?, ?> member : questionEntity.getAttributes()) {
      DatabaseMapping mapping = ((AttributeImpl<?, ?>) member).getMapping();
      if (mapping instanceof DirectMapMapping) {
        return;
      }
      if (mapping instanceof AggregateCollectionMapping) {
        return;
      }
      if (mapping instanceof DirectCollectionMapping) {
        return;
      }
    }
    fail("Could not find a possible answer constellation in Question");
  }

  @Test
  public void test5AnswerStringsInQuestion() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    if (questionEntity == null) {
      fail("Could not find questionEntity");
    }

    int stringCount = 0;
    for (Object member : questionEntity.getAttributes()) {
      if (member instanceof SingularAttribute) {
        if (((SingularAttribute<?, ?>) member).getType().getJavaType() == String.class) {
          stringCount = stringCount + 1;
        }
      }
    }

    if (stringCount > 2) {
      fail("Found more then 2 Strings in Question!");
    }
  }

  @Test
  public void test6QuestionInCategory() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    if (questionEntity == null || categoryEntity == null) {
      fail("Could not find questionEntity or categoryEntity");
    }

    for (Object member : categoryEntity.getAttributes()) {
      try {
        Type<?> type = ((PluralAttribute<?, ?, ?>) member).getElementType();
        if (type == questionEntity) {
          return;
        }
      } catch (Exception ignored) {
        //This is expected
      }
    }

    fail("Could not find questions in category");
  }

  private static boolean hasEqualsMethod(Class<?> c) {
    try {
      c.getDeclaredMethod("equals", Object.class);
      return true;
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  // Explicitly want to test `equals` implementation; ignore IntelliJ warnings about `equals` misuse
  @SuppressWarnings({"SimplifiableAssertion", "EqualsWithItself", "ConstantConditions"})
  private static void assertEqualsImplementation(Object a, Object b) {
    String className = a.getClass().getSimpleName();
    assertTrue(className + " equals method should return true for `this`", a.equals(a));
    assertFalse(className + " equals method should return false for `null`",
        a.equals(null));
    assertFalse(className + " equals method should return false for different objects",
        a.equals(b));
  }

  @Test
  public void test7EqualsMethod() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    if (questionEntity == null || categoryEntity == null) {
      fail("Could not find questionEntity or categoryEntity");
    }

    if (!hasEqualsMethod(questionEntity.getJavaType())) {
      fail("Could not find equals method in Question entity");
    }

    List<?> questions = controller.getLab01Data().getQuestions();
    Object question1 = questions.get(0);
    Object question2 = questions.get(1);
    assertEqualsImplementation(question1, question2);

    if (!hasEqualsMethod(categoryEntity.getJavaType())) {
      fail("Could not find equals method in Category entity");
    }

    List<?> categories = controller.getLab01Data().getCategories();
    Object category1 = categories.get(0);
    Object category2 = categories.get(1);
    assertEqualsImplementation(category1, category2);

    if (answerEntity != null) {
      if (!hasEqualsMethod(answerEntity.getJavaType())) {
        fail("Could not find equals method in Answer entity");
      }
    }
  }

  private static boolean hasHashCodeMethod(Class<?> c) {
    try {
      c.getDeclaredMethod("hashCode");
      return true;
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  private static void assertHashCodeImplementation(List<?> objects) {
    assertFalse(objects.isEmpty());

    Object obj = objects.get(0);
    String className = obj.getClass().getSimpleName();
    assertEquals(className + " hashCode method should return consistent results",
        obj.hashCode(), obj.hashCode());

    // Check if student implementation just calls `super.hashCode()`
    // Because super.hashCode() could coincidentally be the same as properly implemented hashCode()
    // reduce likelihood for false positive test failure by checking all objects
    boolean differsFromIdentityHashCode = false;
    for (Object o : objects) {
      if (o.hashCode() != System.identityHashCode(o)) {
        differsFromIdentityHashCode = true;
        break;
      }
    }

    if (!differsFromIdentityHashCode) {
      fail(className + " hashCode method seems to call super.hashCode()");
    }
  }

  @Test
  public void test8HashCodeMethod() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    if (questionEntity == null || categoryEntity == null) {
      fail("Could not find questionEntity or categoryEntity");
    }

    if (!hasHashCodeMethod(questionEntity.getJavaType())) {
      fail("Could not find hashCode method in Question entity");
    }

    List<?> questions = controller.getLab01Data().getQuestions();
    assertHashCodeImplementation(questions);

    if (!hasHashCodeMethod(categoryEntity.getJavaType())) {
      fail("Could not find hashCode method in Category entity");
    }

    List<?> categories = controller.getLab01Data().getCategories();
    assertHashCodeImplementation(categories);

    if (answerEntity != null) {
      if (!hasHashCodeMethod(answerEntity.getJavaType())) {
        fail("Could not find hashCode method in Answer entity");
      }
    }
  }

  @Test
  public void test9CategoryNameUnique() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    if (categoryEntity == null) {
      fail("Could not find categoryEntity");
    }

    for (Attribute<?, ?> member : categoryEntity.getAttributes()) {
      try {
        SingularAttributeImpl<?, ?> attribute = (SingularAttributeImpl<?, ?>) member;
        String name = attribute.getName();
        if (name.equals("name")) {
          boolean isUnique = attribute.getMapping().getField().isUnique();
          if (!isUnique) {
            fail("Attribute 'name' in Category is not unique");
          } else {
            return;
          }
        }
      } catch (Exception ignored) {
        //This is expected
      }
    }
    fail("Could not find attribute 'name' in Category");
  }
}
