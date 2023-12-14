package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.persistence.EntityManager;
import de.hda.fbi.db2.stud.entity.Player;
import de.hda.fbi.db2.stud.entity.Game;

public class Lab04MassDataImpl extends Lab04MassData {

  @Override
  public void createMassData() {
    int players = 10000;
    int games = 100;
    int dayOfMonth = 1;
    int startHour = 8;
    int startMinute = 10;
    int numCategory;
    int numQuestions;
    List<Category> categoriesToPlay = new ArrayList<>();
    Set<Category> allCategories = new HashSet<>();
    List<Category> playableCategories;
    List<Question> gameQuestions = new ArrayList<>();
    Random rand = new Random();
    int randomInt;
    String query;
    Player player;
    Game game;



    EntityManager em = lab02EntityManager.getEntityManager();
    String catQuery = "SELECT c FROM Category c ORDER BY c.categoryId";
    playableCategories = em.createQuery(catQuery, Category.class).getResultList();

    for (int i = 0; i < players; i++) { // each player
      if (i % 1000 == 0) {
        dayOfMonth++;
        startHour = startHour + 1;
        startMinute = startMinute + 1;
      }
      player = (Player) lab03Game.getOrCreatePlayer("Player" + i);
      for (int j = 0; j < games; j++) { // each game
        if (j % 10 == 0) {
          dayOfMonth++;
          startHour = startHour + 3;
          startMinute = startMinute + 1;
        }
        numCategory = rand.nextInt(5);
        numQuestions = rand.nextInt(5);
        allCategories.addAll(playableCategories);
        categoriesToPlay.clear(); // Clear the list before selecting new categories
        Iterator<Category> iterator = allCategories.iterator();
        for (int k = 0; k < numCategory; k++) {
          randomInt = rand.nextInt(allCategories.size());
          iterator = allCategories.iterator();
          for (int m = 0; m < randomInt; m++) {
            iterator.next();
          }
          Category selectedCategory = iterator.next();
          categoriesToPlay.add(selectedCategory);
          iterator.remove(); // Remove the selected category
        }
        gameQuestions = (List<Question>) lab03Game.getQuestions(categoriesToPlay, numQuestions);
        game = (Game) lab03Game.createGame(player, gameQuestions);
        lab03Game.playGame(game);
        try {
          game.setCustomTime(dayOfMonth, startHour, startMinute);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
        lab03Game.persistGame(game);
      }
    }
  }
}
