package com.jpmorgan.interview.exception;

/**
 * Created by bipo on 06/08/2017.
 *
 * throws this when instruction is neither B/S in TradeEntity
 */
public class InstructionNotExistException extends Exception{
    public InstructionNotExistException(String message) {
        super(message);
    }
}