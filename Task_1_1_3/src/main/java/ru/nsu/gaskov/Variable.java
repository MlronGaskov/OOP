package ru.nsu.gaskov;

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
    public double eval(String variablesValues) {
        variablesValues = variablesValues.replaceAll(" ", "");
        for (String variable: variablesValues.split(";")) {
            if (variable.split("=").length != 2 || !Number.isNumber(variable.split("=")[1])) {
                throw new IllegalArgumentException("Invalid variable value format.");
            }
            if (Objects.equals(variable.split("=")[0], name)) {
                return (new Number(variable.split("=")[1])).eval(variablesValues);
            }
        }
        throw new IllegalArgumentException("No such variable name.");
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
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(obj.toString(), this.toString());
    }
}
