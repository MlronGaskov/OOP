package ru.nsu.gaskov;

import java.util.Objects;

public class Mul extends Expression {
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

    public Mul(Expression leftMultiplier, Expression rightMultiplier) {
        this.leftMultiplier = leftMultiplier;
        this.rightMultiplier = rightMultiplier;
    }

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

    public Expression getLeftMultiplier() {
        return leftMultiplier;
    }

    public Expression getRightMultiplier() {
        return rightMultiplier;
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(
            new Mul(leftMultiplier.derivative(variable), rightMultiplier),
            new Mul(leftMultiplier, rightMultiplier.derivative(variable))
        );
    }

    @Override
    public double eval(String variablesValues) {
        return leftMultiplier.eval(variablesValues) * rightMultiplier.eval(variablesValues);
    }

    @Override
    public Expression simplify() {
        try {
            return new Number(this.eval(""));
        } catch (IllegalArgumentException e) {
            Expression leftMultiplierSimplified = leftMultiplier.simplify();
            Expression rightMultiplierSimplified = rightMultiplier.simplify();
            if (Objects.equals(leftMultiplierSimplified, new Number(0))
                || Objects.equals(rightMultiplierSimplified, new Number(0)))
            {
                return new Number(0);
            }
            if (Objects.equals(leftMultiplierSimplified, new Number(1))) {
                return rightMultiplierSimplified;
            }
            if (Objects.equals(rightMultiplierSimplified, new Number(1))) {
                return leftMultiplierSimplified;
            }
            return new Mul(leftMultiplierSimplified, rightMultiplierSimplified);
        }
    }

    @Override
    public String toString() {
        return "(" + leftMultiplier.toString() + "*" + rightMultiplier.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return (Objects.equals(((Mul) obj).getLeftMultiplier(), leftMultiplier)
            && Objects.equals(((Mul) obj).getRightMultiplier(), rightMultiplier))
            || (Objects.equals(((Mul) obj).getLeftMultiplier(), rightMultiplier)
            && Objects.equals(((Mul) obj).getRightMultiplier(), leftMultiplier));
    }
}
