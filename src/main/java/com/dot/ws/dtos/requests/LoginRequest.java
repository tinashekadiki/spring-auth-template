package com.dot.ws.dtos.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull @Email
    private String email;
    @NotNull
    private String password;

    public LoginRequest(String username, String password) {
        this.email = username;
        this.password = password;
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
}
