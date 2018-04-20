package org.softuni.nuggets.models.binding;

public class LoginBindingModel {
    private String id;

    private String username;

    private String password;

    public LoginBindingModel() {
    }

    public LoginBindingModel(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
