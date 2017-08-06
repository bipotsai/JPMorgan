package com.jpmorgan.interview.enums;

import com.jpmorgan.interview.exception.InstructionNotExistException;
import org.junit.Test;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.junit.Assert.*;

/**
 * Created by bipo on 06/08/2017.
 */
public class InstructionTest {
    @Test
    public void getInstruction() throws Exception {
        String symbol = "B";
        assertEquals(Instruction.BUY, Instruction.getInstruction(symbol));
        symbol = "S";
        assertEquals(Instruction.SELL, Instruction.getInstruction(symbol));
    }

    @Test
    public void testInstructionNotExistExceptionMessage() {
        try {
            String symbol = "F";
            Instruction.getInstruction(symbol);
        } catch (InstructionNotExistException e) {
            assertEquals(e.getMessage(), "Invalid Instruction Symbol, only B or S is valid");
            //assert others
        }
    }

    @Test(expected = InstructionNotExistException.class)
    public void testInstructionNotExistException() throws InstructionNotExistException {
        String symbol = "F";
        Instruction.getInstruction(symbol);
    }

}