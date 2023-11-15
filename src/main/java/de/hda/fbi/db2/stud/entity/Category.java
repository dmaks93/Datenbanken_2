package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Category", schema = "db2")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_id_generator")
  @SequenceGenerator(name = "cat_id_generator", sequenceName = "db2.cat_id")
  private int id;
  @Column(name = "name", unique = true)
  private String name;

  @OneToMany(mappedBy = "category")
  private List<Question> questionList;

  public Category() {}

  public Category(String name) {
    this.name = name;
    this.questionList = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Question> getQuestions() {
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



  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return id == category.id;
  }
}


