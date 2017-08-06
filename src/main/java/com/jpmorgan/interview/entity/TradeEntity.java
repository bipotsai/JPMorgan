package com.jpmorgan.interview.entity;

import com.jpmorgan.interview.enums.Currency;
import com.jpmorgan.interview.enums.Instruction;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bipo on 06/08/2017.
 * Trade Entity
 */
public class TradeEntity {
    private final String title;
    private final Instruction instruction;
    private final BigDecimal agreedFx;
    private final Currency currency;
    private final Date instructionDate;
    private final Date settlementDate;
    private final int units;
    private final BigDecimal price;

    public TradeEntity(String title, Instruction instruction, BigDecimal agreedFx, Currency currency, Date instructionDate, Date settlementDate, int units, BigDecimal price) {
        this.title = title;
        this.instruction = instruction;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.units = units;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public BigDecimal getAgreedFx() {
        return agreedFx;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Date getInstructionDate() {
        return instructionDate;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public int getUnits() {
        return units;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount(){
        //Price per unit * Units * Agreed Fx
        return price.multiply(new BigDecimal(units)).multiply(agreedFx);
    }
}
