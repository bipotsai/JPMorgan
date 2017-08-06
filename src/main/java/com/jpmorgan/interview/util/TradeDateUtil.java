package com.jpmorgan.interview.util;

import com.jpmorgan.interview.enums.Currency;
import com.jpmorgan.interview.exception.InvalidWorkDayException;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bipo on 06/08/2017.
 */
public class TradeDateUtil {

    static Calendar calendar = Calendar.getInstance(Locale.US);

    /**
     * check the settle date is valid week day by the it's currency
     * @param currency
     * @param inputDate
     * @return
     */
    public static boolean isWorkDay(Currency currency, Date inputDate) {
        calendar.setTime(inputDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return currency.getValidWorkDays().contains(dayOfWeek);
    }

    /**
     * get adjacency work day
     * @param currency
     * @param inputDate
     * @return
     * @throws InvalidWorkDayException if a currency has no valid work day, throw excpetion
     */
    public static Date getNextWorkDay(Currency currency, Date inputDate) throws InvalidWorkDayException{
        calendar.setTime(inputDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        for(int diff=1;diff<=Calendar.DAY_OF_WEEK;diff++){
            int nextDay = (dayOfWeek + diff) % Calendar.DAY_OF_WEEK;
            if(currency.getValidWorkDays().contains(nextDay)){
                calendar.add(Calendar.DATE, diff);
                return calendar.getTime();
            }
        }
        throw new InvalidWorkDayException("This Currency:"+ currency.name()+" has no valid work day");
    }
}
