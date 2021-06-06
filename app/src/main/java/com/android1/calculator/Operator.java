package com.android1.calculator;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;

enum Operator {
    ADD(Double::sum),
    DIVIDE((left, right) -> {
        if (right == 0) throw new ArithmeticException("Деление на 0!");
        return left / right;

    }),
    MULTIPLY((left, right) -> left * right),
    SUBTRACT((left, right) -> left - right);

    DoubleBinaryOperator doubleBinaryOperator;
//    MainActivity mainActivity = new MainActivity();
//    char additionSymbol = mainActivity.getString(R.string.addition_sign).charAt(0);
//    char divisionSymbol = mainActivity.getString(R.string.division_sign).charAt(0);
//    char multiplicationSymbol = mainActivity.getString(R.string.multiplication_sign).charAt(0);
//    char subtractionSymbol = mainActivity.getString(R.string.subtraction_sign).charAt(0);


    public static Map<String, Operator> map = new HashMap<String, Operator>() {{
        put("+", ADD);
        put("/", DIVIDE);
        put("x", MULTIPLY);
        put("-", SUBTRACT);

    }};

    Operator(DoubleBinaryOperator doubleBinaryOperator) {
        this.doubleBinaryOperator = doubleBinaryOperator;
    }

    public static Operator getOperation(String operator) {
        return map.get(operator);
    }

    public double calculate(double left, double right) {
        return doubleBinaryOperator.applyAsDouble(left, right);
    }
}

