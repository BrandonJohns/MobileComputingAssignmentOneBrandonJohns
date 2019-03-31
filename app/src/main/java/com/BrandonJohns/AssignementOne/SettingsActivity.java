package com.BrandonJohns.AssignementOne;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends Activity {
    static final int SETTINGS_REQUEST = 1;
    RadioGroup unitsRadioGroup;
    RadioButton selectedRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();
        String currentUnits = intent.getStringExtra("units");

        if (currentUnits.equals("Metric")) {
            RadioButton metricButton = findViewById(R.id.radio_metric);
            metricButton.setChecked(true);
        }

        else {
            RadioButton imperialButton = findViewById(R.id.radio_imperial);
            imperialButton.setChecked(true);
        }
    }

    public void saveSettings(View view) {
        unitsRadioGroup = findViewById(R.id.unitSelection);
        int selection = unitsRadioGroup.getCheckedRadioButtonId();
        selectedRadioButton = findViewById(selection);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("unitSelection", selectedRadioButton.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void exitToMain(View view) {
        finish();
    }

    public void startSettingsActivity(View view) {
        finish();
    }
}
