package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import de.hda.fbi.db2.stud.entity.Player;
import de.hda.fbi.db2.stud.entity.Game;

public class Lab04MassDataImpl extends Lab04MassData {

  @Override
  public void createMassData() {
    int players = 10000;
    int games = 100;

    int numCategory;
    int numQuestions;
    List<Category> categoriesToPlay = new ArrayList<>();
    List<Category> allCategories = new ArrayList<>();
    List<Question> gameQuestions = new ArrayList<>();
    Random rand = new Random();
    int randomInt;
    String query;
    Player player;
    Game game;

    EntityManager em = lab02EntityManager.getEntityManager();
    for (int i = 0; i < players; i++) { // each player
      player = (Player) lab03Game.getOrCreatePlayer("Player" + i);
      for (int j = 0; j < games; j++) { // each game
        numCategory = rand.nextInt(5);
        numQuestions = rand.nextInt(5);
        query = "SELECT c FROM Category c";
        allCategories = em.createQuery(query, Category.class).getResultList();
        for (int k = 0; k < numCategory; k++) {
          randomInt = rand.nextInt(allCategories.size());
          System.out.println("Game: " + j + ", Category: " + randomInt);
          categoriesToPlay.add(allCategories.get(randomInt));
          allCategories.remove(randomInt);
        }
        gameQuestions = (List<Question>) lab03Game.getQuestions(categoriesToPlay, numQuestions);
        game = (Game) lab03Game.createGame(player, gameQuestions);
        lab03Game.playGame(game);
        //game.set
        lab03Game.persistGame(game);
      }
    }
  }
}
