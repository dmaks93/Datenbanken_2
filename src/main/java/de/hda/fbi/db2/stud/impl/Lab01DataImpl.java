package de.hda.fbi.db2.stud.impl;


import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Answer;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Lab01DataImpl extends Lab01Data {
  List<Question> questionList = new ArrayList<>();
  List<Category> categoryList = new ArrayList<>();

  @Override
  public List<Question> getQuestions() {
    return questionList;
  }

  @Override
  public List<Category> getCategories() {
    return categoryList;
  }

  @Override
  public void loadCsvFile(List<String[]> csvLines) {
    boolean firstRow = true;
    List<Answer> answerBuffer;
    Category categoryBuffer = null;
    Question questionBuffer;
    int catId = 0;
    for (String[] row : csvLines) {
      if (!firstRow) {

        answerBuffer = new ArrayList<>();
        for (int i = 2; i < 6; i++) {
          answerBuffer.add(new Answer(row[i]));
        }

        categoryBuffer = loadCategory(row[7]);

        questionBuffer = new Question(Integer.parseInt(row[0]), row[1], answerBuffer,
            Integer.parseInt(row[6]), categoryBuffer);
        questionList.add(questionBuffer);
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
    for (Category c : categoryList) {
      String catName = c.getName() + ": ";

      System.out.print("Category --> " + catName);
      System.out.print("\n");

      List<Question> catQuestions = c.getQuestionList();

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
   * searches the desired category with O(log n) and inserts new categories with O(n).
   * @param catName name of the desired category
   * @return reference to the desired category
   */
  public Category loadCategory(String catName) {
    Category category;
    category = new Category(catName);
    int position = Collections.binarySearch(categoryList, category, new CategoryComparator());
    if (position >= 0) { // Wenn Element gefunden
      return categoryList.get(position);
    } else {
      position = -position - 1;
      categoryList.add(position, category);
      return category;
    }
  }
}
