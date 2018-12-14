package ru.aglophotis.mirea.microservice.balance.shop.entities;

import ru.aglophotis.mirea.microservice.balance.shop.data.DatabaseContract;

import javax.persistence.*;

@Entity
@Table(name = DatabaseContract.TABLE_NAME)
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DatabaseContract.COLUMN_ID)
    private int id;

    @Column(name = DatabaseContract.COLUMN_AUTHOR_ID)
    private int authorId;

    @Column(name = DatabaseContract.COLUMN_CURRENCY_ID)
    private int currencyId;

    @Column(name = DatabaseContract.COLUMN_BALANCE)
    private double balance;

    public Balance(int authorId, int currencyId, double balance) {
        this.authorId = authorId;
        this.currencyId = currencyId;
        this.balance = balance;
    }

    public Balance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
