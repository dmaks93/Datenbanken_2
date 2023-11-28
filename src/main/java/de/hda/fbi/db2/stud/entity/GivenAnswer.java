package de.hda.fbi.db2.stud.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "question_id", "game_id" }))
public class GivenAnswer {
  @Id
  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;
  @Id
  @ManyToOne
  @JoinColumn(name = "game_id")
  private Game game;
  private boolean isCorrect;

  public GivenAnswer() {}

  public GivenAnswer(Game game, Question question) {
    this.question = question;
    this.game = game;
  }

  public Question getQuestion() {
    return question;
  }

  public Game getGame() {
    return game;
  }

  public boolean isCorrect() {
    return isCorrect;
  }

  public void setCorrect(boolean correct) {
    isCorrect = correct;
  }
}
