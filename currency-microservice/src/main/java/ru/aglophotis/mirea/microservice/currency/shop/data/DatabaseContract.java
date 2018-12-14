package ru.aglophotis.mirea.microservice.currency.shop.data;

public interface DatabaseContract {
    String TABLE_NAME = "currencies";

    String COLUMN_ID = "id";
    String COLUMN_CURRENCY = "currency";
    String COLUMN_EXCHANGE_RATE = "exchange_rate";

}
