package com.jpmorgan.interview.enums;

import java.util.Arrays;
import java.util.HashSet;

import java.util.Calendar;

/**
 * Created by bipo on 06/08/2017.
 */
public enum Currency {

    GENERAL("GENERAL", new HashSet<>(Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY))),
    SAR("SAR", new HashSet<>(Arrays.asList(Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY))),
    AED("AED", new HashSet<>(Arrays.asList(Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY)));

    private final HashSet<Integer> validWorkDays;
    private final String currencySign;

    Currency(String currencySign, HashSet<Integer> validWorkDays) {
        this.currencySign = currencySign;
        this.validWorkDays = validWorkDays;
    }

    public HashSet<Integer> getValidWorkDays() {
        return validWorkDays;
    }

    @Override
    public String toString() {
        return currencySign;
    }

    public static Currency getCurrency(String currencySign) {

        if (SAR.toString().equals(currencySign)) {
            return SAR;
        } else if (AED.toString().equals(currencySign)) {
            return AED;
        } else {
            return GENERAL;
        }
    }
}
