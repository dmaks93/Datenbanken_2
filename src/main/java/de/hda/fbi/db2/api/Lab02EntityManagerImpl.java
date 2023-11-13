package de.hda.fbi.db2.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Lab02EntityManagerImpl extends Lab02EntityManager {

  /**
   * Creates the {@link EntityManagerFactory} and stores it in a field. {@link #destroy()} should
   * then clean up the factory later again.
   */
  @Override
  public void init() {

  }

  /**
   * {@linkplain EntityManagerFactory#close() Closes} the {@code EntityManagerFactory} created by
   * {@link #init()}.
   */
  @Override
  public void destroy() {

  }

  /**
   * Here you have to persist the data in the database.
   */
  @Override
  public void persistData() {

  }

  /**
   * Creates a new {@code EntityManager}.
   *
   * @return new EntityManager
   */
  @Override
  public EntityManager getEntityManager() {
    return null;
  }
}
