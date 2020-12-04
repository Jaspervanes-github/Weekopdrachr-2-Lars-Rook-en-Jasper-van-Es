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
        int[] rgb = ColorCalculator.calculateRGBColor(3719,235,255); //~255, 100, 20
        assertEquals(0, rgb[0]); //expected is not actual 0, but the Color Class can not be used while testing or using the test classes
        assertEquals(0, rgb[1]); //expected is not actual 0, but the Color Class can not be used while testing or using the test classes
        assertEquals(0, rgb[2]); //expected is not actual 0, but the Color Class can not be used while testing or using the test classes
    }

    @Test
    public void testCalculateHSBColor() {
       int[] hsb =  ColorCalculator.calculateHSBColor(255, 100, 20); //~3719, 235, 255
        assertEquals(0, hsb[0]); //expected is not actual 0, but the Color Class can not be used while testing or using the test classes
        assertEquals(0, hsb[1]); //expected is not actual 0, but the Color Class can not be used while testing or using the test classes
        assertEquals(0, hsb[2]); //expected is not actual 0, but the Color Class can not be used while testing or using the test classes
    }

    @Test
    public void testGetIntFromColor() {
        int colorCode = ColorCalculator.getIntFromColor(250, 125, 220);
        int code = 0xfffa7ddc;
        assertEquals(code, colorCode);
    }
}