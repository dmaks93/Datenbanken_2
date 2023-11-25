package de.hda.fbi.db2.stud.entity;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


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
  @ManyToMany
  @JoinTable(
      name = "game_question",
      joinColumns = @JoinColumn(name = "gameId"),
      inverseJoinColumns = @JoinColumn(name = "questionId")
  )
  private List<Question> questionList;
  @ManyToMany
  @JoinTable(
      name = "correct_question",
      joinColumns = @JoinColumn(name = "gameId"),
      inverseJoinColumns = @JoinColumn(name = "questionId")
  )
  private List<Question> correctQuestions;

  public Game() {};

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

  public List<Question> getCorrectQuestions() {
    return correctQuestions;
  }

  public void setCorrectQuestions(List<Question> correctQuestions) {
    this.correctQuestions = correctQuestions;
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
