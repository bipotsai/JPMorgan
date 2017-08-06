package com.jpmorgan.interview.util;

import com.jpmorgan.interview.enums.Currency;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by bipo on 06/08/2017.
 */
public class TradeDateUtilTest {
    @Test
    public void isWorkDay() throws Exception {


        //test GENERAL settlement date on Monday
        Currency currency = Currency.GENERAL;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date settlementDate = sdf.parse("07 Aug 2017");//Monday
        assertTrue(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test SAR settlement date on Saturday
        currency = Currency.GENERAL;
        settlementDate = sdf.parse("12 Aug 2017");//Saturday
        assertFalse(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test GENERAL settlement date on Sunday
        settlementDate = sdf.parse("06 Aug 2017");//Sunday
        assertFalse(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test SAR settlement date on Monday
        currency = Currency.SAR;
        settlementDate = sdf.parse("07 Aug 2017");//Monday
        assertTrue(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test SAR settlement date on SunDay
        currency = Currency.SAR;
        settlementDate = sdf.parse("06 Aug 2017");//Sunday
        assertTrue(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test SAR settlement date on Friday
        currency = Currency.SAR;
        settlementDate = sdf.parse("11 Aug 2017");//Friday
        assertFalse(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test SAR settlement date on Saturday
        currency = Currency.SAR;
        settlementDate = sdf.parse("12 Aug 2017");//Saturday
        assertFalse(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test AED settlement date on Monday
        currency = Currency.AED;
        settlementDate = sdf.parse("07 Aug 2017");//Monday
        assertTrue(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test AED settlement date on SunDay
        currency = Currency.AED;
        settlementDate = sdf.parse("06 Aug 2017");//Sunday
        assertTrue(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test AED settlement date on Friday
        currency = Currency.AED;
        settlementDate = sdf.parse("11 Aug 2017");//Friday
        assertFalse(TradeDateUtil.isWorkDay(currency, settlementDate));

        //test AED settlement date on Saturday
        currency = Currency.AED;
        settlementDate = sdf.parse("12 Aug 2017");//Saturday
        assertFalse(TradeDateUtil.isWorkDay(currency, settlementDate));

    }

    @Test
    public void getNextWorkDay() throws Exception {
        //test GENERAL settlement date on Monday
        Currency currency = Currency.GENERAL;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date settlementDate = sdf.parse("07 Aug 2017");//Monday
        assertEquals("08 Aug 2017", sdf.format(TradeDateUtil.getNextWorkDay(currency, settlementDate)));

        //test GENERAL settlement date on Saturday
        currency = Currency.GENERAL;
        settlementDate = sdf.parse("05 Aug 2017");//Saturday
        assertEquals("07 Aug 2017", sdf.format(TradeDateUtil.getNextWorkDay(currency, settlementDate)));

        //test SAR settlement date on Monday
        currency = Currency.SAR;
        settlementDate = sdf.parse("07 Aug 2017");//Monday
        assertEquals("08 Aug 2017", sdf.format(TradeDateUtil.getNextWorkDay(currency, settlementDate)));

        //test SAR settlement date on SunDay
        currency = Currency.SAR;
        settlementDate = sdf.parse("06 Aug 2017");//Sunday
        assertEquals("07 Aug 2017", sdf.format(TradeDateUtil.getNextWorkDay(currency, settlementDate)));

        //test SAR settlement date on Saturday
        currency = Currency.SAR;
        settlementDate = sdf.parse("05 Aug 2017");//Saturday
        assertEquals("06 Aug 2017", sdf.format(TradeDateUtil.getNextWorkDay(currency, settlementDate)));

        //test AED settlement date on Monday
        currency = Currency.AED;
        settlementDate = sdf.parse("07 Aug 2017");//Monday
        assertEquals("08 Aug 2017", sdf.format(TradeDateUtil.getNextWorkDay(currency, settlementDate)));

        //test AED settlement date on SunDay
        currency = Currency.AED;
        settlementDate = sdf.parse("06 Aug 2017");//Sunday
        assertEquals("07 Aug 2017", sdf.format(TradeDateUtil.getNextWorkDay(currency, settlementDate)));

        //test SAR settlement date on Saturday
        currency = Currency.AED;
        settlementDate = sdf.parse("05 Aug 2017");//Saturday
        assertEquals("06 Aug 2017", sdf.format(TradeDateUtil.getNextWorkDay(currency, settlementDate)));

        //test SAR settlement date on Thurday
        currency = Currency.AED;
        settlementDate = sdf.parse("10 Aug 2017");//Thurday
        assertEquals("13 Aug 2017", sdf.format(TradeDateUtil.getNextWorkDay(currency, settlementDate)));
    }

}