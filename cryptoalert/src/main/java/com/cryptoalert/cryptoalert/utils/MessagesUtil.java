package com.cryptoalert.cryptoalert.utils;

import com.cryptoalert.cryptoalert.mappers.Price;

public class MessagesUtil {

    public static String priceMessageBuilder(Price price, String ticker) {
        return String.format("%s:\n" +
                        "Current: $%s\n\n",
                ticker.toUpperCase(),
                price.close);
    }
}
