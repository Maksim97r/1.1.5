package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getFactory().getCurrentSession();
        try (session) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS `pred_progect1`.`user` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT(3) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`))").executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getFactory().getCurrentSession();

        try (session) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user;").executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = Util.getFactory().getCurrentSession();

        try (session) {
            User newUser = new User(name, lastName, age);
            session.beginTransaction();
            session.save(newUser);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        Session session = Util.getFactory().getCurrentSession();

        try (session) {
            session.beginTransaction();
            User newUser = session.get(User.class, id);
            session.delete(newUser);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getFactory().getCurrentSession();
        List<User> allUser = new ArrayList<>();

        try (session) {
            session.beginTransaction();
            allUser = session.createQuery("from User")
                    .getResultList();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        return allUser;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getFactory().getCurrentSession();

        try (session) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
