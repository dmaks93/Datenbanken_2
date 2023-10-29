package de.hda.fbi.db2.stud.entity;

import java.util.List;

public class Question {
  private int id;
  private String text;
  private List<Answer> answerList;
  private int correctAnswer;
  private Category questionCategory;
  public Question() {
  }

  public Question(int id, String text, List<Answer> answerList, int correctAnswer, Category questionCategory) {
    this.id = id;
    this.text = text;
    this.answerList = answerList;
    this.correctAnswer = correctAnswer;
    this.questionCategory = questionCategory;
  }

  public boolean checkAnswer(int index) {
    return this.correctAnswer == index;
  }

  /* -------Getter and Setter-------------*/
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<Answer> getAnswerList() {
    return answerList;
  }

  public void setAnswerList(List<Answer> answerList) {
    this.answerList = answerList;
  }

  public int getCorrectAnswer() {
    return correctAnswer;
  }

  public void setCorrectAnswer(int correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public void setQuestionCategory(Category cat) {
    questionCategory = cat;
  }

  public String getQuestionCategory() {
    return questionCategory.getName();
  }
}
