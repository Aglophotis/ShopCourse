package ru.aglophotis.mirea.microservice.balance.entities;

public class Payload {
    private int sub;
    private String name;
    private String role;
    private long iat;
    private long exp;

    public Payload(int sub, String name, String role, long iat, long exp) {
        this.sub = sub;
        this.name = name;
        this.role = role;
        this.iat = iat;
        this.exp = exp;
    }

    public Payload() {
    }

    public int getSub() {
        return sub;
    }

    public void setSub(int sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getIat() {
        return iat;
    }

    public void setIat(long iat) {
        this.iat = iat;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }
}
