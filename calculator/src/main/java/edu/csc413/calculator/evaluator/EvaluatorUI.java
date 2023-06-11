package edu.csc413.calculator.evaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.csc413.calculator.operators.Operator;
public class EvaluatorUI extends JFrame implements ActionListener {

    private JTextField expressionTextField = new JTextField();
    private JPanel buttonPanel = new JPanel();

    // total of 20 buttons on the calculator,
    // numbered from left to right, top to bottom
    // bText[] array contains the text for corresponding buttons
    private static final String[] buttonText = {
        "7", "8", "9", "+",
        "4", "5", "6", "-", 
        "1", "2", "3", "*", 
        "(", "0", ")", "/", 
        "C", "CE", "=", "^"
    };

    /**
     * C  is for clear, clears entire expression
     * CE is for clear expression, clears last entry up until the last operator.
     */
    private JButton[] buttons = new JButton[buttonText.length];

    public static void main(String argv[]) {
        new EvaluatorUI();
    }

    public EvaluatorUI() {
        setLayout(new BorderLayout());
        this.expressionTextField.setPreferredSize(new Dimension(600, 50));
        this.expressionTextField.setFont(new Font("Courier", Font.BOLD, 24));
        this.expressionTextField.setHorizontalAlignment(JTextField.CENTER);

        add(expressionTextField, BorderLayout.NORTH);
        expressionTextField.setEditable(false);

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(5, 4));

        //create 20 buttons with corresponding text in bText[] array
        JButton tempButtonReference;
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            tempButtonReference = new JButton(buttonText[i]);
            tempButtonReference.setFont(new Font("Courier", Font.BOLD, 24));
            buttons[i] = tempButtonReference;
        }

        //add buttons to button panel
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        //set up buttons to listen for mouse input
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("Calculator");
        setSize(400, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This function is triggered anytime a button is pressed
     * on our Calculator GUI.
     * @param actionEventObject Event object generated when a
     *                    button is pressed.
     */
    public void actionPerformed(ActionEvent actionEventObject) {

        String temp;

        if ((!actionEventObject.getActionCommand().equals("=")) && (!actionEventObject.getActionCommand().equals("C")) && (!actionEventObject.getActionCommand().equals("CE"))) { //Only numbers and mathematical operators
            this.expressionTextField.setText(this.expressionTextField.getText() + actionEventObject.getActionCommand()); //We display the user's input on the calculator screen by appending to the previous screen value
        } else if (actionEventObject.getActionCommand().equals("=")) {  //if user puts in an equal sign
            Evaluator eval = new Evaluator();
            temp = this.expressionTextField.getText();  //temporarily storing the previous field value for evaluation
//            try {    //We use a try catch to account for the possibility that the user may enter an invalid expression which would prompt the Evaluator to throw an exception
//                int result = eval.evaluateExpression(temp);
//                this.expressionTextField.setText(this.expressionTextField.getText() + "=" + result); //Appending an equal sign and the result of the evaluation(for valid expressions)
//            } catch (Exception x) {     //Catching exception
//                this.expressionTextField.setText("Invalid!!!(Press C to clear)");
//            }

        }
        if (actionEventObject.getActionCommand().equals("C")) {
            this.expressionTextField.setText("");       //removes entries "C" on calculator function
        }
        if (actionEventObject.getActionCommand().equals("CE")) {
            String last_entry = this.expressionTextField.getText();   //stores last_entry temporarily
            int last_operator_index = 0;                //this variable will hold the index of the last operator
            for (int i = last_entry.length() - 1; i >= 0; i--) { //We start at the end because that is much more efficient to find the last(right-most) operator
                String str = String.valueOf(last_entry.charAt(i));    //str holds a String of the value of the character at a certain position
                if (Operator.check(str)) {    //if operator, we update index and break, because we want the last index and we are going left to right
                    last_operator_index = i;
                    break;
                }

            }
            //Now we copy from the last entry string to the new string up to(not including) last operator or last operand
            char old_arr[] = last_entry.toCharArray();
            char[] arr = new char[old_arr.length];
            for (int i = 0; i < last_operator_index; i++) {
                arr[i] = old_arr[i];
            }
            String new_txt = new String(arr);
            expressionTextField.setText(new_txt);
        }
    }
}
