package ru.aglophotis.mirea.microservice.item.shop.data;

public interface DatabaseContract {
    String TABLE_NAME = "items";

    String COLUMN_TYPE = "type";
    String COLUMN_PRICE = "price";
    String COLUMN_AMOUNT = "amount";
    String COLUMN_ID = "id";
    String COLUMN_NAME = "name";
}
