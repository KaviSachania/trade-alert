package com.cryptoalert.cryptoalert.auth.registration;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class RegistrationRequest {
    private final String email;
    private final String password;

    public RegistrationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String toString() {
        return this.email + " " + this.password;
    }

}