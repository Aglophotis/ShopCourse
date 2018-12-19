package ru.aglophotis.mirea.microservice.cart.entities;

public class Currency {
    private int id;
    private String currency;
    private double exchangeRate;

    public Currency(int id, String currency, double exchangeRate) {
        this.id = id;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
    }

    public Currency() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
