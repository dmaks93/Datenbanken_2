package de.hda.fbi.db2.stud.entity;

public class Answer {
  private String text;

  public Answer() {

  }

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
}
