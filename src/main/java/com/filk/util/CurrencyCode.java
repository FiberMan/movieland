package com.filk.util;

import java.util.ArrayList;
import java.util.List;

public enum CurrencyCode {
    UAH,
    USD,
    EUR;

    public static boolean contains(String code) {
        for (CurrencyCode value : CurrencyCode.values()) {
            if(value.toString().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getStringList() {
        List<String> list = new ArrayList<>();
        for (CurrencyCode code : CurrencyCode.values()) {
            list.add(code.toString());
        }
        return list;
    }
}
