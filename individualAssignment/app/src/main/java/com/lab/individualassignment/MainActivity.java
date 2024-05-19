package com.lab.individualassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCalculate, btnClear;
    EditText etUnitsLabel, etRebateLabel;
    TextView textTotal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        etUnitsLabel = findViewById(R.id.etUnitsLabel);
        etRebateLabel = findViewById(R.id.etRebateLabel);
        textTotal = findViewById(R.id.textTotal);

        btnCalculate.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();

        if (selected == R.id.menuAbout) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == btnCalculate) {
            String strUnitsL = etUnitsLabel.getText().toString();
            String strRebateL = etRebateLabel.getText().toString();
            DecimalFormat df = new DecimalFormat("0.00");
            double unitsL, rebateL;
            double charges = 0;
            double total = 0;
            try {
                unitsL = Double.parseDouble(strUnitsL);
                rebateL = Double.parseDouble(strRebateL);

                if (unitsL <= 200) {
                    charges = unitsL * 0.218;
                } else if (unitsL <= 300) {
                    charges = (200 * 0.218) + ((unitsL - 200) * 0.334);
                } else if (unitsL <= 600) {
                    charges = (200 * 0.218) + (100 * 0.334) + ((unitsL - 300) * 0.516);
                } else {
                    charges = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((unitsL - 600) * 0.546);
                }

                total = charges - (charges * rebateL / 100);

            } catch (NumberFormatException nfe) {
                Toast.makeText(this, "Please enter valid numbers in the input fields", Toast.LENGTH_SHORT).show();
                return;
            } catch (Exception exception) {
                Toast.makeText(this, "An error occurred, please try again", Toast.LENGTH_SHORT).show();
                return;
            }
            textTotal.setText("Total Bills: RM " + df.format(total));

        } else if (view == btnClear) {
            etUnitsLabel.setText("");
            etRebateLabel.setText("");
            textTotal.setText("");
        }
    }
}

