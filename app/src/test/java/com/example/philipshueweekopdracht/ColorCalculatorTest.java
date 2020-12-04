package com.example.philipshueweekopdracht;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ColorCalculatorTest extends TestCase {

    @Test
    public void testCalculateRGBColor() {
        int[] rgb = ColorCalculator.calculateRGBColor(3719,235,255);
        assertEquals(255, rgb[0]);
        assertEquals(100, rgb[1]);
        assertEquals(20, rgb[2]);
    }

    @Test
    public void testCalculateHSBColor() {
//        ColorCalculator.calculateHSBColor();
    }

    @Test
    public void testGetIntFromColor() {
//        ColorCalculator.getIntFromColor();
    }
}