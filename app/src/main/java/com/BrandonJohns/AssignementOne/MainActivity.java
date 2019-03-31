package com.BrandonJohns.AssignementOne;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final double FEET_IN_METER = 3.2808;
    double length;
    double current;
    double area;
    double voltage;
    String units = "Metric";
    TextView displayVoltageDrop;
    TextView displayVoltageDropPercent;
    TextView displayWatts;
    EditText lengthInput;
    EditText currentInput;
    EditText voltageInput;
    Spinner spinner;
    TextView lengthText;
    ArrayAdapter<CharSequence> metricAdapter;
    ArrayAdapter<CharSequence> imperialAdapter;
    Calculator calculator;
    String formattedVoltageDrop;
    String formattedVoltageDropPercent;
    String formattedWatts;

    // TODO: 24/03/2019 test classes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayVoltageDrop = findViewById(R.id.voltage_drop);
        displayVoltageDropPercent = findViewById(R.id.voltage_drop_percent);
        displayWatts = findViewById(R.id.watts);
        lengthInput = findViewById(R.id.length);
        lengthText = findViewById(R.id.length_text);
        currentInput = findViewById(R.id.current);
        voltageInput = findViewById(R.id.voltage);
        spinner = findViewById(R.id.cableSpinner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            units = savedInstanceState.getString("units");
            formattedVoltageDrop = savedInstanceState.getString("voltageDrop");
            formattedVoltageDropPercent = savedInstanceState.getString("voltageDropPercent");
            formattedWatts = savedInstanceState.getString("watts");
            if (formattedWatts != null){ // TODO: 24/03/2019 fix this hack to address all the variables
                displayVoltageDrop.setText(formattedVoltageDrop);
                displayVoltageDropPercent.setText(formattedVoltageDropPercent);
                displayWatts.setText(formattedWatts);
            }
        }
        //metric
        metricAdapter = ArrayAdapter.createFromResource(this, R.array.metric_cable_sizes,
                android.R.layout.simple_spinner_item);
        metricAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //imperial
        imperialAdapter = ArrayAdapter.createFromResource(this,
                R.array.imperial_cable_sizes, android.R.layout.simple_spinner_item);
        imperialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("units", units);
        savedInstanceState.putString("watts", formattedWatts);
        savedInstanceState.putString("voltageDrop", formattedVoltageDrop);
        savedInstanceState.putString("voltageDropPercent", formattedVoltageDropPercent);


    }

    public void calculate(View view) {
        try {
            //get current input
            current = Double.parseDouble(currentInput.getText().toString());
            //get voltage input
            voltage = Double.parseDouble(voltageInput.getText().toString());
            //Metric inputs
            if (units.equals("Metric")) {
                //get length input
                length = Double.parseDouble(lengthInput.getText().toString());
                //cable choice
                spinner = findViewById(R.id.cableSpinner);
                String areaString = spinner.getSelectedItem().toString();
                areaString = areaString.replaceAll("mm²", "");
                area = Double.parseDouble(areaString);
            }
            //imperial inputs
            else {
                //cable choice
                int spinnerPos = spinner.getSelectedItemPosition();
                String[] sizeValues = getResources().getStringArray(R.array.imperial_cable_values);
                area = Double.parseDouble(sizeValues[spinnerPos]);
                length = Double.parseDouble(lengthInput.getText().toString()) / FEET_IN_METER;
            }
            calculator = new Calculator(length, current, area, voltage);
            calculator.calculate();

            formattedVoltageDrop = calculator.getFormattedVoltageDrop();
            formattedVoltageDropPercent = calculator.getFormattedVoltageDropPercent();
            formattedWatts = calculator.getFormattedWatts();

            displayVoltageDrop.setText(formattedVoltageDrop);
            displayVoltageDropPercent.setText(formattedVoltageDropPercent);
            displayWatts.setText(formattedWatts);
        } catch (NumberFormatException e) {
            if ("java.lang.NumberFormatException: empty String".equals(e.toString())) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Error empty input fields", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Error input must be numeric", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    public void reset(View view) {
        lengthInput.setText(null);
        currentInput.setText(null);
        voltageInput.setText(null);
        formattedVoltageDrop = getString(R.string.default_voltage_drop);
        formattedVoltageDropPercent = getString(R.string.default_voltage_drop_percent);
        formattedWatts = getString(R.string._0);
        displayVoltageDrop.setText(formattedVoltageDrop);
        displayVoltageDropPercent.setText(formattedVoltageDropPercent);
        displayWatts.setText(formattedWatts);
    }

    public void startSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("units", units);
        startActivityForResult(intent, SettingsActivity.SETTINGS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent unitSelection) {
        super.onActivityResult(requestCode, resultCode, unitSelection);

        if (requestCode == SettingsActivity.SETTINGS_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (unitSelection != null) {
                    units = unitSelection.getStringExtra("unitSelection");
                    updateUI();
                }
            }
        }
    }

    private void updateUI() {
        if (units.equals("Metric")) {
            lengthText.setText(getString(R.string.metric_length_text));
            spinner.setAdapter(metricAdapter);

        } else {
            lengthText.setText(getString(R.string.imperial_length_text));
            spinner.setAdapter(imperialAdapter);
        }
    }
}
