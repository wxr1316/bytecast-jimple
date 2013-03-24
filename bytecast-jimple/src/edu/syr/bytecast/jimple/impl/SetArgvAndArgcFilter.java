/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.jimpleBean.ParsedInstructionsSet;
import edu.syr.bytecast.jimple.beans.jimpleBean.JInstructionInfo;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nick
 */
//this file is used to filter the pre-memory process part

public class SetArgvAndArgcFilter implements IFilter{
    public boolean doTest(Map<Long, IInstruction> instList, int index)
    {
        IInstruction ins = instList.get(index);
        int count = 0;
        if( ins.getInstructiontype() == InstructionType.MOV
                && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(0).getOperandValue() == "%edi"
                //&& ins.getOperands().get(1).getOperandType() == OperandType.MEMORY_ADDRESS
                && ins.getOperands().get(1).getOperandValue().toString().contains("%rbp") )
        {
            count++;
            ins = instList.get(index + count);
            if( ins.getInstructiontype() == InstructionType.MOV
                    && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(0).getOperandValue() == "%rsi"
                    //&& ins.getOperands().get(1).getOperandType() == OperandType.MEMORY_ADDRESS
                    && ins.getOperands().get(1).getOperandValue().toString().contains("%rbp")) //SectionName
            {
                count++;
                return true;
                }
            }
        return false;
    } 
}



    
    