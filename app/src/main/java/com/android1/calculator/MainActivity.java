package com.android1.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    private ThemeStorage themeStorage;
    private boolean isFirstOperation = true;
    private boolean isOperatorSelected = false;
    boolean isValueSet = false;

    SwitchCompat switchTheme;
    MaterialButton pressedButton;
    char selectedOperator;

    private final String KEY = "Key";
    FieldsAndValues values = new FieldsAndValues();

    EditText workField;
    TextView result;
    TextView fakeWorkField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeStorage = new ThemeStorage(this);

        setTheme(themeStorage.getTheme().getResources());

        setContentView(R.layout.activity_main);

        workField = findViewById(R.id.work_field);
        result = findViewById(R.id.result);
        fakeWorkField = findViewById(R.id.fake_text_view);

        switchTheme = findViewById(R.id.switch_theme);

        MaterialButton button0 = findViewById(R.id.number_0);
        MaterialButton button1 = findViewById(R.id.number_1);
        MaterialButton button2 = findViewById(R.id.number_2);
        MaterialButton button3 = findViewById(R.id.number_3);
        MaterialButton button4 = findViewById(R.id.number_4);
        MaterialButton button5 = findViewById(R.id.number_5);
        MaterialButton button6 = findViewById(R.id.number_6);
        MaterialButton button7 = findViewById(R.id.number_7);
        MaterialButton button8 = findViewById(R.id.number_8);
        MaterialButton button9 = findViewById(R.id.number_9);
        MaterialButton buttonDivision = findViewById(R.id.division);
        MaterialButton buttonMultiplication = findViewById(R.id.multiplication);
        MaterialButton buttonAddition = findViewById(R.id.addition);
        MaterialButton buttonSubtraction = findViewById(R.id.subtraction);
        MaterialButton buttonDot = findViewById(R.id.dot);
        MaterialButton buttonEqual = findViewById(R.id.equal);
        MaterialButton buttonRemove = findViewById(R.id.remove);
        MaterialButton buttonCleaner = findViewById(R.id.clean);
        MaterialButton buttonCE = findViewById(R.id.ce);
        MaterialButton buttonSwitch = findViewById(R.id.switch_button);

        if (switchTheme.isChecked()) {
            themeStorage.setTheme(AppTheme.DARK);
        } else {
            themeStorage.setTheme(AppTheme.LIGHT);
        }

        switchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchTheme.isChecked()) {
                    themeStorage.setTheme(AppTheme.DARK);
                } else {
                    themeStorage.setTheme(AppTheme.LIGHT);
                }
                recreate();
            }
        });


        button0.setOnClickListener(element2 -> {
            if (!workField.getText().toString().isEmpty()) {
                addElementOfExpression(element2);
            }
        });
        button1.setOnClickListener(this::addElementOfExpression);
        button2.setOnClickListener(this::addElementOfExpression);
        button3.setOnClickListener(this::addElementOfExpression);
        button4.setOnClickListener(this::addElementOfExpression);
        button5.setOnClickListener(this::addElementOfExpression);
        button6.setOnClickListener(this::addElementOfExpression);
        button7.setOnClickListener(this::addElementOfExpression);
        button8.setOnClickListener(this::addElementOfExpression);
        button9.setOnClickListener(this::addElementOfExpression);

        buttonDot.setOnClickListener(element1 -> {
            if (workField.getText().toString().isEmpty()) {
                workField.setText("0");
            }
            addElementOfExpression(element1);
        });

        buttonCleaner.setOnClickListener(v -> setDefault());

        buttonCE.setOnClickListener(v -> workField.setText("0"));

        buttonSwitch.setOnClickListener(v -> {
            int a = Integer.parseInt(workField.getText().toString());
            a = a * (-1);
            workField.setText(String.format("%d", a));
        });

        buttonRemove.setOnClickListener(v -> {
            String expression = workField.getText().toString();
            int input = expression.length();
            if (input > 0) {
                workField.setText(expression.substring(0, --input));
            }
            if (input == 0) {
                workField.setText("0");
            }
        });

        buttonAddition.setOnClickListener(element -> {
            addElementOfExpression(element);
            result();
        });

        buttonMultiplication.setOnClickListener(element -> {
            addElementOfExpression(element);
            result();
        });

        buttonSubtraction.setOnClickListener(element -> {
            addElementOfExpression(element);
            result();
        });

        buttonDivision.setOnClickListener(element -> {
            addElementOfExpression(element);
            result();
        });

        buttonEqual.setOnClickListener(v -> {
            pressedButton = (MaterialButton) v;

            try {
                if (!isFirstOperation) {
                    if (!isOperatorSelected) {
                        values.setSecondValue(Double.parseDouble(workField.getText().toString()));
                        isOperatorSelected = true;
                    }
                    result.setText(String.format("%s%s%s%s",
                            outputResult(values.getFirstValue()),
                            selectedOperator,
                            outputResult(values.getSecondValue()),
                            pressedButton.getText().toString()));

                    double equalityResult = Calculator.getOperation(this.selectedOperator).calculate(
                            values.getFirstValue(),
                            values.getSecondValue());
                    values.setFirstValue(equalityResult);
                    workField.setText(outputResult(values.getFirstValue()));
                }
            } catch (ArithmeticException exception) {
                setDefault();
                fakeWorkField.setText(exception.getMessage());
            }

        });


        workField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                fakeWorkField.setText(workField.getText().toString());

            }
        });
    }


    private void addElementOfExpression(View element) {
        pressedButton = (MaterialButton) element;

        char pressedButtonSymbolChar = pressedButton.getText().toString().charAt(0);
        String pressedButtonSymbolStr = pressedButton.getText().toString();

        if (Character.isDigit(pressedButtonSymbolChar)
                || pressedButtonSymbolChar == '.') {
//            if (!isFirstOperation) {
//                isOperatorSelected = false;
//            }
            workField.append(pressedButtonSymbolStr);
        } else {
            selectedOperator = pressedButtonSymbolChar;
            result.setText(String.format("%s%s", outputResult(values.getFirstValue()), selectedOperator));
            values.setSecondValue(Double.parseDouble(workField.getText().toString()));
        }
    }

    private void result() {

//        workField.setText(String.format("%s", outputResult(values.getFirstValue())));
//        workField.setText("0");
//        fakeWorkField.setText(String.format("%s", outputResult(values.getFirstValue())));
    }

    private void setValues() {
//        try {
//            if (isFirstOperation || isOperatorSelected) {
//                values.setFirstValue(Double.parseDouble(workField.getText().toString()));
//                isFirstOperation = false;
//            } else {
//                values.setSecondValue(Double.parseDouble(workField.getText().toString()));
//                values.setFirstValue(Calculator.getOperation(this.selectedOperator).calculate(
//                        values.getFirstValue(),
//                        values.getSecondValue()));
//            }
//            isOperatorSelected = true;
//            return true;
//
//        } catch (ArithmeticException e) {
//            setDefault();
//            fakeWorkField.setText(e.getMessage());
//            return false;
//        }
        try {
            values.setFirstValue(Double.parseDouble(workField.getText().toString()));
            values.setSecondValue(Double.parseDouble(workField.getText().toString()));
            values.setFirstValue(Calculator.getOperation(this.selectedOperator).calculate(
                    values.getFirstValue(),
                    values.getSecondValue()));
            isOperatorSelected = true;

        } catch (
                ArithmeticException e) {
            setDefault();
            fakeWorkField.setText(e.getMessage());
        }
    }


    private String outputResult(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            if (String.valueOf(value).length() < 16) {
                DecimalFormat df = new DecimalFormat("#.###############");
                return df.format(value);
            } else {
                DecimalFormat df = new DecimalFormat("#.###############E0");
                return df.format(value).replace("E", "*10^");
            }
        }
    }

    private void setDefault() {
        fakeWorkField.setText("");
        workField.setText("0");
        result.setText("");
        values.setFirstValue(0);
        values.setSecondValue(0);
        isFirstOperation = true;
        isOperatorSelected = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable(KEY, values);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        instanceState.getParcelable(KEY);
    }

}