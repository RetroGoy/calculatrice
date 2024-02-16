package com.example.calculatrice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private int op1 = 0;
    private int op2 = 0;
    private Ops operator = null;
    private boolean isOp1 = true;

    public enum Ops {
        PLUS,
        MOINS,
        FOIS,
        DIV;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.screen);

        Button btnEgal = findViewById(R.id.btnEgal);
        btnEgal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
            }
        });

        for (int i = 1; i <= 9; i++) {
            int buttonId = getResources().getIdentifier("btn" + i, "id", getPackageName());
            Button digitButton = findViewById(buttonId);
            digitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNumber(v);
                }
            });
        }
    }

    public void setOperator(View v) {
        if (v.getId() == R.id.btnPlus) {
            operator = Ops.PLUS;
        } else if (v.getId() == R.id.btnMoins) {
            operator = Ops.MOINS;
        } else if (v.getId() == R.id.btnFois) {
            operator = Ops.FOIS;
        } else if (v.getId() == R.id.btnDiv) {
            operator = Ops.DIV;
        } else {
            Toast.makeText(this, "Opérateur non reconnu", Toast.LENGTH_LONG).show();
            return;
        }

        isOp1 = false;
        updateDisplay();
    }


    public void addNumber(View v) {
        try {
            int val = Integer.parseInt(((Button) v).getText().toString());
            if (isOp1) {
                op1 = op1 * 10 + val;
                updateDisplay();
            } else {
                op2 = op2 * 10 + val;
                updateDisplay();
            }
        } catch (NumberFormatException | ClassCastException e) {
            Toast.makeText(this, "Valeur erronée", Toast.LENGTH_LONG).show();
        }
    }

    public void compute() {
        if (!isOp1) {
            if (operator == Ops.PLUS) {
                op1 = op1 + op2;
            } else if (operator == Ops.MOINS) {
                op1 = op1 - op2;
            } else if (operator == Ops.FOIS) {
                op1 = op1 * op2;
            } else if (operator == Ops.DIV) {
                if (op2 != 0) {
                    op1 = op1 / op2;
                } else {
                    Toast.makeText(this, "Division par zéro", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                return;
            }
            op2 = 0;
            isOp1 = true;
            updateDisplay();
        }
    }


    private void updateDisplay() {
        int v = isOp1 ? op1 : op2;
        screen.setText(String.format("%9d", v));
    }

    public void resetCalculator(View v) {
        op1 = 0;
        op2 = 0;
        operator = null;
        isOp1 = true;
        updateDisplay();
    }
}