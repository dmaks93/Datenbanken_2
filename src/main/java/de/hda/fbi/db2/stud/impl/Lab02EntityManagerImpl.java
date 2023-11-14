package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.stud.entity.Answer;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Lab02EntityManagerImpl extends Lab02EntityManager {
 private EntityManagerFactory emf = null;

  /**
   * Creates the {@link EntityManagerFactory} and stores it in a field. {@link #destroy()} should
   * then clean up the factory later again.
   */

  @Override
  public void init() {
     emf = Persistence.createEntityManagerFactory("fbi-postgresPU");
  }

  /**
   * {@linkplain EntityManagerFactory#close() Closes} the {@code EntityManagerFactory} created by
   * {@link #init()}.
   */
  @Override
  public void destroy() {
    emf.close();
  }

  /**
   * Here you have to persist the data in the database.
   */
  @Override
  public void persistData() {
    EntityManager em = getEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();

      List<Question> allQuestions = (List<Question>) lab01Data.getQuestions();

        for (Question q : allQuestions) {
            List<Answer> possibleAnswers = q.getAnswerList();
            for (Answer a: possibleAnswers) {
                if (!em.contains(a)) {
                    em.persist(a);
                }
            }
            if (!em.contains(q)) {
                em.persist(q);
            }

            Category c = q.getCategory();
            if (!em.contains(c)) {
                em.persist(c);
            }
        }

      tx.commit();

    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        tx.rollback();
      }
      throw e;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  /**
   * Creates a new {@code EntityManager}.
   *
   * @return new EntityManager
   */
  @Override
  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }
}
