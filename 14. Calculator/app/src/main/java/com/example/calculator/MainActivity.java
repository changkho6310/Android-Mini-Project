package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtProcess, edtOutput;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnClear, btnBackspace, btnDivide, btnPlus, btnMinus, btnMultiply, btnDot, btnEqual;
    String strProcess = "";
    String strOutput = "";
    boolean isDotClicked = false;
    boolean isNumberBeforeDot = false;
    boolean isOperatorAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMapping();
        addControls();

    }

    private void showProcess() {
        edtProcess.setText(strProcess);
    }

    private void addControls() {
        btn0.setOnClickListener(view -> {
            onNumberButtonClicked("0");
            isOperatorAvailable = true;
            showProcess();
        });
        btn1.setOnClickListener(view -> {
            onNumberButtonClicked("1");
            isOperatorAvailable = true;
            showProcess();
        });
        btn2.setOnClickListener(view -> {
            onNumberButtonClicked("2");
            isOperatorAvailable = true;
            showProcess();
        });
        btn3.setOnClickListener(view -> {
            onNumberButtonClicked("3");
            isOperatorAvailable = true;
            showProcess();
        });
        btn4.setOnClickListener(view -> {
            onNumberButtonClicked("4");
            isOperatorAvailable = true;
            showProcess();
        });
        btn5.setOnClickListener(view -> {
            onNumberButtonClicked("5");
            isOperatorAvailable = true;
            showProcess();
        });
        btn6.setOnClickListener(view -> {
            onNumberButtonClicked("6");
            isOperatorAvailable = true;
            showProcess();
        });
        btn7.setOnClickListener(view -> {
            onNumberButtonClicked("7");
            isOperatorAvailable = true;
            showProcess();
        });
        btn8.setOnClickListener(view -> {
            onNumberButtonClicked("8");
            isOperatorAvailable = true;
            showProcess();
        });
        btn9.setOnClickListener(view -> {
            onNumberButtonClicked("9");
            isOperatorAvailable = true;
            showProcess();
        });

        btnDot.setOnClickListener(view -> {
            onDotButtonClicked();
            showProcess();
        });


        btnPlus.setOnClickListener(view -> {
            if (isOperatorAvailable) {
                onOperatorButtonClicked("+");
                showProcess();
            } else {
                toastMakeText(this, "Wrong Input");
            }
            isOperatorAvailable = false;
        });

        btnMinus.setOnClickListener(view -> {
            if (isOperatorAvailable) {
                onOperatorButtonClicked("-");
                showProcess();
            } else {
                toastMakeText(this, "Wrong Input");
            }
            isOperatorAvailable = false;
        });

        btnMultiply.setOnClickListener(view -> {
            if (isOperatorAvailable) {
                onOperatorButtonClicked("*");
                showProcess();
            } else {
                toastMakeText(this, "Wrong Input");
            }
            isOperatorAvailable = false;
        });

        btnDivide.setOnClickListener(view -> {
            if (isOperatorAvailable) {
                onOperatorButtonClicked("/");
                showProcess();
            } else {
                toastMakeText(this, "Wrong Input");
            }
            isOperatorAvailable = false;
        });

        btnClear.setOnClickListener(view -> {
            strProcess = "";
            strOutput = "";
            showProcess();
            showOutput();
        });

        btnBackspace.setOnClickListener(view -> {
            if (strProcess.length() > 0) {
                strProcess = strProcess.substring(0, strProcess.length() - 1);
            }
            showProcess();
        });

        btnEqual.setOnClickListener(view -> {
            showProcess();
            calculate();
            edtOutput.setText(strOutput);
        });
    }

    private void showOutput() {
        edtOutput.setText(strOutput);
    }

    private void viewMapping() {
        edtProcess = findViewById(R.id.edt_process);
        edtOutput = findViewById(R.id.edt_output);
        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btnClear = findViewById(R.id.btn_clear);
        btnBackspace = findViewById(R.id.btn_backspace);
        btnDivide = findViewById(R.id.btn_divide);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnDot = findViewById(R.id.btn_dot);
        btnEqual = findViewById(R.id.btn_equal);
    }

    private void onNumberButtonClicked(String number) {
        if (!isDotClicked) {
            isNumberBeforeDot = true;
        }
        strProcess += number;
    }

    private void onDotButtonClicked() {
        if (!isDotClicked && isNumberBeforeDot) {
            // Do sth
            strProcess += ".";
            isDotClicked = true;
            isNumberBeforeDot = false;
        } else {
            toastMakeText(this, "Wrong Input");
        }
    }

    private void onOperatorButtonClicked(String operator) {
        isDotClicked = false;
        isNumberBeforeDot = false;
        strProcess += operator;
    }

    private void toastMakeText(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private ArrayList<Integer> getOperatorPositions(String str) {
        ArrayList<Integer> operatorPositions = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (isOperator(str.charAt(i))) {
                operatorPositions.add(i);
            }
        }
        return operatorPositions;
    }

    private ArrayList<Float> parseStringToFloatList() {
        ArrayList<Integer> operatorPositions = getOperatorPositions(strProcess);
        ArrayList<Float> fNumArr = new ArrayList<Float>();
        for (int i = 0; i <= operatorPositions.size(); i++) {
            if (i == 0) {
                // The first iteration
                String fStr = strProcess.substring(0, operatorPositions.get(i));
                fNumArr.add(Float.parseFloat(fStr));
            } else if (i == operatorPositions.size()) {
                // The last iteration
                String fStr = strProcess.substring(operatorPositions.get(i - 1) + 1);
                fNumArr.add(Float.parseFloat(fStr));
            } else {
                String fStr = strProcess.substring(operatorPositions.get(i - 1) + 1, operatorPositions.get(i));
                fNumArr.add(Float.parseFloat(fStr));
            }
        }
        return fNumArr;
    }

    private ArrayList<String> getOperatorsList(String str) {
        ArrayList<String> operatorArr = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (isOperator(str.charAt(i))) {
                operatorArr.add(String.valueOf(str.charAt(i)));
            }
        }
        return operatorArr;
    }

    private void calculate() {
        if (!isInputValid()) {
            strOutput = getResources().getString(R.string.nan_wrong_input);
            return;
        }
        if (strProcess.length() == 0) {
            strOutput = "";
            return;
        }
        ArrayList<String> operatorArr = getOperatorsList(strProcess);
        if (operatorArr.size() == 0) {
            strOutput = String.valueOf(Float.parseFloat(strProcess));
            return;
        }
        ArrayList<Float> fNumArr = parseStringToFloatList();

        for (int i = 0; i < operatorArr.size(); ) {
            if (operatorArr.get(i).equals("*")) {
                fNumArr.set(i, fNumArr.get(i) * fNumArr.get(i + 1));
                operatorArr.remove(i);
                fNumArr.remove(i + 1);
            } else if (operatorArr.get(i).equals("/")) {
                if (fNumArr.get(i + 1) == 0) {
                    strOutput = getResources().getString(R.string.nan_divide_zero);
                    return;
                } else {
                    fNumArr.set(i, fNumArr.get(i) / fNumArr.get(i + 1));
                    operatorArr.remove(i);
                    fNumArr.remove(i + 1);
                }
            } else {
                i++;
            }
        }

        int i = 0;
        while (operatorArr.size() > 0) {
            if (operatorArr.get(i).equals("+")) {
                fNumArr.set(i, fNumArr.get(i) + fNumArr.get(i + 1));
                operatorArr.remove(i);
                fNumArr.remove(i + 1);
            } else if (operatorArr.get(i).equals("-")) {
                fNumArr.set(i, fNumArr.get(i) - fNumArr.get(i + 1));
                operatorArr.remove(i);
                fNumArr.remove(i + 1);
            } else {
                i++;
            }
        }

        strOutput = String.valueOf(fNumArr.get(0));
    }

    private boolean isInputValid() {
        // Not Valid : 5/3-2*4+3-
        // Not Valid : 5/3-2*4+3+
        // Not Valid : 5/3-2*4+3/
        // Not Valid : 5/3-2*4+3*
        if (strProcess.length() > 0) {
            char c = strProcess.charAt(strProcess.length() - 1);
            return c != '+' && c != '-' && c != '*' && c != '/';
        }
        return true;
    }
}