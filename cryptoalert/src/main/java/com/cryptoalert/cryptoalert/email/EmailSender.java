package com.cryptoalert.cryptoalert.email;

public interface EmailSender {
    void send(String to, String email);
}