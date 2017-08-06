package com.jpmorgan.interview.enums;

import com.jpmorgan.interview.exception.InstructionNotExistException;

/**
 * Created by bipo on 06/08/2017.
 */
public enum Instruction {

    BUY("B"),
    SELL("S");

    private final String symbol;

    Instruction(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static Instruction getInstruction(String symbol) throws InstructionNotExistException{

        if (BUY.toString().equals(symbol)) {
            return BUY;
        } else if (SELL.toString().equals(symbol)) {
            return SELL;
        } else throw new InstructionNotExistException("Invalid Instruction Symbol, only B or S is valid");
    }
}
