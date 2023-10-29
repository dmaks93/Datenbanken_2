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
    Category categoryBuffer;

    for (String[] row : csvLines
    ) {
      if (!firstRow) {

        answerBuffer = new ArrayList<>();
        for (int i = 2; i < 6; i++) {
          answerBuffer.add(new Answer(row[i]));
        }

        categoryBuffer = new Category(row[7]);
        boolean duplicate = false;
        for (Category cat: categoryList
        ) {
          if (Objects.equals(cat.getName(), categoryBuffer.getName())) {
            categoryBuffer = categoryList.get(categoryList.size() - 1);
            duplicate = true;
            break;
          }
        }
        if (!duplicate) {
          categoryList.add(categoryBuffer);
        }
        questionList.add(new Question(Integer.parseInt(row[0]), row[1], answerBuffer,
            Integer.parseInt(row[6]), categoryBuffer));
      } else {
        firstRow = false;
      }
    }
  }
}
