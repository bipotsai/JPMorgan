package com.jpmorgan.interview.enums;

import com.jpmorgan.interview.exception.InstructionNotExistException;

/**
 * Created by bipo on 06/08/2017.
 * the instruction of trade either B/S.
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

    /**
     * get enum:Instruction by string type symbol
     * @param symbol
     * @return
     * @throws InstructionNotExistException when instruction is neither B/S
     */
    public static Instruction getInstruction(String symbol) throws InstructionNotExistException{

        if (BUY.toString().equals(symbol)) {
            return BUY;
        } else if (SELL.toString().equals(symbol)) {
            return SELL;
        } else throw new InstructionNotExistException("Invalid Instruction Symbol, only B or S is valid");
    }
}
