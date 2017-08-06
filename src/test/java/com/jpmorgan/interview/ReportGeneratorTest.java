package com.jpmorgan.interview;

import com.jpmorgan.interview.entity.TradeEntity;
import com.jpmorgan.interview.enums.Currency;
import com.jpmorgan.interview.enums.Instruction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by bipo on 06/08/2017.
 */
public class ReportGeneratorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }


    @Test
    public void addTrade() throws Exception {
        //foo B 0.50 SGP 01 Jan 2016 02 Jan 2016 200 100.25
        String title = "foo";
        Instruction instruction = Instruction.getInstruction("B");
        BigDecimal agreedFx = new BigDecimal(0.50);
        Currency currency = Currency.getCurrency("SGP");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date instructionDate = sdf.parse("01 Aug 2017");
        Date settlementDate = sdf.parse("02 Aug 2017");
        int units = 200;
        BigDecimal price = new BigDecimal(100.25);
        TradeEntity trade = new TradeEntity(title, instruction, agreedFx, currency, instructionDate, settlementDate, units, price);

        ReportGenerator rg = new ReportGenerator();
        boolean result  = rg.addTrade(trade);
        assertTrue(result);
    }

    @Test
    public void generateEveryIncomingAmountReport() throws Exception {
        //bar S 0.22 AED 09 Aug 2017 07 Aug 2017 450 150.5
        String title = "bar";
        Instruction instruction = Instruction.getInstruction("S");
        BigDecimal agreedFx = new BigDecimal(0.22);
        Currency currency = Currency.getCurrency("AED");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date instructionDate = sdf.parse("07 Aug 2017");
        Date settlementDate = sdf.parse("09 Aug 2017");
        int units = 450;
        BigDecimal price = new BigDecimal(150.5);
        TradeEntity trade = new TradeEntity(title, instruction, agreedFx, currency, instructionDate, settlementDate, units, price);

        ReportGenerator rg = new ReportGenerator();
        rg.addTrade(trade);
        rg.generateEveryIncomingAmountReport();

        assertEquals("09 Aug 2017 : 14899.500000 \n", outContent.toString());
    }

    @Test
    public void generateEveryOutgoingAmountReport() throws Exception {
        //foo B 0.50 SGP 01 Jan 2016 02 Jan 2016 200 100.25
        String title = "foo";
        Instruction instruction = Instruction.getInstruction("B");
        BigDecimal agreedFx = new BigDecimal(0.50);
        Currency currency = Currency.getCurrency("SGP");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date instructionDate = sdf.parse("01 Aug 2017");
        Date settlementDate = sdf.parse("02 Aug 2017");
        int units = 200;
        BigDecimal price = new BigDecimal(100.25);
        TradeEntity trade = new TradeEntity(title, instruction, agreedFx, currency, instructionDate, settlementDate, units, price);

        ReportGenerator rg = new ReportGenerator();
        rg.addTrade(trade);
        rg.generateEveryOutgoingAmountReport();

        assertEquals("02 Aug 2017 : 10025.000000 \n", outContent.toString());
    }

    @Test
    public void generateOutgoingAmountRankingReport() throws Exception {
        ReportGenerator rg = new ReportGenerator();

        //test case
        //foo B 0.50 SGP 01 Aug 2017 02 Aug 2017 200 100.25
        //bar B 0.22 AED 09 Aug 2017 09 Aug 2017 450 150.5
        //A00 B 0.22 SAR 09 Aug 2017 08 Aug 2017 450 150.5
        //bar B 0.22 AED 09 Aug 2017 09 Aug 2017 450 150.5
        //A00 B 0.22 SAR 09 Aug 2017 10 Aug 2017 450 150.5
        //bar B 0.22 AED 09 Aug 2017 11 Aug 2017 450 150.5
        //A01 B 0.22 SAR 09 Aug 2017 12 Aug 2017 450 150.5
        //foo B 0.50 SGP 10 Aug 2017 13 Aug 2017 200 100.25

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        TradeEntity trade1 = new TradeEntity("foo", Instruction.getInstruction("B"), new BigDecimal(0.50)
                , Currency.getCurrency("SGP"), sdf.parse("01 Aug 2017"), sdf.parse("02 Aug 2017")
                , 200, new BigDecimal(100.25));
        TradeEntity trade2 = new TradeEntity("bar", Instruction.getInstruction("B"), new BigDecimal(0.22)
                , Currency.getCurrency("AED"), sdf.parse("09 Aug 2017"), sdf.parse("09 Aug 2017")
                , 450, new BigDecimal(150.5));
        TradeEntity trade3 = new TradeEntity("A00", Instruction.getInstruction("B"), new BigDecimal(0.22)
                , Currency.getCurrency("SAR"), sdf.parse("08 Aug 2017"), sdf.parse("08 Aug 2017")
                , 450, new BigDecimal(150.5));
        TradeEntity trade4 = new TradeEntity("bar", Instruction.getInstruction("B"), new BigDecimal(0.22)
                , Currency.getCurrency("SAR"), sdf.parse("09 Aug 2017"), sdf.parse("09 Aug 2017")
                , 450, new BigDecimal(150.5));
        TradeEntity trade5 = new TradeEntity("A00", Instruction.getInstruction("B"), new BigDecimal(0.22)
                , Currency.getCurrency("SAR"), sdf.parse("09 Aug 2017"), sdf.parse("10 Aug 2017")
                , 450, new BigDecimal(150.5));
        TradeEntity trade6 = new TradeEntity("bar", Instruction.getInstruction("B"), new BigDecimal(0.22)
                , Currency.getCurrency("AED"), sdf.parse("09 Aug 2017"), sdf.parse("11 Aug 2017")
                , 450, new BigDecimal(150.5));
        TradeEntity trade7 = new TradeEntity("A01", Instruction.getInstruction("B"), new BigDecimal(0.22)
                , Currency.getCurrency("SAR"), sdf.parse("09 Aug 2017"), sdf.parse("12 Aug 2017")
                , 450, new BigDecimal(150.5));
        TradeEntity trade8 = new TradeEntity("foo", Instruction.getInstruction("B"), new BigDecimal(0.50)
                , Currency.getCurrency("SGP"), sdf.parse("10 Aug 2017"), sdf.parse("13 Aug 2017")
                , 200, new BigDecimal(100.25));

        rg.addTrade(trade1);
        rg.addTrade(trade2);
        rg.addTrade(trade3);
        rg.addTrade(trade4);
        rg.addTrade(trade5);
        rg.addTrade(trade6);
        rg.addTrade(trade7);
        rg.addTrade(trade8);
        rg.generateOutgoingAmountRankingReport();
        assertEquals("bar : 44698.500000 \nA00 : 29799.000000 \nfoo : 20050.000000 \nA01 : 14899.500000 \n", outContent.toString());
    }

    @Test
    public void generateIncomingAmountRankingReport() throws Exception {
        ReportGenerator rg = new ReportGenerator();

        //test case
        //foo S 0.50 SGP 01 Aug 2017 02 Aug 2017 200 100.25
        //bar S 0.22 AED 09 Aug 2017 07 Aug 2017 100 150.5
        //A03 S 0.12 SAR 09 Aug 2017 08 Aug 2017 100 150.5
        //bar S 0.22 AED 09 Aug 2017 09 Aug 2017 100 150.5
        //A03 S 0.10 SAR 09 Aug 2017 10 Aug 2017 500 150.5
        //bar S 0.22 AED 09 Aug 2017 11 Aug 2017 200 150.5
        //A04 S 0.22 SAR 09 Aug 2017 12 Aug 2017 200 150.5
        //foo S 1.00 USD 10 Aug 2017 13 Aug 2017 100 100.25

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        TradeEntity trade1 = new TradeEntity("foo", Instruction.getInstruction("S"), new BigDecimal(0.50)
                , Currency.getCurrency("SGP"), sdf.parse("01 Aug 2017"), sdf.parse("02 Aug 2017")
                , 200, new BigDecimal(100.25));
        TradeEntity trade2 = new TradeEntity("bar", Instruction.getInstruction("S"), new BigDecimal(0.22)
                , Currency.getCurrency("AED"), sdf.parse("09 Aug 2017"), sdf.parse("09 Aug 2017")
                , 100, new BigDecimal(150.5));
        TradeEntity trade3 = new TradeEntity("A03", Instruction.getInstruction("S"), new BigDecimal(0.12)
                , Currency.getCurrency("SAR"), sdf.parse("08 Aug 2017"), sdf.parse("08 Aug 2017")
                , 100, new BigDecimal(150.5));
        TradeEntity trade4 = new TradeEntity("bar", Instruction.getInstruction("S"), new BigDecimal(0.22)
                , Currency.getCurrency("AED"), sdf.parse("09 Aug 2017"), sdf.parse("09 Aug 2017")
                , 100, new BigDecimal(150.5));
        TradeEntity trade5 = new TradeEntity("A03", Instruction.getInstruction("S"), new BigDecimal(0.10)
                , Currency.getCurrency("SAR"), sdf.parse("09 Aug 2017"), sdf.parse("10 Aug 2017")
                , 500, new BigDecimal(150.5));
        TradeEntity trade6 = new TradeEntity("bar", Instruction.getInstruction("S"), new BigDecimal(0.22)
                , Currency.getCurrency("AED"), sdf.parse("09 Aug 2017"), sdf.parse("11 Aug 2017")
                , 200, new BigDecimal(150.5));
        TradeEntity trade7 = new TradeEntity("A04", Instruction.getInstruction("S"), new BigDecimal(0.22)
                , Currency.getCurrency("SAR"), sdf.parse("09 Aug 2017"), sdf.parse("12 Aug 2017")
                , 200, new BigDecimal(150.5));
        TradeEntity trade8 = new TradeEntity("foo", Instruction.getInstruction("S"), new BigDecimal(1.00)
                , Currency.getCurrency("USD"), sdf.parse("10 Aug 2017"), sdf.parse("13 Aug 2017")
                , 100, new BigDecimal(100.25));

        rg.addTrade(trade1);
        rg.addTrade(trade2);
        rg.addTrade(trade3);
        rg.addTrade(trade4);
        rg.addTrade(trade5);
        rg.addTrade(trade6);
        rg.addTrade(trade7);
        rg.addTrade(trade8);
        rg.generateIncomingAmountRankingReport();
        assertEquals("foo : 20050.000000 \nbar : 13244.000000 \nA03 : 9331.000000 \nA04 : 6622.000000 \n", outContent.toString());
    }

}