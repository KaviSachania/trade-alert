package com.cryptoalert.cryptoalert.mappers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinnhubStock {
    public final String[] open;
    public final String[] high;
    public final String[] low;
    public final String[] current;
    public final String[] volume;
    public final String[] time;
    public final String status;

    public FinnhubStock(@JsonProperty("o") String[] open,
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
}
