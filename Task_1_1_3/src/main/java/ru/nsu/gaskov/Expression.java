package ru.nsu.gaskov;

import java.util.HashMap;

public abstract class Expression {
    protected static String getLastTerm(String expressionString) {
        return getLastPart(expressionString, false);
    }

    protected static String getLastMultiplier(String expressionString) {
        return getLastPart(expressionString, true);
    }

    protected static String removeOuterBrackets(String expressionString) {
        while (expressionString.startsWith("(")
            && expressionString.endsWith(")")
            && getLastPart(expressionString, true).length() == expressionString.length())
        {
            expressionString = expressionString.substring(1, expressionString.length() - 1);
        }
        return expressionString;
    }

    public static Expression create(String expressionString) {
        if (Number.isNumber(expressionString)) {
            return new Number(expressionString);
        } else if (Variable.isVariable(expressionString)) {
            return new Variable(expressionString);
        } else if (Add.isAdd(expressionString)) {
            return new Add(expressionString);
        } else if (Sub.isSub(expressionString)) {
            return new Sub(expressionString);
        } else if (Mul.isMul(expressionString)) {
            return new Mul(expressionString);
        } else if (Div.isDiv(expressionString)) {
            return new Div(expressionString);
        }
        throw new IllegalArgumentException("Invalid Expression format.");
    }

    public void print() {
        System.out.println(this.toString());
    }


    public double eval(String variablesValues) {
        HashMap<String, Double> parsedValues = new HashMap<>();
        if (variablesValues.isEmpty()) {
            return this.eval(parsedValues);
        }
        variablesValues = variablesValues.replaceAll(" ", "");
        for (String variable: variablesValues.split(";")) {
            if (variable.split("=").length != 2
                || !Number.isNumber(variable.split("=")[1]))
            {
                throw new IllegalArgumentException("Invalid variable value format.");
            }
            parsedValues.put(
                variable.split("=")[0],
                Double.parseDouble(variable.split("=")[1])
            );
        }
        return this.eval(parsedValues);
    }

    public abstract Expression derivative(String variable);
    public abstract double eval(HashMap<String, Double> variablesValues);
    public abstract Expression simplify();
    public abstract boolean isEquals(Object obj);

    public static Expression simplify(Expression expression) {
        return expression.simplify();
    }

    @Override
    public abstract String toString();

    private static String getLastPart(String expressionString, boolean isMultiplier) {
        int inBracketsCnt = 0;
        for (int i = expressionString.length() - 1; i >= 0; --i) {
            char c = expressionString.charAt(i);
            switch (c) {
                case ')' -> inBracketsCnt += 1;
                case '(' -> inBracketsCnt -= 1;
                case '+', '-', '*', '/' -> {
                    if (inBracketsCnt == 0 && (c != '*' && c != '/' || isMultiplier)) {
                        return expressionString.substring(i + 1);
                    }
                }
            }
        }
        return expressionString;
    }
}
