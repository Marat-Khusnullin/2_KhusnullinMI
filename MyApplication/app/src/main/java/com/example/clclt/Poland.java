package com.example.clclt;

import java.util.LinkedList;

/**
 * Created by Марат on 20.09.2016.
 */
public class Poland {

    static boolean isNothing(char c) {
        return c == ' ';
    }
    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == 'x' || c == '/' || c == '%';
    }
    static int priority(char elmnt) {
        switch (elmnt) {
            case '+':
            case '-':
                return 1;
            case 'x':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }
    }
    static void processOperator(LinkedList<Double> st, char op) {
        double r = st.removeLast();
        double l = st.removeLast();
        switch (op) {
            case '+':
                st.add(l + r);
                break;
            case '-':
                st.add(l - r);
                break;
            case 'x':
                st.add(l * r);
                break;
            case '/':
                st.add(l / r);
                break;
            case '%':
                st.add(l % r);
                break;
        }
    }
    public double eval(String s) {
        LinkedList<Double> st = new LinkedList<Double>();
        LinkedList<Character> sign = new LinkedList<Character>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isNothing(c))
                continue;
            if (c == '(')
                sign.add('(');
            else if (c == ')') {
                while (sign.getLast() != '(')
                    processOperator(st,sign.removeLast());
                sign.removeLast();
            } else if (isOperator(c)) {
                while (!sign.isEmpty() && priority(sign.getLast()) >= priority(c))
                    processOperator(st, sign.removeLast());
                sign.add(c);
            } else {
                String operand = "";
                while (i < s.length() && (Character.isDigit(s.charAt(i))||s.charAt(i) == '.'))
                    operand += s.charAt(i++);
                --i;
                st.add(Double.parseDouble(operand));
            }
        }
        while (!sign.isEmpty())
            processOperator(st, sign.removeLast());
        return st.get(0);
    }
}
