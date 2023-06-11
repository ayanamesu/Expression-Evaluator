package edu.csc413.calculator.evaluator;
import edu.csc413.calculator.operators.*;
import java.util.Stack;
import java.util.StringTokenizer;
public class Evaluator {

  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer expressionTokenizer;
  private final String delimiters = " +/*-^()";

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }


  //code that could be removed
  private void process() {
    Operator operatorFromStack = operatorStack.pop();
    Operand operandTwo = operandStack.pop();
    Operand operandOne = operandStack.pop();
    Operand result = operatorFromStack.execute(operandOne, operandTwo);
    operandStack.push(result);

  }
  private void processOperatorStack() {
    while (!operatorStack.isEmpty() && operatorStack.peek().priority() > 0) {
      process();
    }
  }



  public int evaluateExpression(String expression) throws InvalidTokenException {
    operatorStack.push(new LeftParenthesisOperator());
    String expressionToken;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.expressionTokenizer = new StringTokenizer(expression, this.delimiters, true);


    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators

    while (this.expressionTokenizer.hasMoreTokens()) { // filter out spaces
      if (!(expressionToken = this.expressionTokenizer.nextToken()).equals(" ")) { // check if token is an operand
        if (Operand.check(expressionToken)) {
          operandStack.push(new Operand(expressionToken));
        } else {
          if (!Operator.check(expressionToken)) {
            throw new InvalidTokenException(expressionToken);
          }


          // TODO Operator is abstract - these two lines will need to be fixed:
          // The Operator class should contain an instance of a HashMap,
          // and values will be instances of the Operators.  See Operator class
          // skeleton for an example.
//          if (Operand.check(expressionToken)) {
//            operandStack.push(new Operand(expressionToken));
//          } else if (Operator.check(expressionToken)) {
//          Operator newOperator = Operator.getOperator(expressionToken); //getting the operator object
          if (expressionToken.equals("(")) {
            operatorStack.push(new LeftParenthesisOperator());

          } else if (expressionToken.equals(")")) {
            processOperatorStack();

            operatorStack.pop(); // Discard the left parenthesis
          } else {
            Operator newOperator = Operator.getOperator(expressionToken);

            while (!operatorStack.isEmpty() && operatorStack.peek().priority() >= newOperator.priority()) {
              process();

              // note that when we eval the expression 1 - 2 we will
              // push the 1 then the 2 and then do the subtraction operation
              // This means that the first number to be popped is the
              // second operand, not the first operand - see the following code
            }

            operatorStack.push(newOperator);
          }
        }
        }
      }
    while (!operatorStack.isEmpty() && !(operatorStack.peek() instanceof LeftParenthesisOperator)) {
      process();


    }

      // Control gets here when we've picked up all of the tokens; you must add
      // code to complete the evaluation - consider how the code given here
      // will evaluate the expression 1+2*3
      // When we have no more tokens to scan, the operand stack will contain 1 2
      // and the operator stack will have + * with 2 and * on the top;
      // In order to complete the evaluation we must empty the stacks,
      // that is, we should keep evaluating the operator stack until it is empty;
      // Suggestion: create a method that processes the operator stack until empty.

    //THIS IS TO CHECK IF THE EXPRESSION HAS FILLED IN PARENTHESIS OR if there are other messy Parenthesis
    char[] c = expression.toCharArray();
    int counter = 0;

    for (int i = 0; i < c.length ; i++){
      if (c[i] == '(')
        counter++;
      if (c[i] == ')')
        counter--;
    }

    System.out.println(counter);
    if (counter != 0)
      throw new InvalidTokenException(expression);
      return operandStack.pop().getValue();
    }

  }











