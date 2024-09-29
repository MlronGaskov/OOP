package ru.nsu.gaskov;

import java.util.HashMap;

/**
 * Abstract class representing a mathematical expression.
 */
public abstract class Expression {

    /**
     * Retrieves the last term in the given expression string.
     *
     * @param expressionString the expression string
     * @return the last term of the expression
     */
    protected static String getLastTerm(String expressionString) {
        return getLastPart(expressionString, false);
    }

    /**
     * Retrieves the last multiplier in the given expression string.
     *
     * @param expressionString the expression string
     * @return the last multiplier of the expression
     */
    protected static String getLastMultiplier(String expressionString) {
        return getLastPart(expressionString, true);
    }

    /**
     * Removes outer brackets from the expression string if they enclose the entire expression.
     *
     * @param expressionString the expression string
     * @return the expression string without outer brackets
     */
    protected static String removeOuterBrackets(String expressionString) {
        while (expressionString.startsWith("(")
            && expressionString.endsWith(")")
            && getLastPart(expressionString, true).length() == expressionString.length()) {
            expressionString = expressionString.substring(1, expressionString.length() - 1);
        }
        return expressionString;
    }

    /**
     * Creates an Expression object based on the provided expression string.
     *
     * @param expressionString the expression string to create the expression from
     * @return the corresponding Expression object
     * @throws IllegalArgumentException if the expression string is invalid
     */
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

    /**
     * Prints the expression in string representation to the standard output.
     */
    public void print() {
        System.out.println(this.toString());
    }

    /**
     * Evaluates the expression given a string of variable values.
     *
     * @param variablesValues a string containing variable values in the format "var1=value1;var2=value2;..."
     * @return the evaluated result of the expression
     * @throws IllegalArgumentException if the variable value format is invalid
     */
    public double eval(String variablesValues) {
        HashMap<String, Double> parsedValues = new HashMap<>();
        if (variablesValues.isEmpty()) {
            return this.eval(parsedValues);
        }
        variablesValues = variablesValues.replaceAll(" ", "");
        for (String variable : variablesValues.split(";")) {
            if (variable.split("=").length != 2
                || !Number.isNumber(variable.split("=")[1])) {
                throw new IllegalArgumentException("Invalid variable value format.");
            }
            parsedValues.put(
                variable.split("=")[0],
                Double.parseDouble(variable.split("=")[1])
            );
        }
        return this.eval(parsedValues);
    }

    /**
     * Computes the derivative of the expression with respect to the specified variable.
     *
     * @param variable the variable to differentiate by
     * @return the derivative of the expression
     */
    public abstract Expression derivative(String variable);

    /**
     * Evaluates the expression using the provided variable values.
     *
     * @param variablesValues a map containing variable names and their corresponding values
     * @return the evaluated result of the expression
     */
    public abstract double eval(HashMap<String, Double> variablesValues);

    /**
     * Simplifies the expression.
     *
     * @return a simplified version of the expression
     */
    public abstract Expression simplify();

    /**
     * Checks if two expressions are equal.
     *
     * @param obj the object to compare with
     * @return true if the expressions are equal, false otherwise
     */
    public abstract boolean isEquals(Object obj);

    /**
     * Simplifies the given expression.
     *
     * @param expression the expression to simplify
     * @return a simplified version of the expression
     */
    public static Expression simplify(Expression expression) {
        return expression.simplify();
    }

    @Override
    public abstract String toString();

    /**
     * Retrieves the last part of the expression string based on the specified criteria for multipliers.
     *
     * @param expressionString the expression string to analyze
     * @param isMultiplier true if looking for multipliers, false otherwise
     * @return the last part of the expression
     */
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
