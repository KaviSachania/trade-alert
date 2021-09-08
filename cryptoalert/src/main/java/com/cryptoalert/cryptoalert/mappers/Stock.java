package com.cryptoalert.cryptoalert.mappers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Stock extends ObjectEntity {

    @Id
    @Column(
            name = "id",
            unique = true,
            nullable = false
    )
    public String id;

    @Column(
            name = "name",
            unique = true,
            nullable = false
    )
    public String name;

    @Column(
            name = "ticker",
            unique = true,
            nullable = false
    )
    public String ticker;

    @Column(
            name = "market_cap",
            nullable = false,
            columnDefinition = "INT default 0"
    )
    public int marketCap;

    public Stock(String id,
                 String name,
                 String ticker,
                 int marketCap) {
        super(id);
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.marketCap = marketCap;
    }

    public Stock() {

    }

    public String getId() {
        return this.id;
    }

    public void setMarketCap(int marketCap) {
        this.marketCap = marketCap;
    }

    public String toString() {
        return this.id + " " + this.name + " " + this.ticker + " " + this.marketCap;
    }
}
