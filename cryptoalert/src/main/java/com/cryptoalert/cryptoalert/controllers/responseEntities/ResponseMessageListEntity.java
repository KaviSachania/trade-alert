package com.cryptoalert.cryptoalert.controllers.responseEntities;

import java.util.Collection;

public class ResponseMessageListEntity<T> {
    public Collection<T> results;

    public ResponseMessageListEntity(Collection<T> results) {
        this.results = results;
    }

}
