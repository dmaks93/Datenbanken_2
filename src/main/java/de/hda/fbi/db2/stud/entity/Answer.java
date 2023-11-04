package de.hda.fbi.db2.stud.entity;

public class Answer {
  private String text;
  private Question question;

  public Answer(String text) {
    this.text = text;
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
}
