package com.cryptoalert.cryptoalert.mappers;

import javax.persistence.Id;

public class ObjectEntity {

    @Id
    public String id;

    public ObjectEntity() {

    }

    public ObjectEntity(String id) {
        this.id = id;
    }

    public String toString() {
        return this.id;
    }

}
