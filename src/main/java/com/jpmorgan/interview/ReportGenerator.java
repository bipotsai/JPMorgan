package com.jpmorgan.interview;

import com.jpmorgan.interview.entity.TradeEntity;
import com.jpmorgan.interview.exception.InvalidWorkDayException;
import com.jpmorgan.interview.util.TradeDateUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bipo on 06/08/2017.
 *
 * Generate required report from input trade entity.
 *
 */
public class ReportGenerator {

    static Calendar calendar = Calendar.getInstance(Locale.US);

    //Daily incoming Amount Map
    HashMap<Date, BigDecimal> incomingDateAmountMap = new HashMap<>();
    //Daily outgoing Amount Map
    HashMap<Date, BigDecimal> outgoingDateAmountMap = new HashMap<>();

    //Entity incoming Amount Map
    HashMap<String, BigDecimal> incomingTitleAmountMap = new HashMap<>();
    //Entity outgoing Amount Map
    HashMap<String, BigDecimal> outgoingTitleAmountMap = new HashMap<>();

    //memorize incoming trade start/end date for daily report
    long startIncomingSettledTimeStamp = Long.MAX_VALUE;
    long endIncomingSettledTimeStamp = Long.MIN_VALUE;

    //memorize outgoing trade start/end date for daily report
    long startOutgoingSettledTimeStamp = Long.MAX_VALUE;
    long endOutgoingSettledTimeStamp = Long.MIN_VALUE;

    //cached data: if developer invokes twice generate ranking report, won't sort again
    ArrayList<Map.Entry<String, BigDecimal>> incomingRankingList = new ArrayList<>();
    ArrayList<Map.Entry<String, BigDecimal>> outgoingRankingList = new ArrayList<>();

    public ReportGenerator() {

    }

    /**
     * input TradeEntity from input stream
     * @param trade
     * @return true:valid trade false:invalid trade
     */
    public boolean addTrade(TradeEntity trade) {
        boolean result = true;
        String title = trade.getTitle();
        Date settlementDate = trade.getSettlementDate();
        BigDecimal amount = trade.getAmount();
        //if settlement date is not a work day, change it to the next valid work day
        if (!TradeDateUtil.isWorkDay(trade.getCurrency(), trade.getSettlementDate())) {
            try {
                settlementDate = TradeDateUtil.getNextWorkDay(trade.getCurrency(), trade.getSettlementDate());
            } catch (InvalidWorkDayException ex) {
                //no valid work day exist, ignore this trade
                System.out.println(ex.getMessage());
                return false;
            }
        }

        //separate incoming and outgoing TradeEntity
        switch (trade.getInstruction()) {
            case BUY:
                outgoingDateAmountMap.put(settlementDate, outgoingDateAmountMap.getOrDefault(settlementDate, new BigDecimal(0.0)).add(amount));
                outgoingTitleAmountMap.put(title, outgoingTitleAmountMap.getOrDefault(title, new BigDecimal(0.0)).add(amount));

                startOutgoingSettledTimeStamp = Math.min(startOutgoingSettledTimeStamp, settlementDate.getTime());
                endOutgoingSettledTimeStamp = Math.max(endOutgoingSettledTimeStamp, settlementDate.getTime());
                break;
            case SELL:
                incomingDateAmountMap.put(settlementDate, incomingDateAmountMap.getOrDefault(settlementDate, new BigDecimal(0.0)).add(amount));
                incomingTitleAmountMap.put(title, incomingTitleAmountMap.getOrDefault(title, new BigDecimal(0.0)).add(amount));

                startIncomingSettledTimeStamp = Math.min(startIncomingSettledTimeStamp, settlementDate.getTime());
                endIncomingSettledTimeStamp = Math.max(endIncomingSettledTimeStamp, settlementDate.getTime());
                break;
        }
        return result;
    }

    /**
     * generate daily incoming amount report to console
     */
    public void generateEveryIncomingAmountReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        calendar.setTimeInMillis(startIncomingSettledTimeStamp);
        while (calendar.getTime().getTime() <= endIncomingSettledTimeStamp) {
            System.out.printf("%s : %f \n", sdf.format(calendar.getTime()), incomingDateAmountMap.getOrDefault(calendar.getTime(), new BigDecimal(0.0)));
            calendar.add(Calendar.DATE, 1);
        }
    }

    /**
     * generate daily outgoing amount report to console
     */
    public void generateEveryOutgoingAmountReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        calendar.setTimeInMillis(startOutgoingSettledTimeStamp);
        while (calendar.getTime().getTime() <= endOutgoingSettledTimeStamp) {
            System.out.printf("%s : %f \n", sdf.format(calendar.getTime()), outgoingDateAmountMap.getOrDefault(calendar.getTime(), new BigDecimal(0.0)));
            calendar.add(Calendar.DATE, 1);
        }
    }

    /**
     * generate incoming amount ranking report to console
     */
    public void generateIncomingAmountRankingReport() {
        //sort at first time
        if (incomingRankingList.isEmpty()) {
            for (Map.Entry<String, BigDecimal> pair : incomingTitleAmountMap.entrySet()) {
                incomingRankingList.add(pair);
            }
            Collections.sort(incomingRankingList, new Comparator<Map.Entry<String, BigDecimal>>()
            {
                public int compare( Map.Entry<String, BigDecimal> o1, Map.Entry<String, BigDecimal> o2 )
                {
                    return (o2.getValue()).compareTo( o1.getValue() );
                }
            } );
        }
        for(Map.Entry<String, BigDecimal> pair : incomingRankingList){
            System.out.printf("%s : %f \n",pair.getKey(), pair.getValue());
        }
    }

    /**
     * generate outgoing amount ranking report to console
     */
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
