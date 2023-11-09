package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {
  private int id;
  private String name;
  private List<Question> questionList;

  /**
   * constructor for categories.
   * @param name category name
   */
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

  /**
   * Add a question to the category.
   * @param question the question to be added
   */
  public void addQuestion(Question question) {
    questionList.add(question);
  }

  /** delete a question by id.
   * @param id id of the question to delete
   */
  public void removeQuestion(int id) {
    for (int i = 0; i < questionList.size(); i++) {
      if (questionList.get(i).getId() == id) {
        questionList.remove(i);
      }
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}


