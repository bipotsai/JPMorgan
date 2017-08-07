package com.jpmorgan.interview.exception;

/**
 * Created by bipo on 06/08/2017.
 *
 * throws this when specified Current has no valid work day
 */
public class InvalidWorkDayException extends Exception{
    public InvalidWorkDayException(String message) {
        super(message);
    }
}
