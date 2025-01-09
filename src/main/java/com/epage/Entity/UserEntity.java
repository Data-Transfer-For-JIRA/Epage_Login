package com.epage.Entity;

import java.util.Date;

public class UserEntity {
    private String role;
    private String username;
    private String userId;
    private String password;
    private Date expireDate;


    public UserEntity() {}
    public UserEntity(String role, String username, String userId, String password, Date expireDate) {
        this.role = role;
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.expireDate = expireDate;
    }
    // Getters and setters
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Date getExpireDate() { return expireDate; }
    public void setExpireDate(Date expireDate) { this.expireDate = expireDate; }
}