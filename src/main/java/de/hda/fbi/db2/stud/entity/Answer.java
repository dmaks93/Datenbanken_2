package de.hda.fbi.db2.stud.entity;

import java.util.Objects;

public class Answer {
  private int id;
  private String text;
  private Question question;

  public Answer(int id, String text) {
    this.text = text;
    this.id = id;
  }

  /* -------Getter and Setter-------------*/
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Answer answer = (Answer) o;
    return id == answer.id && Objects.equals(text, answer.text) && Objects.equals(
        question, answer.question);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, question);
  }
}
