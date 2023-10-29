package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {
  private String name;
  private List<Question> questionList;

  public Category(String name) {
    this.name = name;
    this.questionList = new ArrayList<>();
  }

  /* -------Getter and Setter-------------*/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Question> getQuestionList() {
    return questionList;
  }

  public void addQuestion(Question question) {
    questionList.add(question);
  }
}


