package ru.nsu.gaskov;

import java.util.HashMap;

/**
 * Class representing a numerical value in a mathematical expression.
 */
public class Number extends Expression {

    /**
     * Checks if the provided string can be parsed as a number.
     *
     * @param expressionString the string to check
     * @return true if the string is a valid number format, false otherwise
     */
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

    /**
     * Constructs a Number instance from a double value.
     *
     * @param number the numerical value
     */
    public Number(double number) {
        this.number = number;
    }

    /**
     * Constructs a Number instance from a string representation of a number.
     *
     * @param expressionString the string to convert into a number
     * @throws IllegalArgumentException if the string is not a valid number format
     */
    public Number(String expressionString) {
        expressionString = Expression.removeOuterBrackets(expressionString);
        if (isNumber(expressionString)) {
            this.number = Double.parseDouble(expressionString);
        } else {
            throw new IllegalArgumentException("Invalid number format.");
        }
    }

    /**
     * Computes the derivative of the number with respect to the specified variable.
     * For any constant number, the derivative is zero.
     *
     * @param variable the variable to differentiate by
     * @return a new Number instance representing 0
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * Evaluates the number, returning its value.
     *
     * @param variablesValues a map of variable names to their values (not used here)
     * @return the numerical value of the instance
     */
    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        return number;
    }

    /**
     * Simplifies the number expression.
     * Since a number is already simplified, it returns a new Number instance with the same value.
     *
     * @return a new Number instance representing this value
     */
    @Override
    public Expression simplify() {
        return new Number(this.toString());
    }

    /**
     * Returns the string representation of the number.
     * If the number is an integer, it returns it without decimal points.
     *
     * @return the string representation of the number
     */
    @Override
    public String toString() {
        if ((int) number == number) {
            return String.valueOf((int) number);
        }
        return String.valueOf(number);
    }

    /**
     * Checks if this Number is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the object is a Number and has the same value, false otherwise
     */
    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Number) obj).eval(new HashMap<>()) == this.eval(new HashMap<>());
    }
}
