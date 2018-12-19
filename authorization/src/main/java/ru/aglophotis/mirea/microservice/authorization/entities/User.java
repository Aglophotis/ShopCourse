package ru.aglophotis.mirea.microservice.authorization.entities;

public class User {
    private int id;
    private String login;
    private String password;
    private String role;
    private String token;

    public User(String login, String password, String role, String token) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
