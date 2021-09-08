package com.cryptoalert.cryptoalert.mappers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinnhubPrice {
    public final String[] open;
    public final String[] high;
    public final String[] low;
    public final String[] current;
    public final String[] volume;
    public final String[] time;
    public final String status;

    public FinnhubPrice(@JsonProperty("o") String[] open,
                        @JsonProperty("h") String[] high,
                        @JsonProperty("l") String[] low,
                        @JsonProperty("c") String[] current,
                        @JsonProperty("v") String[] volume,
                        @JsonProperty("t") String[] time,
                        @JsonProperty("s") String status) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.current = current;
        this.volume = volume;
        this.time = time;
        this.status = status;
    }

    public double[] getLatestPrice() {
        double[] latestPrice = new double[4];
        latestPrice[0] = Double.parseDouble(this.open[this.open.length-1]);
        latestPrice[1] = Double.parseDouble(this.current[this.current.length-1]);
        latestPrice[2] = Double.parseDouble(this.high[this.high.length-1]);
        latestPrice[3] = Double.parseDouble(this.low[this.low.length-1]);

        return latestPrice;
    }
}
