package ru.nsu.gaskov;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class representing a division operation in a mathematical expression.
 */
public class Div extends Expression {

    /**
     * Checks if the provided string represents a valid division operation.
     *
     * @param expressionString the string to check
     * @return true if the string represents a valid division, false otherwise
     */
    public static boolean isDiv(String expressionString) {
        if (Add.isAdd(expressionString) || Sub.isSub(expressionString)) {
            return false;
        }
        expressionString = Expression.removeOuterBrackets(expressionString);
        String divisor = getLastMultiplier(expressionString);
        return divisor.length() < expressionString.length()
            && expressionString.charAt(expressionString.length() - divisor.length() - 1) == '/';
    }

    private final Expression dividend;
    private final Expression divisor;

    /**
     * Constructs a Div instance with the specified dividend and divisor.
     *
     * @param dividend the expression to be divided
     * @param divisor the expression by which to divide
     */
    public Div(Expression dividend, Expression divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
    }

    /**
     * Constructs a Div instance from a string representation of a division operation.
     *
     * @param expressionString the string to convert into a division expression
     * @throws IllegalArgumentException if the string is not a valid division format
     */
    public Div(String expressionString) {
        if (!isDiv(expressionString)) {
            throw new IllegalArgumentException("Invalid Div format.");
        }
        expressionString = Expression.removeOuterBrackets(expressionString);
        divisor = Expression.create(getLastMultiplier(expressionString));
        dividend = Expression.create(
            expressionString.substring(0,
                expressionString.length() - getLastMultiplier(expressionString).length() - 1)
        );
    }

    /**
     * Retrieves the dividend of the division.
     *
     * @return the dividend of the division
     */
    public Expression getDividend() {
        return dividend;
    }

    /**
     * Retrieves the divisor of the division.
     *
     * @return the divisor of the division
     */
    public Expression getDivisor() {
        return divisor;
    }

    /**
     * Computes the derivative of the division expression with respect to the specified variable
     * using the quotient rule.
     *
     * @param variable the variable to differentiate by
     * @return a new Div instance representing the derivative of the division
     */
    @Override
    public Expression derivative(String variable) {
        return new Div(
            new Sub(
                new Mul(dividend.derivative(variable), divisor),
                new Mul(dividend, divisor.derivative(variable))
            ),
            new Mul(divisor, divisor)
        );
    }

    /**
     * Evaluates the division expression using the provided variable values.
     *
     * @param variablesValues a map of variable names to their values
     * @return the result of the division
     */
    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        return dividend.eval(variablesValues) / divisor.eval(variablesValues);
    }

    /**
     * Simplifies the division expression.
     * If both terms are numbers, it returns a new Number instance representing their quotient.
     * If the dividend is 0, it returns 0. If the divisor is 1, it returns the dividend.
     *
     * @return a simplified expression
     */
    @Override
    public Expression simplify() {
        Expression dividendSimplified = dividend.simplify();
        Expression divisorSimplified = divisor.simplify();

        if (dividendSimplified instanceof Number
            && divisorSimplified instanceof Number) {
            return new Number(dividendSimplified.eval(new HashMap<>())
                / divisorSimplified.eval(new HashMap<>()));
        }
        if (dividendSimplified.isEquals(new Number(0))) {
            return new Number(0);
        }
        if (divisorSimplified.isEquals(new Number(1))) {
            return dividendSimplified;
        }

        return new Div(dividendSimplified, divisorSimplified);
    }

    /**
     * Returns the string representation of the division expression.
     *
     * @return a string representing the division in the format "(dividend / divisor)"
     */
    @Override
    public String toString() {
        return "(" + dividend.toString() + "/" + divisor.toString() + ")";
    }

    /**
     * Checks if this Div expression is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the object is a Div and both terms are equal, false otherwise
     */
    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Div) obj).getDividend().isEquals(dividend)
            && ((Div) obj).getDivisor().isEquals(divisor);
    }
}
