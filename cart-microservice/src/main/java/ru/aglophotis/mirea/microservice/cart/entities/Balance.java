package ru.aglophotis.mirea.microservice.cart.entities;

public class Balance {
    private int id;
    private int authorId;
    private int currencyId;
    private double balance;

    public Balance(int id, int authorId, int currencyId, double balance) {
        this.id = id;
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
