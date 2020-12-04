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

    @Before
    public void init() {
        lamp = new Lamp("1", "Test lamp", true, 102, 51, 26, 20, 30, 40);
    }

    @Test
    public void testGetHueValue() {
        assertEquals(20, lamp.getHueValue());
    }

    @Test
    public void testSetHueValue() {
        lamp.setHueValue(300);
        assertEquals(300, lamp.getHueValue());
    }

    @Test
    public void testGetSatValue() {
        assertEquals(30, lamp.getSatValue());
    }

    @Test
    public void testSetSatValue() {
        lamp.setSatValue(300);
        assertEquals(300, lamp.getSatValue());
    }

    @Test
    public void testGetBriValue() {
        assertEquals(40, lamp.getBriValue());
    }

    @Test
    public void testSetBriValue() {
        lamp.setBriValue(300);
        assertEquals(300, lamp.getBriValue());
    }

    @Test
    public void testGetLampID() {
        assertEquals("1", lamp.getLampID());
    }

    @Test
    public void testSetLampID() {
        lamp.setLampID("3");
        assertEquals("3" ,lamp.getLampID());
    }

    @Test
    public void testGetNameLamp() {
        assertEquals("Test lamp", lamp.getNameLamp());
    }

    @Test
    public void testSetNameLamp() {
        lamp.setNameLamp("Slaapkamer");
        assertEquals("Slaapkamer", lamp.getNameLamp());
    }

    @Test
    public void testIsPower() {
        assertTrue(lamp.isPower());
    }

    @Test
    public void testSetPower() {
        lamp.setPower(false);
        assertFalse(lamp.isPower());
    }

    @Test
    public void testGetColorValueRed() {
       assertEquals(102, lamp.getColorValueRed());
    }

    @Test
    public void testSetColorValueRed() {
        lamp.setColorValueRed(150);
        assertEquals(150, lamp.getColorValueRed());
    }

    @Test
    public void testGetColorValueGreen() {
        assertEquals(51, lamp.getColorValueGreen());
    }

    @Test
    public void testSetColorValueGreen() {
        lamp.setColorValueGreen(200);
        assertEquals(200, lamp.getColorValueGreen());
    }

    @Test
    public void testGetColorValueBlue() {
        assertEquals(26, lamp.getColorValueBlue());
    }

    @Test
    public void testSetColorValueBlue() {
        lamp.setColorValueBlue(250);
        assertEquals(250, lamp.getColorValueBlue());
    }

    @Test
    public void testSetRGBValues() {
        lamp.setRGBValues(10,15,20);
        assertEquals(10, lamp.getColorValueRed());
        assertEquals(15, lamp.getColorValueGreen());
        assertEquals(20, lamp.getColorValueBlue());
    }
    @Test
    public void testGetFadingSpeed() {
        assertEquals(500, lamp.getFadingSpeed());
    }

    @Test
    public void testSetFadingSpeed() {
        lamp.setFadingSpeed(1000);
        assertEquals(1000, lamp.getFadingSpeed());
    }

    @Test
    public void testGetDiscoSpeed() {
        assertEquals(500, lamp.getDiscoSpeed());
    }

    @Test
    public void testSetDiscoSpeed() {
        lamp.setDiscoSpeed(1500);
        assertEquals(1500, lamp.getDiscoSpeed());
    }

    @Test
    public void testIsFadingMode() {
        assertFalse(lamp.isFadingMode());
    }

    @Test
    public void testSetFadingMode() {
        lamp.setFadingMode(true);
        assertTrue(lamp.isFadingMode());
    }

    @Test
    public void testIsDiscoMode() {
        assertFalse(lamp.isDiscoMode());
    }

    @Test
    public void testSetDiscoMode() {
        lamp.setDiscoMode(true);
        assertTrue(lamp.isDiscoMode());
    }
}