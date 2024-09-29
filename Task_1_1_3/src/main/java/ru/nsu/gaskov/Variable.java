package ru.nsu.gaskov;

import java.util.HashMap;
import java.util.Objects;

public class Variable extends Expression {
    public static boolean isVariable(String expressionString) {
        expressionString = Expression.removeOuterBrackets(expressionString);
        return expressionString.matches("[a-zA-Z_]+[0-9_]*");
    }

    private final String name;

    public Variable(String name) {
        if (isVariable(name)) {
            this.name = Expression.removeOuterBrackets(name);
        } else {
            throw new IllegalArgumentException("Invalid variable name format.");
        }
    }

    @Override
    public Expression derivative(String variable) {
        variable = removeOuterBrackets(variable);
        if (Objects.equals(variable, name)) {
            return new Number(1);
        }
        return new Number(0);
    }

    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        if (!variablesValues.containsKey(name)) {
            throw new IllegalArgumentException("No such variable name.");
        }
        return variablesValues.get(name);
    }

    @Override
    public Expression simplify() {
        return new Variable(this.toString());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(obj.toString(), this.toString());
    }
}
