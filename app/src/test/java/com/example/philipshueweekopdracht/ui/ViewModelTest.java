package com.example.philipshueweekopdracht.ui;

import com.example.philipshueweekopdracht.Lamp;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;


@RunWith(MockitoJUnitRunner.class)
public class ViewModelTest extends TestCase {

    @Mock
    ViewModel viewModel;


    @Before
    public void init(){
        viewModel = new ViewModel();
    }

    @Test
    public void testGetLampSelected() {
        Lamp selectedLamp = viewModel.getLampSelected().getValue();
        assertTrue(selectedLamp instanceof Lamp);
    }

    @Test
    public void testGetLampSelectedWhenOneLampSet(){
        Lamp testLamp = new Lamp("1", "TestLamp1", true, 254, 0, 0, 0, 255, 255);
        viewModel.setLampSelected(testLamp);

        Lamp selectedLamp = viewModel.getLampSelected().getValue();
        assertEquals(testLamp, selectedLamp);
    }

    @Test
    public void testSetLampSelected() {

    }

    @Test
    public void testGetAllLamps() {


    }

    @Test
    public void testSetAllLamps() {


    }

    @Test
    public void testAddLamp() {


    }

    @Test
    public void testDeleteLamp() {


    }

    @Test
    public void testClearList() {


    }
}