package de.hda.fbi.db2.stud.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;


@Entity
public class Question {
  @Id
  private int questionId;
  private String text;
  private int correctAnswer;

  @ElementCollection
  @OrderColumn(name = "index")
  private List<Answer> answerList;
  @ManyToOne
  private Category category;

  public Question(){}

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
    this.questionId = id;
    this.text = text;
    this.answerList = answerList;
    this.correctAnswer = correctAnswer;
    this.category = category;
  }

  public int getQuestionId() {
    return questionId;
  }

  public String getText() {
    return text;
  }

  /**
   * check if the provided answer is correct.
   * @param index the index of the correct answer
   * @return weather the answer is correct or not
   */
  public boolean checkAnswer(int index) {
    return this.correctAnswer == index;
  }

  /**
   * returns the name string of the category.
   * @return the name of the category
   */
  public Category getCategory() {
    return category;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setCorrectAnswer(int correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public void setAnswerList(List<Answer> answerList) {
    this.answerList = answerList;
  }

  public List<Answer> getAnswerList() {
    return answerList;
  }

  public void setCategory(Category cat) {
    category = cat;
  }

  public int getCorrectAnswer() {
    return correctAnswer;
  }



  @Override
  public int hashCode() {
    return Objects.hash(questionId);
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
    return questionId == question.questionId && correctAnswer == question.correctAnswer
        && Objects.equals(text, question.text) && Objects.equals(answerList,
        question.answerList) && Objects.equals(category, question.category);
  }
}
