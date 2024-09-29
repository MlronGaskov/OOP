package ru.nsu.gaskov;

import java.util.HashMap;
import java.util.Objects;

public class Add extends Expression {
    public static boolean isAdd(String expressionString) {
        expressionString = Expression.removeOuterBrackets(expressionString);
        String rightTerm = getLastTerm(expressionString);
        return rightTerm.length() < expressionString.length()
            && expressionString.charAt(expressionString.length() - rightTerm.length() - 1) == '+';
    }

    private final Expression leftTerm;
    private final Expression rightTerm;

    public Add(Expression leftTerm, Expression rightTerm) {
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }

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

    public Expression getLeftTerm() {
        return leftTerm;
    }

    public Expression getRightTerm() {
        return rightTerm;
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(leftTerm.derivative(variable), rightTerm.derivative(variable));
    }

    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        return leftTerm.eval(variablesValues) + rightTerm.eval(variablesValues);
    }

    @Override
    public Expression simplify() {
        Expression leftTermSimplified = leftTerm.simplify();
        Expression rightTermSimplified = rightTerm.simplify();
        if (leftTermSimplified.getClass() == Number.class
            && rightTermSimplified.getClass() == Number.class) {
            return new Number(leftTermSimplified.eval("") + rightTermSimplified.eval(""));
        }
        if (Objects.equals(leftTermSimplified, new Number(0))) {
            return rightTermSimplified;
        }
        if (Objects.equals(rightTermSimplified, new Number(0))) {
            return leftTermSimplified;
        }
        return new Add(leftTermSimplified, rightTermSimplified);
    }

    @Override
    public String toString() {
        return "(" + leftTerm.toString() + "+" + rightTerm.toString() + ")";
    }

    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return (leftTerm.isEquals(((Add) obj).leftTerm)
            && rightTerm.isEquals(((Add) obj).rightTerm))
            || (leftTerm.isEquals(((Add) obj).rightTerm)
            && leftTerm.isEquals(((Add) obj).leftTerm));
    }
}
