package com.jpmorgan.interview;

import com.jpmorgan.interview.entity.TradeEntity;
import com.jpmorgan.interview.exception.InvalidWorkDayException;
import com.jpmorgan.interview.util.TradeDateUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bipo on 06/08/2017.
 */
public class ReportGenerator {

    static Calendar calendar = Calendar.getInstance(Locale.US);

    //Daily incoming Amount Map
    HashMap<Date, BigDecimal> incommingDateAmountMap = new HashMap<>();
    //Daily outgoing Amount Map
    HashMap<Date, BigDecimal> outgoingDateAmountMap = new HashMap<>();

    //Entity incoming Amount Map
    HashMap<String, BigDecimal> incommingTitleAmountMap = new HashMap<>();
    //Entity outgoing Amount Map
    HashMap<String, BigDecimal> outgoingTitleAmountMap = new HashMap<>();

    //memorize incoming trade start/end date for daily report
    long startIncommingSettledTimeStamp = Long.MAX_VALUE;
    long endIncommingSettledTimeStamp = Long.MIN_VALUE;

    //memorize outgoing trade start/end date for daily report
    long startOutgoingSettledTimeStamp = Long.MAX_VALUE;
    long endOutgoingSettledTimeStamp = Long.MIN_VALUE;

    //cached data
    ArrayList<Map.Entry<String, BigDecimal>> incommingRankingList = new ArrayList<>();
    ArrayList<Map.Entry<String, BigDecimal>> outgoingRankingList = new ArrayList<>();

    public ReportGenerator() {

    }

    public boolean addTrade(TradeEntity trade) {
        boolean result = true;
        String title = trade.getTitle();
        Date settlementDate = trade.getSettlementDate();
        BigDecimal amount = trade.getAmount();
        //if settlement date is not work day, change it to next valid work day
        if (!TradeDateUtil.isWorkDay(trade.getCurrency(), trade.getSettlementDate())) {
            try {
                settlementDate = TradeDateUtil.getNextWorkDay(trade.getCurrency(), trade.getSettlementDate());
            } catch (InvalidWorkDayException ex) {
                //no valid work day exist, ignore this trade
                System.out.println(ex.getMessage());
                return false;
            }
        }

        switch (trade.getInstruction()) {
            case BUY:
                outgoingDateAmountMap.put(settlementDate, outgoingDateAmountMap.getOrDefault(settlementDate, new BigDecimal(0.0)).add(amount));
                outgoingTitleAmountMap.put(title, outgoingTitleAmountMap.getOrDefault(title, new BigDecimal(0.0)).add(amount));

                startOutgoingSettledTimeStamp = Math.min(startOutgoingSettledTimeStamp, settlementDate.getTime());
                endOutgoingSettledTimeStamp = Math.max(endOutgoingSettledTimeStamp, settlementDate.getTime());
                break;
            case SELL:
                incommingDateAmountMap.put(settlementDate, incommingDateAmountMap.getOrDefault(settlementDate, new BigDecimal(0.0)).add(amount));
                incommingTitleAmountMap.put(title, incommingTitleAmountMap.getOrDefault(title, new BigDecimal(0.0)).add(amount));

                startIncommingSettledTimeStamp = Math.min(startIncommingSettledTimeStamp, settlementDate.getTime());
                endIncommingSettledTimeStamp = Math.max(endIncommingSettledTimeStamp, settlementDate.getTime());
                break;
        }
        return result;
    }

    public void generateEveryIncomingAmountReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        calendar.setTimeInMillis(startIncommingSettledTimeStamp);
        while (calendar.getTime().getTime() <= endIncommingSettledTimeStamp) {
            System.out.printf("%s : %f \n", sdf.format(calendar.getTime()), incommingDateAmountMap.getOrDefault(calendar.getTime(), new BigDecimal(0.0)));
            calendar.add(Calendar.DATE, 1);
        }
    }

    public void generateEveryOutgoingAmountReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        calendar.setTimeInMillis(startOutgoingSettledTimeStamp);
        while (calendar.getTime().getTime() <= endOutgoingSettledTimeStamp) {
            System.out.printf("%s : %f \n", sdf.format(calendar.getTime()), outgoingDateAmountMap.getOrDefault(calendar.getTime(), new BigDecimal(0.0)));
            calendar.add(Calendar.DATE, 1);
        }
    }

    public void generateIncomingAmountRankingReport() {
        //sort at first time
        if (incommingRankingList.isEmpty()) {
            for (Map.Entry<String, BigDecimal> pair : incommingTitleAmountMap.entrySet()) {
                incommingRankingList.add(pair);
            }
            Collections.sort( incommingRankingList, new Comparator<Map.Entry<String, BigDecimal>>()
            {
                public int compare( Map.Entry<String, BigDecimal> o1, Map.Entry<String, BigDecimal> o2 )
                {
                    return (o2.getValue()).compareTo( o1.getValue() );
                }
            } );
        }
        for(Map.Entry<String, BigDecimal> pair : incommingRankingList){
            System.out.printf("%s : %f \n",pair.getKey(), pair.getValue());
        }
    }

    public void generateOutgoingAmountRankingReport() {
        //sort at first time
        if (outgoingRankingList.isEmpty()) {
            for (Map.Entry<String, BigDecimal> pair : outgoingTitleAmountMap.entrySet()) {
                outgoingRankingList.add(pair);
            }
            Collections.sort( outgoingRankingList, new Comparator<Map.Entry<String, BigDecimal>>()
            {
                public int compare( Map.Entry<String, BigDecimal> o1, Map.Entry<String, BigDecimal> o2 )
                {
                    return (o2.getValue()).compareTo( o1.getValue() );
                }
            } );
        }
        for(Map.Entry<String, BigDecimal> pair : outgoingRankingList){
            System.out.printf("%s : %f \n",pair.getKey(), pair.getValue());
        }
    }

}
