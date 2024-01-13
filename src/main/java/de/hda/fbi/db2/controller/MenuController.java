package de.hda.fbi.db2.controller;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.stud.entity.GameQuestion;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 * MenuController Created by l.koehler on 05.08.2019.
 */
public class MenuController {

  private final Controller controller;

  protected Lab02EntityManager lab02EntityManager;

  public MenuController(Controller controller) {
    this.controller = controller;
  }

  public final void setLab02EntityManager(Lab02EntityManager lab02EntityManager) {
    this.lab02EntityManager = lab02EntityManager;
  }

  /**
   * shows the menu.
   */
  public void showMenu() {
    do {
      System.out.println("Choose your Destiny?");
      System.out.println("--------------------------------------");
      System.out.println("1: Re-read csv");
      System.out.println("2: Play test");
      System.out.println("3: Create mass data");
      System.out.println("4: Analyze data");
      System.out.println("0: Quit");
    } while (readInput());
  }

  private boolean readInput() {
    try {
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
      String input = reader.readLine();
      if (input == null) {
        return true;
      }
      switch (input) {
        case "0":
          return false;
        case "1":
          readCsv();
          break;
        case "2":
          playTest();
          break;
        case "3":
          createMassData();
          break;
        case "4":
          analyzeData();
          break;
        default:
          System.out.println("Input Error");
          break;
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private void analyzeData() throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = dateFormat.parse("2024-02-26");
    Date endDate = dateFormat.parse("2024-02-28");

    TypedQuery<String> query1 = controller.getLab02EntityManager().getEntityManager().createNamedQuery("findDistinctUsernamesByTimeRange", String.class);
    query1.setParameter("startTime",  startDate);
    query1.setParameter("endTime",  endDate);

    List<String> usernames = query1.getResultList();

    for (String s : usernames) {
      System.out.println(s);
    }

    TypedQuery<Object[]> query2 = controller.getLab02EntityManager().getEntityManager().createNamedQuery("getPlayerGameInfo", Object[].class);
    query2.setParameter("player",  "Player1");

    List<Object[]> player_game_info = query2.getResultList();

    for (Object[] result : player_game_info) {
      int gameId = (int) result[0];
      Date startTime = (Date) result[1];
      Long totalQuestions = (Long) result[2];
      Long correctAnswers = (Long) result[3];

      // Calculate the percentage of correct answers
      double correctPercentage = (correctAnswers * 100.0) / totalQuestions;

      System.out.println("Game ID: " + gameId + ", Start Time: " + startTime +
          ", Total Questions: " + totalQuestions +
          ", Correct Answers: " + correctAnswers +
          ", Correct Percentage: " + correctPercentage + "%");
    }

  }

  private void createMassData() {
    controller.createMassData();
  }

  private void playTest() {
    Lab03Game gameController = controller.getLab03Game();
    Object player = gameController.interactiveGetOrCreatePlayer();
    List<?> questions = gameController.interactiveGetQuestions();
    Object game = gameController.createGame(player, questions);
    gameController.interactivePlayGame(game);
    gameController.persistGame(game);
  }

  private void readCsv() {
    controller.readCsv();
  }
}
