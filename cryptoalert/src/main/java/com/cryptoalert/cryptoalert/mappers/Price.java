package com.cryptoalert.cryptoalert.mappers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Price extends ObjectEntity {

    @Id
    @Column(
            name = "id",
            unique = true,
            nullable = false
    )
    public String id;

    @Column(
            name = "stock_id",
            nullable = false
    )
    public String stockId;

    @Column(
            name = "open",
            nullable = false,
            columnDefinition = "FLOAT"
    )
    public double open;

    @Column(
            name = "close",
            nullable = false,
            columnDefinition = "FLOAT"
    )
    public double close;

    @Column(
            name = "high",
            nullable = false,
            columnDefinition = "FLOAT"
    )
    public double high;

    @Column(
            name = "low",
            nullable = false,
            columnDefinition = "FLOAT"
    )
    public double low;

    @Column(
            name = "time",
            nullable = false,
            columnDefinition = "BIGINT default 0"
    )
    public long time;

    public Price(String id,
                 String stockId,
                 double open,
                 double close,
                 double high,
                 double low,
                 long time) {
        super(id);
        this.id = id;
        this.stockId = stockId;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.time = time;
    }

    public Price() {

    }
}
