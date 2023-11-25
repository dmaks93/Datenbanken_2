package de.hda.fbi.db2.stud.entity;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Game {
  @Id
  private int gameId;

  private Date start;
  private Date end;

  private Player player;

  private List<Question> questionList;

  public Game(Player player, List<Question> questions) {
    this.player = player;
    this.questionList = questions;
  }

  public List<Question> getQuestionList() {
    return questionList;
  }

  public Player getPlayer() {
    return player;
  }

  public void setQuestionList(List<Question> questionList) {
    this.questionList = questionList;
  }

  public int getGameID() {
    return gameId;
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Game game = (Game) o;
    return gameId == game.gameId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(gameId);
  }
}
