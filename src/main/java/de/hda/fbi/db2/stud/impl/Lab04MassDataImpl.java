package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;

public class Lab04MassDataImpl extends Lab04MassData {

  @Override
  public void createMassData() {
    int players = 10000;
    int games = 100;

    String pName;
    int numCategory;
    int numQuestion;
    List<Category> categorysToPlay = new ArrayList<>();
    List<Category> allCategories = new ArrayList<>();
    Random rand = new Random();
    int randomInt;
    String query;

    EntityManager em = lab02EntityManager.getEntityManager();
    for (int i = 0; i < players; i++) { // each player
      pName = "Player_" + i;
      for (int j = 0; j < games; j++) { // each game
        numCategory = rand.nextInt(5);
        numQuestion = rand.nextInt(5);
        query = "SELECT c FROM Category c";
        allCategories = em.createQuery(query, Category.class).getResultList();
        for (int k = 0; k < numCategory; k++) {
          randomInt = rand.nextInt(allCategories.size());
          categorysToPlay.add(allCategories.get(randomInt));
          allCategories.remove(randomInt);

        }
        lab03Game.getQuestions();
      }
    }
  }
}
