package com.cryptoalert.cryptoalert.mappers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
public class Candidate extends ObjectEntity {

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
            name = "trend",
            nullable = false
    )
    public int trend;

    @Column(
            name = "rebounds",
            nullable = false,
            columnDefinition = "INT"
    )
    public int rebounds;

    @Column(
            name = "slope",
            nullable = false,
            columnDefinition = "FLOAT"
    )
    public double slope;

    @Column(
            name = "anchor_price",
            nullable = false,
            columnDefinition = "FLOAT"
    )
    public double anchorPrice;

    @Column(
            name = "anchor_time",
            nullable = false,
            columnDefinition = "INT"
    )
    public int anchorTime;

    @Column(
            name = "length",
            nullable = false,
            columnDefinition = "BIGINT default 0"
    )
    public long length;

    @Column(
            name = "is_new",
            nullable = false
    )
    public boolean isNew;

    @Column(nullable = false)
    public LocalDateTime createdAt;

    public Candidate(String id,
                     String stockId,
                     int trend,
                     int rebounds,
                     double slope,
                     double anchorPrice,
                     int anchorTime,
                     long length,
                     boolean isNew,
                     LocalDateTime createdAt) {
        super(id);
        this.id = id;
        this.stockId = stockId;
        this.trend = trend;
        this.rebounds = rebounds;
        this.slope = slope;
        this.anchorPrice = anchorPrice;
        this.anchorTime = anchorTime;
        this.length = length;
        this.isNew = isNew;
        this.createdAt = createdAt;
    }

    public Candidate() {

    }

    public String toString() {
        return this.id + " " +
                this.stockId + " " +
                this.trend + " " +
                this.rebounds + " " +
                this.slope + " " +
                this.anchorPrice + " " +
                this.anchorTime + " " +
                this.length + " " +
                this.isNew + " " +
                this.createdAt;
    }
}
