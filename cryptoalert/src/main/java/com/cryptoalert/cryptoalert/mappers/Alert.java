package com.cryptoalert.cryptoalert.mappers;

import com.cryptoalert.cryptoalert.auth.user.AppUser;

import javax.annotation.Nullable;
import javax.persistence.*;

@Entity
@Table
public class Alert extends ObjectEntity {

    @Id
    @Column(
            name = "id",
            unique = true,
            nullable = false
    )
    public String id;

//    @ManyToOne
//    @MapsId("stockId")
//    @JoinColumn(
//            name = "stock_id",
//            nullable = false,
//            referencedColumnName = "id",
//            foreignKey = @ForeignKey(
//                    name = "alert_stock_fk"
//            )
//    )
//    public String stockId;

    @Column(
            name = "trend",
            nullable = false
    )
    public int trend;

    @Column(
            nullable = true,
            name = "min_slope",
            columnDefinition = "FLOAT"
    )
    public Double minSlope;

    @Column(
            nullable = true,
            name = "min_length",
            columnDefinition = "INT"
    )
    public Integer minLength;

    @Column(
            nullable = true,
            name = "min_market_cap",
            columnDefinition = "INT"
    )
    public Integer minMarketCap;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private AppUser appUser;

    public Alert(String id,
                 int trend,
                 @Nullable Double minSlope,
                 @Nullable Integer minLength,
                 @Nullable Integer minMarketCap,
                 AppUser appUser) {
        super(id);
        this.id = id;
        this.trend = trend;
        this.minSlope = minSlope;
        this.minLength = minLength;
        this.minMarketCap = minMarketCap;
        this.appUser = appUser;
    }

    public Alert() {

    }

    public String getId() {
        return this.id;
    }

}
