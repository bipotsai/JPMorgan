package com.jpmorgan.interview;

import com.jpmorgan.interview.entity.TradeEntity;
import com.jpmorgan.interview.enums.Currency;
import com.jpmorgan.interview.enums.Instruction;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bipo on 06/08/2017.
 *
 * test program
 */
public class Main {

    public ReportGenerator getReportGenerator(String testDataFile) {
        ReportGenerator rg = new ReportGenerator();
        try {
            File inFile = new File(testDataFile);

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));


            String line = null;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] input = line.split(" ");
                    //foo B 0.50 SGP 01 Jan 2016 02 Jan 2016 200 100.25
                    String title = input[0];
                    Instruction instruction = Instruction.getInstruction(input[1]);
                    BigDecimal agreedFx = new BigDecimal(input[2]);
                    Currency currency = Currency.getCurrency(input[3]);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                    Date instructionDate = sdf.parse(input[4] + " " + input[5] + " " + input[6]);
                    Date settlementDate = sdf.parse(input[7] + " " + input[8] + " " + input[9]);
                    int units = Integer.parseInt(input[10]);
                    BigDecimal price = new BigDecimal(input[11]);
                    TradeEntity trade = new TradeEntity(title, instruction, agreedFx, currency, instructionDate, settlementDate, units, price);
                    rg.addTrade(trade);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            reader.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rg;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.printf("*****            Test Case  1         ***** \n");
        ReportGenerator case1Report = main.getReportGenerator("res/testcase/case1.in");
        System.out.printf("***** Everyday Incoming Amount Report ***** \n");
        case1Report.generateEveryIncomingAmountReport();
        System.out.printf("***** Everyday Outgoing Amount Report ***** \n");
        case1Report.generateEveryOutgoingAmountReport();
        System.out.printf("***** Incoming Amount Ranking  Report ***** \n");
        case1Report.generateIncomingAmountRankingReport();
        System.out.printf("***** Outgoing Amount Ranking  Report ***** \n");
        case1Report.generateOutgoingAmountRankingReport();

        System.out.printf("*****            Test Case  2         ***** \n");
        ReportGenerator case2Report = main.getReportGenerator("res/testcase/case2.in");
        System.out.printf("***** Everyday Incoming Amount Report ***** \n");
        case2Report.generateEveryIncomingAmountReport();
        System.out.printf("***** Everyday Outgoing Amount Report ***** \n");
        case2Report.generateEveryOutgoingAmountReport();
        System.out.printf("***** Incoming Amount Ranking  Report ***** \n");
        case2Report.generateIncomingAmountRankingReport();
        System.out.printf("***** Outgoing Amount Ranking  Report ***** \n");
        case2Report.generateOutgoingAmountRankingReport();

        System.out.printf("*****            Test Case  3         ***** \n");
        ReportGenerator case3Report = main.getReportGenerator("res/testcase/case3.in");
        System.out.printf("***** Everyday Incoming Amount Report ***** \n");
        case3Report.generateEveryIncomingAmountReport();
        System.out.printf("***** Everyday Outgoing Amount Report ***** \n");
        case3Report.generateEveryOutgoingAmountReport();
        System.out.printf("***** Incoming Amount Ranking  Report ***** \n");
        case3Report.generateIncomingAmountRankingReport();
        System.out.printf("***** Outgoing Amount Ranking  Report ***** \n");
        case3Report.generateOutgoingAmountRankingReport();
    }
}
