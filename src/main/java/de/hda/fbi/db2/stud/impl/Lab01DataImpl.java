package de.hda.fbi.db2.stud.impl;


import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Answer;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Lab01DataImpl extends Lab01Data {
  HashMap<Integer, Question> questionList = new HashMap<>();
  HashMap<String, Category> categoryList = new HashMap<>();

  @Override
  public List<Question> getQuestions() {
    return new ArrayList<>(questionList.values());
  }

  @Override
  public List<Category> getCategories() {
    return new ArrayList<>(categoryList.values());
  }

  @Override
  public void loadCsvFile(List<String[]> csvLines) {
    boolean firstRow = true;
    List<Answer> answerBuffer;
    Category categoryBuffer = null;
    Question questionBuffer;
    for (String[] row : csvLines) {
      if (!firstRow) {

        answerBuffer = new ArrayList<>();
        categoryBuffer = loadCategory(row[7]);
        questionBuffer = new Question(Integer.parseInt(row[0]), row[1], answerBuffer,
            Integer.parseInt(row[6]), categoryBuffer);

        for (int i = 2; i < 6; i++) {
          answerBuffer.add(new Answer(row[i]));
        }
        for (Answer a: answerBuffer) {
          a.setQuestion(questionBuffer);
        }
        questionList.put(questionBuffer.getId(), questionBuffer);
        categoryBuffer.addQuestion(questionBuffer);

      } else {
        firstRow = false;
      }
    }
    this.printOut();
  }

  /**
   * prints out all questions in categories.
   */
  public void printOut() {
    for (Map.Entry<String, Category> c: categoryList.entrySet()) {
      String catName = c.getValue().getName() + ": ";

      System.out.print("Category --> " + catName);
      System.out.print("\n");

      List<Question> catQuestions = c.getValue().getQuestions();

      for (Question q : catQuestions) {
        int questionId = q.getId();
        System.out.print("ID: " + questionId + ": " + q.getText());
        System.out.print("\n");
        System.out.print("Possible Answers: " + "\n");

        List<Answer> questionAnswer = q.getAnswerList();
        for (int i = 0; i < questionAnswer.size(); i++) {
          System.out.print("Answer " + i + " --> " + questionAnswer.get(i).getText() + "\n");
        }
        System.out.print("Correct Answer is: ");
        System.out.print(q.getCorrectAnswer());
        System.out.print("\n\n");
      }
    }
    System.out.print("There are: " + categoryList.size() + " Categories and "
        + questionList.size() + " Questions\n\n");
  }

  /**
   * searches the desired category and inserts new category.
   * @param catName name of the desired category
   * @return reference to the desired category
   */
  public Category loadCategory(String catName) {
    if (categoryList.containsKey(catName)) {
      return categoryList.get(catName);
    } else {
      Category category;
      category = new Category(catName);
      categoryList.put(catName, category);
      return category;
    }
  }
}

