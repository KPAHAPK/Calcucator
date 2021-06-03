package com.android1.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;

enum Calculator {
    ADD('+', Double::sum),
    DIVIDE('/', (left, right) -> {
        if (right == 0) throw new ArithmeticException("Деление на 0!");
        return left / right;

    }),
    MULTIPLY('x', (left, right) -> left * right),
    SUBTRACT('-', (left, right) -> left - right);

    private final char symbol;
    DoubleBinaryOperator doubleBinaryOperator;
    public static Map<Character, Calculator> map = new HashMap<Character, Calculator>() {{
        put(ADD.getSymbol(), ADD);
        put(DIVIDE.getSymbol(), DIVIDE);
        put(MULTIPLY.getSymbol(), MULTIPLY);
        put(SUBTRACT.getSymbol(), SUBTRACT);

    }};

    Calculator(char symbol, DoubleBinaryOperator doubleBinaryOperator) {
        this.symbol = symbol;
        this.doubleBinaryOperator = doubleBinaryOperator;
    }

    public char getSymbol() {
        return symbol;
    }

    public static Calculator getOperation(char operator) {
        return map.get(operator);
    }

    public double calculate(double left, double right) {
        return doubleBinaryOperator.applyAsDouble(left, right);
    }
}

