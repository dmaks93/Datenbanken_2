package de.hda.fbi.db2.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.controller.Controller;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by l.koehler on 05.08.2019.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Lab03Test {

  private static Metamodel metaData;

  private static Controller controller;

  private static EntityType<?> gameEntity;

  private static synchronized void setGameEntity(EntityType<?> gameEntity) {
    Lab03Test.gameEntity = gameEntity;
  }

  /**
   * Lab03Test init.
   */
  @BeforeClass
  public static void init() {
    controller = Controller.getInstance();
    // Skip test class if students have not implemented lab01 or lab02 yet
    assumeNotNull(controller.getLab01Data(), controller.getLab02EntityManager());

    Lab03Game impl = controller.getLab03Game();
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

    try {
      if (controller.getLab02EntityManager().getEntityManager() == null) {
        fail("Lab02EntityManager.getEntityManager() returns null");
      }
      metaData = controller.getLab02EntityManager().getEntityManager().getMetamodel();
    } catch (Exception e) {
      fail("Exception during entityManager creation");
    }
  }

  @Test
  public void test1Functionality() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    Lab03Game gameController = controller.getLab03Game();
    Lab01Data lab01Data = controller.getLab01Data();
    List<Object> categories = new ArrayList<>();
    categories.add(lab01Data.getCategories().get(0));
    categories.add(lab01Data.getCategories().get(1));

    List<?> questions = gameController.getQuestions(categories, 2);
    assertNotNull(questions);
    assertFalse("Questions for categories should not be empty", questions.isEmpty());
    assertTrue("Should at most return 2 questions", questions.size() <= 2);

    Object player = gameController.getOrCreatePlayer("PlayerName");
    assertNotNull(player);
    Object game = gameController.createGame(player, questions);
    assertNotNull(game);
    gameController.playGame(game);
    gameController.persistGame(game);
  }

  @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
  @Test
  public void test2FindGameEntity() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    Lab03Game gameController = controller.getLab03Game();
    Lab01Data lab01Data = controller.getLab01Data();
    List<Object> categories = new ArrayList<>();
    categories.add(lab01Data.getCategories().get(0));
    categories.add(lab01Data.getCategories().get(1));

    List<?> questions = gameController.getQuestions(categories, 2);
    assertNotNull(questions);
    assertFalse("Questions for categories should not be empty", questions.isEmpty());
    assertTrue("Should at most return 2 questions", questions.size() <= 2);

    Object player = gameController.getOrCreatePlayer("PlayerName");
    assertNotNull(player);
    Object game = gameController.createGame(player, questions);
    assertNotNull(game);

    boolean gameFound = false;
    for (EntityType<?> classes : metaData.getEntities()) {
      if (classes.getJavaType() == game.getClass()) {
        gameFound = true;
        setGameEntity(classes);
      }
    }

    if (!gameFound) {
      fail("Could not find Game class as entity");
    }
  }

  @Test
  public void test3FindPlayerEntity() {
    if (metaData == null) {
      fail("No MetaModel");
    }

    Lab03Game gameController = controller.getLab03Game();
    Object player = gameController.getOrCreatePlayer("PlayerName");
    assertNotNull(player);

    boolean playerFound = false;
    for (EntityType<?> classes : metaData.getEntities()) {
      if (classes.getJavaType() == player.getClass()) {
        playerFound = true;
      }
    }

    if (!playerFound) {
      fail("Could not find Player class as entity");
    }
  }
}
