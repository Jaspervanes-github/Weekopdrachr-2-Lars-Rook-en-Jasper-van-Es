package com.example.philipshueweekopdracht;

import junit.framework.TestCase;

import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class LampTest extends TestCase {

    @Mock
    Lamp lamp;
    Lamp testLamp;

    @Before
    public void init() {
        lamp = new Lamp("1", "Test lamp", true, 200, 10, 20);
        testLamp = Mockito.mock(Lamp.class);
    }

    @Test
    public void testGetHueValue() {
        Mockito.when(testLamp.getHueValue()).thenReturn(357);
        Mockito.verify(testLamp);
        assertEquals(Mockito.anyInt(), testLamp.getHueValue());

    }

    @Test
    public void testSetHueValue() {
        testLamp.setHueValue(300);
        assertEquals(300, testLamp.getHueValue());
    }

    public void testGetSatValue() {
        assertEquals(96, testLamp.getSatValue());
    }

    public void testSetSatValue() {
        testLamp.setSatValue(300);
        assertEquals(300, testLamp.getSatValue());
    }

    public void testGetBriValue() {
        assertEquals(100, testLamp.getBriValue());
    }

    public void testSetBriValue() {
        testLamp.setBriValue(300);
        assertEquals(300, testLamp.getBriValue());
    }

    public void testGetLampID() {
    }

    public void testSetLampID() {
    }

    public void testGetNameLamp() {
    }

    public void testSetNameLamp() {
    }

    public void testIsPower() {
    }

    public void testSetPower() {
    }

    @Test
    public void testGetColorValueRed() {
        Mockito.when(testLamp.getColorValueRed()).thenReturn(lamp.getColorValueRed());
        assertEquals(255, testLamp.getColorValueRed());
    }

    public void testSetColorValueRed() {
    }

    public void testGetColorValueGreen() {
    }

    public void testSetColorValueGreen() {
    }

    public void testGetColorValueBlue() {
    }

    public void testSetColorValueBlue() {
    }

    public void testGetFadingSpeed() {
    }

    public void testSetFadingSpeed() {
    }

    public void testGetDiscoSpeed() {
    }

    public void testSetDiscoSpeed() {
    }

    public void testIsFadingMode() {
    }

    public void testSetFadingMode() {
    }

    public void testIsDiscoMode() {
    }

    public void testSetDiscoMode() {
    }
}