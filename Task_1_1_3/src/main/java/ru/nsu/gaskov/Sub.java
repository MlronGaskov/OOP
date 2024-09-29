package ru.nsu.gaskov;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class representing a subtraction operation in a mathematical expression.
 */
public class Sub extends Expression {

    /**
     * Checks if the provided string represents a valid subtraction operation.
     *
     * @param expressionString the string to check
     * @return true if the string represents a valid subtraction, false otherwise
     */
    public static boolean isSub(String expressionString) {
        expressionString = Expression.removeOuterBrackets(expressionString);
        String subtrahend = getLastTerm(expressionString);
        return subtrahend.length() < expressionString.length()
            && expressionString.charAt(expressionString.length() - subtrahend.length() - 1) == '-';
    }

    private final Expression minuend;
    private final Expression subtrahend;

    /**
     * Constructs a Sub instance with the specified minuend and subtrahend.
     *
     * @param minuend   the expression from which another expression is to be subtracted
     * @param subtrahend the expression to be subtracted
     */
    public Sub(Expression minuend, Expression subtrahend) {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
    }

    /**
     * Constructs a Sub instance from a string representation of a subtraction operation.
     *
     * @param expressionString the string to convert into a subtraction expression
     * @throws IllegalArgumentException if the string is not a valid subtraction format
     */
    public Sub(String expressionString) {
        if (!isSub(expressionString)) {
            throw new IllegalArgumentException("Invalid Sub format.");
        }
        expressionString = Expression.removeOuterBrackets(expressionString);
        subtrahend = Expression.create(getLastTerm(expressionString));
        minuend = Expression.create(
            expressionString.substring(0,
                expressionString.length() - getLastTerm(expressionString).length() - 1)
        );
    }

    /**
     * Retrieves the minuend of the subtraction.
     *
     * @return the minuend of the subtraction
     */
    public Expression getMinuend() {
        return minuend;
    }

    /**
     * Retrieves the subtrahend of the subtraction.
     *
     * @return the subtrahend of the subtraction
     */
    public Expression getSubtrahend() {
        return subtrahend;
    }

    /**
     * Computes the derivative of the subtraction expression with respect to the specified variable.
     *
     * @param variable the variable to differentiate by
     * @return a new Sub instance representing the derivative of the subtraction
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(minuend.derivative(variable), subtrahend.derivative(variable));
    }

    /**
     * Evaluates the subtraction expression using the provided variable values.
     *
     * @param variablesValues a map of variable names to their values
     * @return the result of the subtraction
     */
    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        return minuend.eval(variablesValues) - subtrahend.eval(variablesValues);
    }

    /**
     * Simplifies the subtraction expression.
     * If both terms are numbers, it returns a new Number instance representing their difference.
     * If the subtrahend is 0, it returns the minuend.
     * If both terms are equal, it returns 0.
     *
     * @return a simplified expression
     */
    @Override
    public Expression simplify() {
        Expression minuendSimplified = minuend.simplify();
        Expression subtrahendSimplified = subtrahend.simplify();

        if (minuendSimplified instanceof Number
            && subtrahendSimplified instanceof Number) {
            return new Number(minuendSimplified.eval(new HashMap<>())
                - subtrahendSimplified.eval(new HashMap<>()));
        }

        if (subtrahendSimplified.isEquals(new Number(0))) {
            return minuendSimplified;
        }
        if (minuendSimplified.isEquals(subtrahendSimplified)) {
            return new Number(0);
        }

        return new Sub(minuendSimplified, subtrahendSimplified);
    }

    /**
     * Returns the string representation of the subtraction expression.
     *
     * @return a string representing the subtraction in the format "(minuend - subtrahend)"
     */
    @Override
    public String toString() {
        return "(" + minuend.toString() + "-" + subtrahend.toString() + ")";
    }

    /**
     * Checks if this Sub expression is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the object is a Sub and both terms are equal, false otherwise
     */
    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Sub) obj).getMinuend().isEquals(minuend)
            && ((Sub) obj).getSubtrahend().isEquals(subtrahend);
    }
}
