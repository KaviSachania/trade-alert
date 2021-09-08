package com.cryptoalert.cryptoalert.controllers.responseEntities;

public class ResponseMessageEntity<T> {
    public T results;

    public ResponseMessageEntity(T results) {
        this.results = results;
    }

//    public ResponseEntity<ResponseMessageEntity<T>> getResponse() {
//        return new ResponseEntity<ResponseMessageEntity<T>>(this, HttpStatus.OK);
//    }

}
