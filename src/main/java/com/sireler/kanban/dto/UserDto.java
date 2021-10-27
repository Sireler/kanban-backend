package com.sireler.kanban.dto;

import com.sireler.kanban.model.User;

public class UserDto {

    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }
}
