package com.sakura.jdbc.learn2;

/**
 * @ClassName User
 * @Author Sakura
 * @DateTime 2024-06-09 00:46:00
 * @Version 1.0
 */
public class User {
    private String user;
    private String password;

    public User() {
    }

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PreparedStatementTest{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
