package com.BrandonJohns.AssignementOne;

import android.view.View;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class calculatorTest {
    @Test
    public void calculateTest() {
        Calculator calculator = new Calculator(10, 100, 120, 12);
        calculator.calculate();
        assertEquals("0.14V", calculator.getFormattedVoltageDrop());
        assertEquals("1.18%", calculator.getFormattedVoltageDropPercent());
        assertEquals("1200.0W", calculator.getFormattedWatts());
        Calculator calculator1 = new Calculator(0, 100, 120, 12);
        calculator1.calculate();
        assertEquals("0.00V", calculator1.getFormattedVoltageDrop());
        assertEquals("1200.0W", calculator1.getFormattedWatts());
        assertEquals("0.00%", calculator1.getFormattedVoltageDropPercent());
        Calculator calculator2 = new Calculator(10, 100, 120, 0);
        calculator2.calculate();
        assertEquals("0.00V", calculator2.getFormattedVoltageDrop());
        assertEquals("0.0W", calculator2.getFormattedWatts());
        assertEquals("0.00%", calculator2.getFormattedVoltageDropPercent());
        Calculator calculator3 = new Calculator(10, 0, 120, 12);
        calculator2.calculate();
        assertEquals("0.00V", calculator3.getFormattedVoltageDrop());
        assertEquals("0.0W", calculator3.getFormattedWatts());
        assertEquals("0.00%", calculator3.getFormattedVoltageDropPercent());

    }
}