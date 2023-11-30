package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.Player;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityManager;

public class Lab03GameImpl extends Lab03Game {
  EntityManagerFactory emf = Persistence.createEntityManagerFactory("fbi-postgresPU");
  EntityManager em = emf.createEntityManager();

  /**
   * Creates a new Player or retrieves it from the database.
   * <p>
   * This function shall try to retrieve the player with the given name playerName from the
   * database. If no such player exists, it shall be created as a Java Object. It is not necessary
   * to persist the Player yet.
   * </p>
   *
   * <p>This function is primarily used for testing. There exists a version with user interaction
   * which shall be used from the menu.
   * </p>
   *
   * @param playerName The name for the new Player.
   * @return Player object which was created or retrieved.
   * @see Lab03Game#interactiveGetOrCreatePlayer()
   */
  @Override
  public Object getOrCreatePlayer(String playerName) {
    Query query = em.createQuery("SELECT p from Player p where p.username = :name");
    query.setParameter("name", playerName);
    Player existingPlayer = (Player) query.getSingleResult();
    if (existingPlayer != null)
      return existingPlayer;
    return new Player(playerName);
  }

  /**
   * Creates a new Player or retrieves it from the database (interactive version).
   *
   * <p>
   * This function shall ask the user for a player name, and then shall try to retrieve such a
   * player from the database. If no such player exists, it shall be created as a Java Object. It is
   * not necessary to persist the player yet.
   * </p>
   *
   * <p>This function is primarily used for user interaction. There exists a version used for
   * testing, {@link #getOrCreatePlayer(String)}.</p>
   *
   * @return Player object which was created or retrieved.
   * @see Lab03Game#getOrCreatePlayer(String)
   */
  @Override
  public Object interactiveGetOrCreatePlayer() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Gebe deinen Namen ein: ");
    String userInput = scanner.nextLine();
    scanner.close();
    return getOrCreatePlayer(userInput);
  }

  /**
   * This function shall generate a random list of questions which are from the given categories.
   *
   * <p>Per category there shall be a certain amount of questions chosen. If a category hosts less
   * questions than that amount, then all of the questions shall be chosen. Questions shall be
   * randomly selected.
   * </p>
   *
   * <p>There is also an interactive version of this function which asks the user for categories
   * instead of randomly selecting them.</p>
   *
   * @param categories                   A list of categories to select questions from
   * @param amountOfQuestionsForCategory The amount of questions per category. If a category has
   *                                     less than this amount, then all questions of that category
   *                                     shall be selected.
   * @return A List of randomly chosen Questions from the given Categories.
   * @see Lab03Game#interactiveGetQuestions()
   */
  @Override
  public List<Question> getQuestions(List<?> categories, int amountOfQuestionsForCategory) {
    List<Question> questions = new ArrayList<Question>();
    Random rand = new Random();
    for (Object cat : categories) {
      if (cat instanceof Category) {
        Category questionCategory = (Category) cat;
        int catQuestionsToGet = Math.min(questionCategory.getQuestions().size(),
            amountOfQuestionsForCategory);
        int nextQuestionIndex;
        Question selectedQuestion;
        while (catQuestionsToGet > 0) {
          nextQuestionIndex = rand.nextInt(catQuestionsToGet);
          selectedQuestion = questionCategory.getQuestions().get(nextQuestionIndex);
          questionCategory.removeQuestion(selectedQuestion.getQuestionId());
          questions.add(selectedQuestion);
          --catQuestionsToGet;
        }
      }
    }
    return questions;
  }

  /**
   * This function shall generate a random list of questions after asking the user for categories.
   *
   * <p>In this function, ask the user for categories and the number of questions per category.
   * Then, randomly select questions from those categories. Choose as many questions per category as
   * were entered, as long as the category has that many questions. If there are fewer questions,
   * then select all of them.</p>
   *
   * @return A List of randomly chosen Questions from categories which the user entered.
   * @see Lab03Game#getQuestions(List, int)
   */
  @Override
  public List<?> interactiveGetQuestions() {
    String userInput = "";
    List<Integer> categoriesToPlay = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    while(true) {
      System.out.println("Gebe die Id der Kategorie ein: (x beendet die Auswahl)");
      userInput = scanner.nextLine();
      if (!userInput.equalsIgnoreCase("x")){
        try {
          int catId = Integer.parseInt(userInput);
          categoriesToPlay.add(catId);
          System.out.println("Kategorie: " + catId + " erfolgreich hinzugefügt");
        } catch (NumberFormatException e) {
          System.out.println("Fehlerhafte Eingabe, bitte versuche es erneut.");
        }
      } else {
        break;
      }
    }
    while(true) {
      System.out.println("Gebe die Anzahl an Fragen pro Kategorie an");
      userInput = scanner.nextLine();
      try {
        int number = Integer.parseInt(userInput);
        System.out.println("Erfolgreich " + number + " Fragen ausgewählt");
        break;
      } catch (NumberFormatException e) {
        System.out.println("Fehlerhafte Eingabe, bitte versuche es erneut.");
      }
    }
    scanner.close();
    try {
      for (int id: categoriesToPlay) {
        // Hier müssen wir die Kategorien inklusive Questions aus der Datenbank abrufen
        // und in categoryList schereiben.
      }
    } catch (NullPointerException e) {
      System.out.println("Es wurde eine nicht existierende Kategorie ausgewählt "
          + "bitte versuchen Sie es erneut");
      return this.interactiveGetQuestions();
    }
    return null;
  }

  /**
   * This function creates a Game Object with the given player and questions, but without playing
   * the game yet.
   *
   * <p>It is important that you neither play the game yet nor persist the game! This is just meant
   * to create the game object.</p>
   *
   * @param player    The Player which shall play the game.
   * @param questions The Questions which shall be asked during the game.
   * @return A Game object which contains an unplayed game for the given player with the given
   * questions.
   */
  @Override
  public Object createGame(Object player, List<?> questions) {
    return new Game((Player) player, (List<Question>) questions);
  }

  /**
   * This function simulates a game play without user interaction by randomly choosing answers.
   *
   * <p>There is also an interactive version of this function which shall be called from the menu.
   * </p>
   *
   * @param game The Game object which shall be played.
   * @see Lab03Game#interactivePlayGame(Object)
   */
  @Override
  public void playGame(Object game) {

  }

  /**
   * This function plays the given game with the user, that is by using user interaction.
   *
   * <p>This is the function that should be called from the menu. Here you can implement the
   * necessary user interaction for the playing of the game.</p>
   *
   * @param game The Game object which shall be played.
   * @see Lab03Game#playGame(Object)
   */
  @Override
  public void interactivePlayGame(Object game) {

  }

  /**
   * Persists a played game, including the player who played it.
   *
   * @param game The Game object to be persisted.
   */
  @Override
  public void persistGame(Object game) {

  }
}
