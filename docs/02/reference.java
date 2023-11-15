package de.hda.fbi.db2;

import de.hda.fbi.db2.stud.entity.Department;
import de.hda.fbi.db2.stud.entity.Employee;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


/**
 * ManyToOne. Sample code for the lecture Databases 2.
 *
 * @author Martin Abel
 * @version 1.1.0 (2023-09-20)
 * @since JDK  8, v1.0.0, 2019-04-01
 * @since JDK 11, v1.1.0, 2023-09-20
 */
public class Main {

  /**
   * Main Method and Entry-Point.
   *
   * @param args Command-Line Arguments.
   */
  public static void main(String[] args) {

    // Connect
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresPU");

    EntityManager em = null;
    EntityTransaction tx = null;
    try {
      em = emf.createEntityManager();
      tx = em.getTransaction();
      tx.begin();

      // Create Departments
      Department department1 = new Department("Development");
      Department department2 = new Department("Administration");

      // Persist
      em.persist(department1);
      em.persist(department2);

      // Create Employees
      Employee employee1 = new Employee("Anton", 41000.0, "Technical Manager");
      Employee employee2 = new Employee("Berti", 42000.0, "Technical Manager");
      Employee employee3 = new Employee("Conny", 51000.0, "Sales Manager");
      Employee employee4 = new Employee("Fritz", 52000.0, "Officer");

      // Persist
      em.persist(employee1);
      em.persist(employee2);
      em.persist(employee3);
      em.persist(employee4);

      // Establish a bidirectional ManyToOne relationship: Ownership = Employee
      // [Employee](0..*) <---> (1..1)[Department]
      employee1.setEdepartment(department1);
      employee2.setEdepartment(department1);
      employee3.setEdepartment(department2);
      employee4.setEdepartment(department2);
      department1.getDemployees().add(employee1);
      department1.getDemployees().add(employee2);
      department2.getDemployees().add(employee3);
      department2.getDemployees().add(employee4);

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

    // Disconnect
    emf.close();
  }
}
