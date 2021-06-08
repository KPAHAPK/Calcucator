package com.android1.calculator;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    private ThemeStorage themeStorage;
    private boolean isFirstOperation = true;
    private boolean isOperatorSelected = false;
    private boolean needToCleanWorkField = false;

    MaterialButton pressedButton;
    private final int[] numberButtonIds = new int[]{R.id.number_1, R.id.number_2, R.id.number_3,
            R.id.number_4, R.id.number_5, R.id.number_6, R.id.number_7, R.id.number_8, R.id.number_9};
    private final int[] symbolButtonIds = new int[]{R.id.addition, R.id.subtraction, R.id.multiplication,
            R.id.division};

    private final String KEY = "Key";
    FieldsAndValues values;

    EditText workField;
    TextView result;
    TextView fakeWorkField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeStorage = new ThemeStorage(this);

        setTheme(themeStorage.getTheme().getResources());

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            values = new FieldsAndValues();
        } else {
            values = savedInstanceState.getParcelable(KEY);
        }

        workField = findViewById(R.id.work_field);
        result = findViewById(R.id.result);
        fakeWorkField = findViewById(R.id.fake_text_view);

        MaterialButton button0 = findViewById(R.id.number_0);

        MaterialButton buttonDot = findViewById(R.id.dot);
        MaterialButton buttonEqual = findViewById(R.id.equal);
        MaterialButton buttonRemove = findViewById(R.id.remove);
        MaterialButton buttonCleaner = findViewById(R.id.clean);
        MaterialButton buttonCE = findViewById(R.id.ce);
        MaterialButton buttonSwitch = findViewById(R.id.switch_button);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK)
                    if (result.getData() != null){

                    }
            }
        });

        findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        button0.setOnClickListener(element2 -> {
            if (!workField.getText().toString().isEmpty()) {
                setElementsOfExpression(element2);
            }
        });


        buttonDot.setOnClickListener(element1 -> {
            MaterialButton button = (MaterialButton) element1;
            workField.append(button.getText().toString());
        });

        buttonCleaner.setOnClickListener(v -> setDefault());

        buttonCE.setOnClickListener(v -> workField.setText("0"));

        buttonSwitch.setOnClickListener(v -> {
            int a = Integer.parseInt(workField.getText().toString());
            a = a * (-1);
            workField.setText(String.valueOf(a));
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


        buttonEqual.setOnClickListener(v -> {
            pressedButton = (MaterialButton) v;

            try {
                if (!isFirstOperation) {
                    if (isOperatorSelected) {
                        values.setSecondValue(Double.parseDouble(workField.getText().toString()));
                        isOperatorSelected = false;
                    }
                    result.setText(String.format("%s%s%s%s",
                            outputResult(values.getFirstValue()),
                            values.getOperator(),
                            outputResult(values.getSecondValue()),
                            pressedButton.getText().toString()));

                    values.setFirstValue(Operator.getOperation(values.getOperator()).calculate(
                            values.getFirstValue(),
                            values.getSecondValue()));
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


    private void setElementsOfExpression(View element) {
        pressedButton = (MaterialButton) element;

        char pressedButtonSymbolChar = pressedButton.getText().toString().charAt(0);
        String pressedButtonSymbolStr = pressedButton.getText().toString();

        if (Character.isDigit(pressedButtonSymbolChar)) {
            cleanWorkFieldIfNecessary();
            isOperatorSelected = !isFirstOperation;
            workField.append(pressedButtonSymbolStr);
        } else {
            isFirstOperation = false;
            if (setValues()) {
                values.setOperator(pressedButtonSymbolStr);
                result.setText(String.format("%s%s", outputResult(values.getFirstValue()), values.getOperator()));
            }
        }
    }

    private void cleanWorkFieldIfNecessary() {
        if (workField.getText().toString().equals("0") || needToCleanWorkField) {
            fakeWorkField.setText("");
            workField.setText("");
            needToCleanWorkField = false;
        }
    }

    private boolean setValues() {
        try {
            if (!isOperatorSelected) {
                values.setFirstValue(Double.parseDouble(workField.getText().toString()));
            } else {
                values.setSecondValue(Double.parseDouble(workField.getText().toString()));
                values.setFirstValue(Operator.getOperation(values.getOperator()).calculate(
                        values.getFirstValue(),
                        values.getSecondValue()));
            }
            fakeWorkField.setText(workField.getText().toString());
            isFirstOperation = false;
            isOperatorSelected = false;
            needToCleanWorkField = true;
            return true;
        } catch (ArithmeticException e) {
            setDefault();
            fakeWorkField.setText(e.getMessage());
            return false;
        }

    }


    private String outputResult(double value) {
        double tooLittleValue = 0.000000000000001;
        DecimalFormat df;
        if (value == (long) value) {
            return String.valueOf((long) value);
        } else {
            if (value < 1 && value > -1) {
                if (value < tooLittleValue && value > -tooLittleValue) {
                    df = new DecimalFormat("#.###############E0");
                    return df.format(value).replace("E", "*10^");
                } else {
                    df = new DecimalFormat("#.###############");
                    return df.format(value);
                }
            } else {
                if (String.valueOf(value).length() < 16) {
                    df = new DecimalFormat("#.###############");
                    return df.format(value);
                } else {
                    df = new DecimalFormat("#.###############E0");
                    return df.format(value).replace("E", "*10^");
                }
            }
        }
    }

    private void setDefault() {
        fakeWorkField.setText("0");
        workField.setText("0");
        result.setText("");
        values.setFirstValue(0);
        values.setSecondValue(0);
        isFirstOperation = true;
        isOperatorSelected = false;
        needToCleanWorkField = false;
    }

    private void setNumberButtonListeners() {
        for (int numberButtonId : numberButtonIds) {
            findViewById(numberButtonId).setOnClickListener(this::setElementsOfExpression);
        }
    }

    private void setSymbolButtonListeners() {
        for (int symbolButtonId : symbolButtonIds) {
            findViewById(symbolButtonId).setOnClickListener(this::setElementsOfExpression);
        }
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
        init();
    }

    private void init() {
        result.setText(String.format("%s%s", outputResult(values.getFirstValue()), values.getOperator()));
    }


}