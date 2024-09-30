package ru.nsu.gaskov;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class representing a variable in a mathematical expression.
 */
public class Variable extends Expression {

    /**
     * Checks if the provided string is a valid variable name.
     *
     * @param expressionString the string to check
     * @return true if the string is a valid variable name, false otherwise
     */
    public static boolean isVariable(String expressionString) {
        expressionString = Expression.removeOuterBrackets(expressionString);
        return expressionString.matches("[a-zA-Z_]+[0-9_]*");
    }

    private final String name;

    /**
     * Constructs a Variable instance with the specified name.
     *
     * @param name the name of the variable
     * @throws IllegalArgumentException if the variable name is not valid
     */
    public Variable(String name) {
        if (isVariable(name)) {
            this.name = Expression.removeOuterBrackets(name);
        } else {
            throw new IllegalArgumentException("Invalid variable name format.");
        }
    }

    /**
     * Computes the derivative of the variable with respect to the specified variable.
     * Returns 1 if the variable matches, and 0 otherwise.
     *
     * @param variable the variable to differentiate by
     * @return a new Number instance representing the derivative
     */
    @Override
    public Expression derivative(String variable) {
        variable = removeOuterBrackets(variable);
        if (Objects.equals(variable, name)) {
            return new Number(1);
        }
        return new Number(0);
    }

    /**
     * Evaluates the variable using the provided values for variables.
     *
     * @param variablesValues a map of variable names to their values
     * @return the value of the variable
     * @throws IllegalArgumentException if the variable name is not found in the map
     */
    @Override
    public double eval(HashMap<String, Double> variablesValues) {
        if (!variablesValues.containsKey(name)) {
            throw new IllegalArgumentException("No such variable name.");
        }
        return variablesValues.get(name);
    }

    /**
     * Simplifies the variable expression.
     *
     * @return a new Variable instance representing this variable
     */
    @Override
    public Expression simplify() {
        return new Variable(this.toString());
    }

    /**
     * Returns the string representation of the variable.
     *
     * @return the name of the variable
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Checks if this Variable is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the object is a Variable and has the same name, false otherwise
     */
    @Override
    public boolean isEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(obj.toString(), this.toString());
    }
}
