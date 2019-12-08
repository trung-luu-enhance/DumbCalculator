package com.example.dumbcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView exp_tv;
    private TextView res_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exp_tv = findViewById(R.id.exp_textview);
        res_tv = findViewById(R.id.res_textview);
    }

    public void onButtonClick(View view){
        String newText = "";

        if (!(exp_tv.getText().length() == 1 && exp_tv.getText().charAt(0) == '0'))
            newText = exp_tv.getText().toString().trim();

        switch (view.getId()) {
            case R.id.clear_button:
                exp_tv.setText("0");
                break;
            case R.id.back_button:
                CharSequence exp_text = exp_tv.getText();
                exp_tv.setText((exp_text.length() > 1)
                        ? exp_text.subSequence(0,exp_text.length() - 1)
                        : "0");
                break;
        }

        if (exp_tv.getText().length() > 33)
            return;

        switch (view.getId()) {
            case R.id.one_button:
                newText = newText + "1";
                exp_tv.setText(newText);
                break;
            case R.id.two_button:
                newText = newText + "2";
                exp_tv.setText(newText);
                break;
            case R.id.three_button:
                newText = newText + "3";
                exp_tv.setText(newText);
                break;
            case R.id.four_button:
                newText = newText + "4";
                exp_tv.setText(newText);
                break;
            case R.id.five_button:
                newText = newText + "5";
                exp_tv.setText(newText);
                break;
            case R.id.six_button:
                newText = newText + "6";
                exp_tv.setText(newText);
                break;
            case R.id.seven_button:
                newText = newText + "7";
                exp_tv.setText(newText);
                break;
            case R.id.eight_button:
                newText = newText + "8";
                exp_tv.setText(newText);
                break;
            case R.id.nine_button:
                newText = newText + "9";
                exp_tv.setText(newText);
                break;
            case R.id.zero_button:
                newText = newText + "0";
                exp_tv.setText(newText);
                break;
            case R.id.addition_button:
                newText = newText + "+";
                exp_tv.setText(newText);
                break;
            case R.id.subtraction_button:
                newText = newText + "-";
                exp_tv.setText(newText);
                break;
            case R.id.multiply_button:
                newText = newText + "*";
                exp_tv.setText(newText);
                break;
            case R.id.division_button:
                newText = newText + "/";
                exp_tv.setText(newText);
                break;
            case R.id.remainder_button:
                newText = newText + "%";
                exp_tv.setText(newText);
                break;
            default:
                return;
        }
    }

    public void onEqualClick(View view) {
        String expression = exp_tv.getText().toString();

        if (expression.charAt(expression.length()-1) == '+'
            ||expression.charAt(expression.length()-1) == '-'){
            expression += "0";
        }

        if (expression.charAt(expression.length()-1) == '*'
            ||expression.charAt(expression.length()-1) == '/'
            ||expression.charAt(expression.length()-1) == '%'){
            expression += "1";
        }

        int result = evaluate(expression);
        res_tv.setText(Integer.toString(result));
        exp_tv.setText("0");
    }

    private static int evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<Integer>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {
            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
                --i;
            }
            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/' || tokens[i]=='%')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2)
    {
        if ((op1 == '*' || op1 == '/' || op1 == '%') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static int applyOp(char op, int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            case '%':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a % b;
        }
        return 0;
    }
}
