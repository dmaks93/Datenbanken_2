package de.hda.fbi.db2.stud.entity;

import java.util.Objects;
import javax.persistence.*;

@Embeddable
//@Table(name = "Answer", schema = "db2")
public class Answer {
 //@Id
 //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Answer_id_generator")
 //@SequenceGenerator(name = "Answer_id_generator", sequenceName = "db2.answer_id")
  private int id;
  private String text;
 // @ManyToOne
 // private Question question;

  public Answer() {
  }

  public Answer(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

 // public Question getQuestion() {
 //   return question;
 // }

 // public void setQuestion(Question question) {
 //   this.question = question;
 // }

  //public int getId() {
  //  return id;
  //}

// @Override
// public boolean equals(Object o) {
//   if (this == o) {
//     return true;
//   }
//   if (o == null || getClass() != o.getClass()) {
//     return false;
//   }
//   Answer answer = (Answer) o;
//   return id == answer.id;
// }

// @Override
// public int hashCode() {
//   return Objects.hash(id);
// }
}
