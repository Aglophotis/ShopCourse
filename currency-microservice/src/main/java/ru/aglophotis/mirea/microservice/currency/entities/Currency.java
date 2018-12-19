package ru.aglophotis.mirea.microservice.currency.entities;

import ru.aglophotis.mirea.microservice.currency.data.DatabaseContract;

import javax.persistence.*;

@Entity
@Table(name = DatabaseContract.TABLE_NAME)
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DatabaseContract.COLUMN_ID)
    private int id;

    @Column(name = DatabaseContract.COLUMN_CURRENCY)
    private String currency;

    @Column(name = DatabaseContract.COLUMN_EXCHANGE_RATE)
    private double exchangeRate;

    public Currency(String currency, double exchangeRate) {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
