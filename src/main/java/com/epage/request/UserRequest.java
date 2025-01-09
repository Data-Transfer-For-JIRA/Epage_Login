package com.epage.request;

public class UserRequest {
    private String role;
    private String username;
    private String userId;
    private String password;
    private String adminCode;

    public UserRequest() {}
    public UserRequest(String role, String username, String userId, String password, String adminCode) {
        this.role = role;
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.adminCode = adminCode;
    }
    public UserRequest(String role, String username, String userId, String password) {
        this.role = role;
        this.username = username;
        this.userId = userId;
        this.password = password;
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

    public String getAdminCode() { return adminCode; }
    public void setAdminCode(String adminCode) { this.adminCode = adminCode; }
}