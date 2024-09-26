package ru.nsu.gaskov;

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
    public double eval(String variablesValues) {
        return leftTerm.eval(variablesValues) + rightTerm.eval(variablesValues);
    }

    @Override
    public Expression simplify() {
        try {
            return new Number(this.eval(""));
        } catch (IllegalArgumentException e) {
            Expression leftTermSimplified = leftTerm.simplify();
            Expression rightTermSimplified = rightTerm.simplify();
            if (Objects.equals(leftTermSimplified, new Number(0))) {
                return rightTermSimplified;
            }
            if (Objects.equals(rightTermSimplified, new Number(0))) {
                return leftTermSimplified;
            }
            return new Add(leftTermSimplified, rightTermSimplified);
        }
    }

    @Override
    public String toString() {
        return "(" + leftTerm.toString() + "+" + rightTerm.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return (Objects.equals(((Add) obj).getLeftTerm(), leftTerm)
            && Objects.equals(((Add) obj).getRightTerm(), rightTerm))
            || (Objects.equals(((Add) obj).getLeftTerm(), rightTerm)
            && Objects.equals(((Add) obj).getRightTerm(), leftTerm));
    }
}
