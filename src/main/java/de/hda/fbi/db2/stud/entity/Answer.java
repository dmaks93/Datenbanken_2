package de.hda.fbi.db2.stud.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class Answer {
  @Column(unique = true)
  private int answerId;
  private String text;

  public Answer() {}

  public Answer(int answerId, String text) {
    this.answerId = answerId;
    this.text = text;
  }

  public int getAnswerId() {
    return answerId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
