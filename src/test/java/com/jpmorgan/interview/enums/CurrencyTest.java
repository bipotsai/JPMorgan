package com.jpmorgan.interview.enums;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bipo on 06/08/2017.
 */
public class CurrencyTest {
    @Test
    public void getCurrency() throws Exception {
        String currencySign = "USD";
        assertEquals(Currency.GENERAL, Currency.getCurrency(currencySign));
        currencySign = "GBP";
        assertEquals(Currency.GENERAL, Currency.getCurrency(currencySign));
        currencySign = "AED";
        assertEquals(Currency.AED, Currency.getCurrency(currencySign));
        currencySign = "SAR";
        assertEquals(Currency.SAR, Currency.getCurrency(currencySign));
    }

}