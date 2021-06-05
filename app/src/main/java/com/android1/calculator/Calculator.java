package com.android1.calculator;

import android.annotation.SuppressLint;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;

enum Calculator {
    ADD(Double::sum),
    DIVIDE((left, right) -> {
        if (right == 0) throw new ArithmeticException("Деление на 0!");
        return left / right;

    }),
    MULTIPLY((left, right) -> left * right),
    SUBTRACT((left, right) -> left - right);

    DoubleBinaryOperator doubleBinaryOperator;
    MainActivity mainActivity = new MainActivity();
    @SuppressLint("ResourceType")
    char additionSymbol = mainActivity.getResources().getString(R.string.addition_sign).charAt(0);
    char divisionSymbol = mainActivity.getResources().getString(R.string.division_sign).charAt(0);
    char multiplicationSymbol = mainActivity.getResources().getString(R.string.multiplication_sign).charAt(0);
    char subtractionSymbol = mainActivity.getResources().getString(R.string.subtraction_sign).charAt(0);


    public static Map<Character, Calculator> map = new HashMap<Character, Calculator>() {{
        put(ADD.additionSymbol, ADD);
        put(DIVIDE.divisionSymbol, DIVIDE);
        put(MULTIPLY.multiplicationSymbol, MULTIPLY);
        put(SUBTRACT.subtractionSymbol, SUBTRACT);

    }};

    Calculator(DoubleBinaryOperator doubleBinaryOperator) {
        this.doubleBinaryOperator = doubleBinaryOperator;
    }

    public static Calculator getOperation(char operator) {
        return map.get(operator);
    }

    public double calculate(double left, double right) {
        return doubleBinaryOperator.applyAsDouble(left, right);
    }
}

