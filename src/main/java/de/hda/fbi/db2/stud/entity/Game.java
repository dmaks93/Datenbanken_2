package de.hda.fbi.db2.stud.entity;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_id_generator")
  @SequenceGenerator(name = "game_id_generator", sequenceName = "db2.game_id")
  private int gameId;
  @Temporal(TemporalType.DATE)
  private Date start;
  @Temporal(TemporalType.DATE)
  private Date end;
  @ManyToOne
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
