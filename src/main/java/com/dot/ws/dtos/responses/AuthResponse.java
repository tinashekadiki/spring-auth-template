package com.dot.ws.dtos.responses;

import lombok.Data;

@Data
public class AuthResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String token;
    private final String phoneNumber;

    public AuthResponse(Long id, String name, String email, String token, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.token = token;
        this.phoneNumber = phoneNumber;
    }
}
