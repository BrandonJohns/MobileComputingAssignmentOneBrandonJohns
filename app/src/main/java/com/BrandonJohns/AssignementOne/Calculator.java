package com.BrandonJohns.AssignementOne;

import java.util.Locale;

class Calculator {
    private static final double WIRE_RESISTANCE = 0.017;
    private double length;
    private double current;
    private double area;
    private double voltage;
    private double voltageDrop;
    private double voltageDropPercent;
    private double watts;


    Calculator(double length, double current, double area, double voltage) {
        this.length = length;
        this.current = current;
        this.area = area;
        this.voltage = voltage;

    }
    void calculate(){
        this.voltageDrop = length * current * WIRE_RESISTANCE / area;
        this.voltageDropPercent = voltageDrop / voltage * 100;
        this.watts = current * voltage;
    }
    String getFormattedVoltageDrop(){
        return String.format(Locale.getDefault(), "%.2fV",
                voltageDrop);
    }
    String getFormattedVoltageDropPercent(){
        return String.format(Locale.getDefault(), "%.2f%%",
                voltageDropPercent);
    }
    String getFormattedWatts(){
        return String.format(Locale.getDefault(), "%.1fW", watts);
    }
}
