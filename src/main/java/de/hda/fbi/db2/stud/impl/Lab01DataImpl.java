package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Answer;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    Question questionBuffer = null;

    for (String[] row : csvLines
    ) {
      if (!firstRow) {

        answerBuffer = new ArrayList<>();
        for (int i = 2; i < 6; i++) {
          answerBuffer.add(new Answer(row[i]));
        }


        boolean duplicate = false;
        for (Category cat: categoryList
        ) {
          if (Objects.equals(cat.getName(), row[7])) {
            categoryBuffer = cat;
            duplicate = true;
            break;
          }
        }
        if (!duplicate) {
          categoryBuffer = new Category(row[7]);
          categoryList.add(categoryBuffer);
        }
        questionBuffer = new Question(Integer.parseInt(row[0]), row[1], answerBuffer,
            Integer.parseInt(row[6]), categoryBuffer);
        questionList.add(questionBuffer);
        categoryBuffer.addQuestion(questionBuffer);
      } else {
        firstRow = false;
      }
    }

    // print out

    for (Category c : categoryList) {
      String catName = c.getName() + ": ";

      System.out.print(catName);
      System.out.print("\n");

      List<Question> catQuestions = c.getQuestionList();

      for (Question q : catQuestions) {
        int questionId = q.getId();
        System.out.print("ID: " + questionId + ": " + q.getText());
        System.out.print("\n");
        System.out.print("Possible Answers: " + "\n");

        List<Answer> questionAnswer = q.getAnswerList();
        for (int i = 0; i < questionAnswer.size(); i++) {
          System.out.print("Answer " + i++ + " " + questionAnswer.get(i).getText() + "\n");
        }
        System.out.print("Correct Answer is: ");
        System.out.print(q.getCorrectAnswer() + 1);
        System.out.print("\n");
      }

    }
  }
}
