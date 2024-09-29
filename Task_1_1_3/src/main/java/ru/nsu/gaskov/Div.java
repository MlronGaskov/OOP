package ru.nsu.gaskov;

import java.util.HashMap;
import java.util.Objects;

public class Div extends Expression {
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

    public Div(Expression dividend, Expression divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
    }

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

    public Expression getDividend() {
        return dividend;
    }

    public Expression getDivisor() {
        return divisor;
    }

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

    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        return dividend.eval(variablesValues) / divisor.eval(variablesValues);
    }

    @Override
    public Expression simplify() {
        Expression dividendSimplified = dividend.simplify();
        Expression divisorSimplified = divisor.simplify();
        if (dividendSimplified.getClass() == Number.class
            && divisorSimplified.getClass() == Number.class) {
            return new Number(dividendSimplified.eval("") / divisorSimplified.eval(""));
        }
        if (Objects.equals(dividendSimplified, new Number(0))) {
            return new Number(0);
        }
        if (Objects.equals(divisorSimplified, new Number(1))) {
            return dividendSimplified;
        }
        return new Div(dividendSimplified, divisorSimplified);
    }

    @Override
    public String toString() {
        return "(" + dividend.toString() + "/" + divisor.toString() + ")";
    }

    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Div) obj).getDividend().isEquals(dividend)
            && ((Div) obj).getDivisor().isEquals(divisor);
    }
}
