package ru.aglophotis.mirea.microservice.cart.entities;

public class UserInfo {
    private int id;
    private String login;
    private String role;
    private String password;

    public UserInfo(String login, String role, String password) {
        this.login = login;
        this.role = role;
        this.password = password;
    }

    public UserInfo() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
