package ru.aglophotis.mirea.microservice.currency.shop.dao;

import org.hibernate.Session;
import ru.aglophotis.mirea.microservice.currency.shop.data.DatabaseContract;
import ru.aglophotis.mirea.microservice.currency.shop.entities.Currency;
import ru.aglophotis.mirea.microservice.currency.shop.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class CurrencyDao {
    public Currency getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Currency.class, id);
        }
    }

    public List<Currency> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Currency").list();
        }
    }

    public int insert(Currency item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            int id = (int) session.save(item);
            session.getTransaction().commit();
            return id;
        }
    }

    public void deleteAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM " + DatabaseContract.TABLE_NAME).executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void delete(Currency item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.delete(item);
            session.getTransaction().commit();
        }
    }

    public void update(Currency item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.update(item);
            session.getTransaction().commit();
        }
    }
}
