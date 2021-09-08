package com.cryptoalert.cryptoalert.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceNotFoundException extends RuntimeException {

    protected final ResponseEntity entity_;

    public ResourceNotFoundException(String message) {
        super(message, null);
        this.entity_ = new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
