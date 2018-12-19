package ru.aglophotis.mirea.microservice.balance.shop.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.aglophotis.mirea.microservice.balance.shop.data.DatabaseContract;
import ru.aglophotis.mirea.microservice.balance.shop.entities.Balance;
import ru.aglophotis.mirea.microservice.balance.shop.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class BalanceDao {
    public List<Balance> getAll(int authorId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Balance where authorId = :authorId")
                    .setParameter("authorId", authorId)
                    .list();
        }
    }

    public int updateById(double balance, int currencyId, int authorId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            int id = session
                    .createQuery("update Balance set balance = :balance where authorId = :authorId and currencyId = :currencyId")
                    .setParameter("authorId", authorId)
                    .setParameter("balance", balance)
                    .setParameter("currencyId", currencyId)
                    .executeUpdate();
            t.commit();
            return id;
        }
    }

    public Balance getByCurrencyId(int currencyId, int authorId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return (Balance) session.createQuery("from Balance where authorId = :authorId and currencyId = :currencyId")
                    .setParameter("authorId", authorId)
                    .setParameter("currencyId", currencyId)
                    .uniqueResult();
        }
    }

    public int insert(Balance item) {
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

    public void delete(Balance item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.delete(item);
            session.getTransaction().commit();
        }
    }

    public void update(Balance balance) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.update(balance);
            session.getTransaction().commit();
        }
    }
}
