package ru.nsu.gaskov;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class representing a multiplication operation in a mathematical expression.
 */
public class Mul extends Expression {

    /**
     * Checks if the provided string represents a valid multiplication operation.
     *
     * @param expressionString the string to check
     * @return true if the string represents a valid multiplication, false otherwise
     */
    public static boolean isMul(String expressionString) {
        if (Add.isAdd(expressionString) || Sub.isSub(expressionString)) {
            return false;
        }
        expressionString = Expression.removeOuterBrackets(expressionString);
        String rightMultiplier = getLastMultiplier(expressionString);
        return rightMultiplier.length() < expressionString.length()
            && expressionString.charAt(expressionString.length() - rightMultiplier.length() - 1) == '*';
    }

    private final Expression leftMultiplier;
    private final Expression rightMultiplier;

    /**
     * Constructs a Mul instance with the specified left and right multipliers.
     *
     * @param leftMultiplier  the expression on the left side of the multiplication
     * @param rightMultiplier the expression on the right side of the multiplication
     */
    public Mul(Expression leftMultiplier, Expression rightMultiplier) {
        this.leftMultiplier = leftMultiplier;
        this.rightMultiplier = rightMultiplier;
    }

    /**
     * Constructs a Mul instance from a string representation of a multiplication operation.
     *
     * @param expressionString the string to convert into a multiplication expression
     * @throws IllegalArgumentException if the string is not a valid multiplication format
     */
    public Mul(String expressionString) {
        if (!isMul(expressionString)) {
            throw new IllegalArgumentException("Invalid Mul format.");
        }
        expressionString = Expression.removeOuterBrackets(expressionString);
        rightMultiplier = Expression.create(getLastMultiplier(expressionString));
        leftMultiplier = Expression.create(
            expressionString.substring(0,
                expressionString.length() - getLastMultiplier(expressionString).length() - 1)
        );
    }

    /**
     * Retrieves the left multiplier of the multiplication.
     *
     * @return the left multiplier of the multiplication
     */
    public Expression getLeftMultiplier() {
        return leftMultiplier;
    }

    /**
     * Retrieves the right multiplier of the multiplication.
     *
     * @return the right multiplier of the multiplication
     */
    public Expression getRightMultiplier() {
        return rightMultiplier;
    }

    /**
     * Computes the derivative of the multiplication expression with respect to the specified variable
     * using the product rule.
     *
     * @param variable the variable to differentiate by
     * @return a new Add instance representing the derivative of the multiplication
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(
            new Mul(leftMultiplier.derivative(variable), rightMultiplier),
            new Mul(leftMultiplier, rightMultiplier.derivative(variable))
        );
    }

    /**
     * Evaluates the multiplication expression using the provided variable values.
     *
     * @param variablesValues a map of variable names to their values
     * @return the result of the multiplication
     */
    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        return leftMultiplier.eval(variablesValues) * rightMultiplier.eval(variablesValues);
    }

    /**
     * Simplifies the multiplication expression.
     * If both terms are numbers, it returns a new Number instance representing their product.
     * If either term is 0, it returns 0. If either term is 1, it returns the other term.
     *
     * @return a simplified expression
     */
    @Override
    public Expression simplify() {
        Expression leftMultiplierSimplified = leftMultiplier.simplify();
        Expression rightMultiplierSimplified = rightMultiplier.simplify();

        if (leftMultiplierSimplified instanceof Number &&
            rightMultiplierSimplified instanceof Number) {
            return new Number(leftMultiplierSimplified.eval(new HashMap<>()) *
                rightMultiplierSimplified.eval(new HashMap<>()));
        }
        if (leftMultiplierSimplified.isEquals(new Number(0)) ||
            rightMultiplierSimplified.isEquals(new Number(0))) {
            return new Number(0);
        }
        if (leftMultiplierSimplified.isEquals(new Number(1))) {
            return rightMultiplierSimplified;
        }
        if (rightMultiplierSimplified.isEquals(new Number(1))) {
            return leftMultiplierSimplified;
        }

        return new Mul(leftMultiplierSimplified, rightMultiplierSimplified);
    }

    /**
     * Returns the string representation of the multiplication expression.
     *
     * @return a string representing the multiplication in the format "(leftMultiplier * rightMultiplier)"
     */
    @Override
    public String toString() {
        return "(" + leftMultiplier.toString() + "*" + rightMultiplier.toString() + ")";
    }

    /**
     * Checks if this Mul expression is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the object is a Mul and both multipliers are equal, false otherwise
     */
    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return (((Mul) obj).getLeftMultiplier().isEquals(leftMultiplier)
            && ((Mul) obj).getRightMultiplier().isEquals(rightMultiplier))
            || (((Mul) obj).getLeftMultiplier().isEquals(rightMultiplier)
            && ((Mul) obj).getRightMultiplier().isEquals(leftMultiplier));
    }
}
