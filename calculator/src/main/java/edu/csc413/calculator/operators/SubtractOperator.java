package edu.csc413.calculator.operators;
import edu.csc413.calculator.evaluator.Operand;

public class SubtractOperator extends Operator{
    public int priority(){
        return 1; //Test case said it isequal to 1
    }
    public Operand execute(Operand operandOne, Operand operandTwo){
        Operand sum = new Operand(operandOne.getValue() - operandTwo.getValue());
        return sum;
    }
}
