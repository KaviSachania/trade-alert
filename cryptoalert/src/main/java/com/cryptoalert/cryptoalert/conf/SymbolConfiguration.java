package com.cryptoalert.cryptoalert.conf;

import java.util.ArrayList;
import java.util.Arrays;

public class SymbolConfiguration {

    private static final ArrayList<String> SYMBOL_LIST = new ArrayList<String>(Arrays.asList(
            "BTC",
            "ETH",
            "DOGE",
            "ALGO",
            "ALPHA",
            "ANKR",
            "ANT",
            "ATM",
            "BAND",
            "BOND",
            "CLV",
            "COMP",
            "COS",
            "DASH",
            "DATA",
            "DENT",
            "DOT",
            "ENJ",
            "EOS",
            "FLOW",
            "FORTH",
            "HIVE",
            "ICP",
            "KAVA",
            "KLAY",
            "LINA",
            "LINK",
            "LIT",
            "LTC",
            "LUNA",
            "MANA",
            "MASK",
            "MATIC",
            "MKR",
            "NANO",
            "NEAR",
            "NEO",
            "NKN",
            "NU",
            "OCEAN",
            "OG",
            "ONE",
            "ONT",
            "PAX",
            "PERL",
            "PERP",
            "POLS",
            "QNT",
            "QTUM",
            "RUNE",
            "SHIB",
            "SOL",
            "STORJ",
            "SUN",
            "THETA",
            "TORN",
            "TROY",
            "UNFI",
            "UNI",
            "WAN",
            "XLM",
            "XRP",
            "ZEN"
    ));

    public static ArrayList<String> getSymbolList() {
        return SYMBOL_LIST;
    }
}
