package ru.aglophotis.mirea.microservice.identity.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.aglophotis.mirea.microservice.identity.data.DatabaseContract;
import ru.aglophotis.mirea.microservice.identity.entities.User;
import ru.aglophotis.mirea.microservice.identity.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDao {
    public User getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    public User getUserByLogin(String login) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            User userDB = (User)session.createQuery("from User where login = :login")
                    .setParameter("login", login)
                    .uniqueResult();
            return userDB;
        }
    }

    public List<User> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User").list();
        }
    }

    public int insert(User item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            int id = (int) session.save(item);
            session.getTransaction().commit();
            return id;
        } catch (HibernateException e) {
            return -1;
        }
    }

    public void deleteAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM " + DatabaseContract.TABLE_NAME).executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void delete(User item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.delete(item);
            session.getTransaction().commit();
        }
    }

    public Integer updateToken(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            int id = session.createQuery("UPDATE User set token = :token where login = :login")
                    .setParameter("token", user.getToken())
                    .setParameter("login", user.getLogin())
                    .executeUpdate();
            session.getTransaction().commit();
            return id;
        }
    }
}
