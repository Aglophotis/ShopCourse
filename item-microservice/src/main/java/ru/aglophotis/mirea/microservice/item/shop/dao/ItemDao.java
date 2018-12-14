package ru.aglophotis.mirea.microservice.item.shop.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.aglophotis.mirea.microservice.item.shop.data.DatabaseContract;
import ru.aglophotis.mirea.microservice.item.shop.entities.Item;
import ru.aglophotis.mirea.microservice.item.shop.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class ItemDao {
    public List<Item> getStuffs() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Item where type = 'stuff'").list();
        }
    }

    public void setAmountById(int amount, int itemId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session
                    .createQuery("update Item set amount = :amount where id = :itemId")
                    .setParameter("amount", amount)
                    .setParameter("itemId", itemId)
                    .executeUpdate();
            t.commit();
        }
    }

    public List<Item> getPets() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Item where type = 'pet'").list();
        }
    }

    public Item getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Item.class, id);
        }
    }

    public List<Item> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Item").list();
        }
    }

    public Long insert(Item item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            Long id = (Long) session.save(item);
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

    public void delete(Item item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.delete(item);
            session.getTransaction().commit();
        }
    }

    public void update(Item item) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.update(item);
            session.getTransaction().commit();
        }
    }
}
