package ru.aglophotis.mirea.microservice.identity.entities;

import ru.aglophotis.mirea.microservice.identity.data.DatabaseContract;

import javax.persistence.*;

@Entity
@Table(name = DatabaseContract.TABLE_NAME)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DatabaseContract.COLUMN_ID)
    private int id;

    @Column(name = DatabaseContract.COLUMN_LOGIN, unique = true)
    private String login;

    @Column(name = DatabaseContract.COLUMN_PASSWORD)
    private String password;

    @Column(name = DatabaseContract.COLUMN_ROLE)
    private String role;

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}