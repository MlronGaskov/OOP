package ru.nsu.gaskov;

import java.util.HashMap;

public class Number extends Expression {
    public static boolean isNumber(String expressionString) {
        expressionString = Expression.removeOuterBrackets(expressionString);
        try {
            Double.parseDouble(expressionString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private final double number;

    public Number(double number) {
        this.number = number;
    }

    public Number(String expressionString) {
        expressionString = Expression.removeOuterBrackets(expressionString);
        if (isNumber(expressionString)) {
            this.number = Double.parseDouble(expressionString);
        } else {
            throw new IllegalArgumentException("Invalid number format.");
        }
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        return number;
    }

    @Override
    public Expression simplify() {
        return new Number(this.toString());
    }

    @Override
    public String toString() {
        if ((int) number == number) {
            return String.valueOf((int) number);
        }
        return String.valueOf(number);
    }

    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Number) obj).eval("") == this.eval("");
    }
}
