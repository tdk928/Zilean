package org.softuni.nuggets.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterBindingModel {
    @NotEmpty(message = "EGN name cannot be empty.")
    @Size(min=10, max=10, message = "Your EGN must be exactly 10 characters.")
    private String username;

    @NotEmpty
    @Size(min=3, max=10, message = "Password must be between 3 and 10 symbols.")
    private String password;

    @NotEmpty
    @Size(min=3, max=10, message = "Password must be between 3 and 10 symbols.")
    private String confirmPassword;

    @NotEmpty(message = "EGN name cannot be empty.")
    @Size(max=20, message = "Your first name must be maximum 20 characters.")
    private String firstName;

    @NotEmpty(message = "EGN name cannot be empty.")
    @Size(max=20, message = "Your last name must be maximum 20 characters.")
    private String lastName;

    private double salary;

    public RegisterBindingModel() {
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
