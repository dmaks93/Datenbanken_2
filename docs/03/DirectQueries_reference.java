package de.hda.fbi.db2;

import de.hda.fbi.db2.stud.entity.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 * DirectQueries. Sample code for the lecture Databases 2.
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
    @SuppressWarnings(value = {"unchecked"})
    public static void main(String[] args) {

        // Connect
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresPU");

        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Create Employees
            Employee employee1 = new Employee("Anton", 41000.0, "Technical Manager");
            Employee employee2 = new Employee("Berti", 42000.0, "Technical Manager");
            Employee employee3 = new Employee("Conny", 51000.0, "Sales Manager");
            Employee employee4 = new Employee("Fritz", 52000.0, "Officer");

            // Persist Employees (and make them available for JPQL queries)
            em.persist(employee1);
            em.persist(employee2);
            em.persist(employee3);
            em.persist(employee4);

            // Scalar result (incl. some exception handling)
            try {
                Query query1 = em.createQuery("select max(e.esalary) from Employee e");
                Double result1 = (Double) query1.getSingleResult();
                System.out.println("Max Employee Salary: " + result1);
            } catch (NoResultException e) { // i.e. select e.esalary from Employee e where e.eid = 23
                System.out.println("Sorry, no result found!");
            } catch (NonUniqueResultException e) { // i.e. select e.esalary from Employee e
                System.out.println("Sorry, non unique results!");
            }

            // Scalar list result (incl. aggregation function)
            Query query2 = em.createQuery("select UPPER(e.ename) from Employee e");
            List<String> result2 = query2.getResultList();
            for (String e : result2) {
                System.out.println("Employee NAME: " + e);
            }

            // Entity list result (incl. where clause using like, between, parameter and order by)
            Query query3 = em.createQuery(
                    "select e from Employee e "
                            + "where e.ename like '%t%' and e.esalary between :min and :max "
                            + "order by e.esalary desc");
            query3.setParameter("min", 40000);
            query3.setParameter("max", 50000);
            List<Employee> result3 = (List<Employee>) query3.getResultList();
            for (Employee e : result3) {
                System.out.println(e.getEname() + "\t" + e.getEsalary());
            }

            // Update (incl. where clause using parameters)
            Query query4 = em.createQuery(
                    "update Employee e set e.esalary = :sal where e.eid = :id");
            query4.setParameter("sal", 46000);
            query4.setParameter("id", 1);
            int count4 = query4.executeUpdate();
            System.out.println("Number of updates: " + count4);

            // Delete (incl. where clause using parameters)
            int count5 = em.createQuery("delete from Employee e where e.eid = :id")
                    .setParameter("id", 2)
                    .executeUpdate();
            System.out.println("Number of deletes: " + count5);

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
