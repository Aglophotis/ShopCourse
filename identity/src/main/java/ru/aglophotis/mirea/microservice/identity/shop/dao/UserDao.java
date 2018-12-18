package ru.aglophotis.mirea.microservice.identity.shop.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.aglophotis.mirea.microservice.identity.shop.data.DatabaseContract;
import ru.aglophotis.mirea.microservice.identity.shop.entities.User;
import ru.aglophotis.mirea.microservice.identity.shop.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDao {
    public User getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
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

    public void update(User item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.update(item);
            session.getTransaction().commit();
        }
    }
}
