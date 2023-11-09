package de.hda.fbi.db2.stud.entity;

import java.util.List;
import java.util.Objects;

public class Question {
  private int id;
  private String text;
  private List<Answer> answerList;
  private int correctAnswer;
  private Category category;

  /**
   * Constructor for question.
   * @param id question id
   * @param text question text
   * @param answerList list of possible answers (4 items)
   * @param correctAnswer index of the correct answer
   * @param category category of the question
   */
  public Question(int id, String text, List<Answer> answerList, int correctAnswer,
      Category category) {
    this.id = id;
    this.text = text;
    this.answerList = answerList;
    this.correctAnswer = correctAnswer;
    this.category = category;
  }

  /**
   * check if the provided answer is correct.
   * @param index the index of the correct answer
   * @return weather the answer is correct or not
   */
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

  public void setCategory(Category cat) {
    category = cat;
  }

  /**
   * returns the name string of the category.
   * @return the name of the category
   */
  public Category getCategory() {
    return category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Question question = (Question) o;
    return id == question.id && correctAnswer == question.correctAnswer && Objects.equals(
        text, question.text) && Objects.equals(answerList, question.answerList)
        && Objects.equals(category, question.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, answerList, correctAnswer, category);
  }
}
