package org.softuni.nuggets.models.binding;

public class LoginBindingModel {
    private String egn;

    private String password;

    public LoginBindingModel() {
    }

    public String getEgn() {
        return this.egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
