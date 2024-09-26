package ru.nsu.gaskov;

import java.util.Objects;

public class Sub extends Expression {
    public static boolean isSub(String expressionString) {
        expressionString = Expression.removeOuterBrackets(expressionString);
        String subtrahend = getLastTerm(expressionString);
        return subtrahend.length() < expressionString.length()
            && expressionString.charAt(expressionString.length() - subtrahend.length() - 1) == '-';
    }

    private final Expression minuend;
    private final Expression subtrahend;

    public Sub(Expression minuend, Expression subtrahend) {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
    }

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

    public Expression getMinuend() {
        return minuend;
    }

    public Expression getSubtrahend() {
        return subtrahend;
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(minuend.derivative(variable), subtrahend.derivative(variable));
    }

    @Override
    public double eval(String variablesValues) {
        return minuend.eval(variablesValues) - subtrahend.eval(variablesValues);
    }

    @Override
    public Expression simplify() {
        try {
            return new Number(this.eval(""));
        } catch (IllegalArgumentException e) {
            Expression minuendSimplified = minuend.simplify();
            Expression subtrahendSimplified = subtrahend.simplify();
            if (Objects.equals(subtrahendSimplified, new Number(0))) {
                return minuendSimplified;
            }
            if (Objects.equals(minuendSimplified, subtrahendSimplified)) {
                return new Number(0);
            }
            return new Sub(minuendSimplified, subtrahendSimplified);
        }
    }

    @Override
    public String toString() {
        return "(" + minuend.toString() + "-" + subtrahend.toString() + ")";
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(((Sub) obj).getMinuend(), minuend)
            && Objects.equals(((Sub) obj).getSubtrahend(), subtrahend);
    }
}
