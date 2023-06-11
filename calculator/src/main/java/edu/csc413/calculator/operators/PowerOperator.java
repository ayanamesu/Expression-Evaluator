package edu.csc413.calculator.operators;
import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator {
    @Override
    public int priority() {
        return 3;
    }
    @Override
    public Operand execute(Operand operandOne, Operand operandTwo){
        Operand sum = new Operand (power(operandOne.getValue(),operandTwo.getValue()));
        return sum;
    }
    public int power(int i, int j) {
        int sum = i;
        for (int count = 2; count <= j; count++) {
            sum = sum * i;
        }
        return sum;
    }
}