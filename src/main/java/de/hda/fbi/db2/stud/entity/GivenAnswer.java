package de.hda.fbi.db2.stud.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GivenAnswer that = (GivenAnswer) o;
    return isCorrect == that.isCorrect && Objects.equals(question, that.question)
        && Objects.equals(game, that.game);
  }

  @Override
  public int hashCode() {
    return this.question.getQuestionId() + this.game.getGameId();
  }
}
