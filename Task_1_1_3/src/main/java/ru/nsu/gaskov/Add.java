package ru.nsu.gaskov;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class representing an addition operation in a mathematical expression.
 */
public class Add extends Expression {

    /**
     * Checks if the provided string represents a valid addition operation.
     *
     * @param expressionString the string to check
     * @return true if the string represents a valid addition, false otherwise
     */
    public static boolean isAdd(String expressionString) {
        expressionString = Expression.removeOuterBrackets(expressionString);
        String rightTerm = getLastTerm(expressionString);
        return rightTerm.length() < expressionString.length()
            && expressionString.charAt(expressionString.length() - rightTerm.length() - 1) == '+';
    }

    private final Expression leftTerm;
    private final Expression rightTerm;

    /**
     * Constructs an Add instance with the specified left and right terms.
     *
     * @param leftTerm  the left expression in the addition
     * @param rightTerm the right expression in the addition
     */
    public Add(Expression leftTerm, Expression rightTerm) {
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }

    /**
     * Constructs an Add instance from a string representation of an addition operation.
     *
     * @param expressionString the string to convert into an addition expression
     * @throws IllegalArgumentException if the string is not a valid addition format
     */
    public Add(String expressionString) {
        if (!isAdd(expressionString)) {
            throw new IllegalArgumentException("Invalid Add format.");
        }
        expressionString = Expression.removeOuterBrackets(expressionString);
        rightTerm = Expression.create(getLastTerm(expressionString));
        leftTerm = Expression.create(
            expressionString.substring(0,
                expressionString.length() - getLastTerm(expressionString).length() - 1)
        );
    }

    /**
     * Retrieves the left term of the addition.
     *
     * @return the left term of the addition
     */
    public Expression getLeftTerm() {
        return leftTerm;
    }

    /**
     * Retrieves the right term of the addition.
     *
     * @return the right term of the addition
     */
    public Expression getRightTerm() {
        return rightTerm;
    }

    /**
     * Computes the derivative of the addition expression with respect to the specified variable.
     *
     * @param variable the variable to differentiate by
     * @return a new Add instance representing the derivative of the addition
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(leftTerm.derivative(variable), rightTerm.derivative(variable));
    }

    /**
     * Evaluates the addition expression using the provided variable values.
     *
     * @param variablesValues a map of variable names to their values
     * @return the result of the addition
     */
    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        return leftTerm.eval(variablesValues) + rightTerm.eval(variablesValues);
    }

    /**
     * Simplifies the addition expression.
     * If both terms are numbers, it returns a new Number instance representing their sum.
     * If any term is 0, it returns the other term.
     *
     * @return a simplified expression
     */
    @Override
    public Expression simplify() {
        Expression leftTermSimplified = leftTerm.simplify();
        Expression rightTermSimplified = rightTerm.simplify();

        if (leftTermSimplified instanceof Number &&
            rightTermSimplified instanceof Number) {
            return new Number(leftTermSimplified.eval(new HashMap<>()) +
                rightTermSimplified.eval(new HashMap<>()));
        }

        if (leftTermSimplified.isEquals(new Number(0))) {
            return rightTermSimplified;
        }
        if (rightTermSimplified.isEquals(new Number(0))) {
            return leftTermSimplified;
        }

        return new Add(leftTermSimplified, rightTermSimplified);
    }

    /**
     * Returns the string representation of the addition expression.
     *
     * @return a string representing the addition in the format "(leftTerm + rightTerm)"
     */
    @Override
    public String toString() {
        return "(" + leftTerm.toString() + "+" + rightTerm.toString() + ")";
    }

    /**
     * Checks if this Add expression is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the object is an Add and both terms are equal, false otherwise
     */
    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return (leftTerm.isEquals(((Add) obj).leftTerm)
            && rightTerm.isEquals(((Add) obj).rightTerm))
            || (leftTerm.isEquals(((Add) obj).rightTerm)
            && rightTerm.isEquals(((Add) obj).leftTerm));
    }
}
