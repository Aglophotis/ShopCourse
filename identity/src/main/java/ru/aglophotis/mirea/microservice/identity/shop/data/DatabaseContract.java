package ru.aglophotis.mirea.microservice.identity.shop.data;

public interface DatabaseContract {
    String TABLE_NAME = "users";

    String COLUMN_ID = "id";
    String COLUMN_LOGIN = "login";
    String COLUMN_PASSWORD = "password";
    String COLUMN_ROLE = "role";
    String COLUMN_TOKEN = "token";
}
