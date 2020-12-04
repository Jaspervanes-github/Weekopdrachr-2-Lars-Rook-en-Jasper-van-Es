package com.example.philipshueweekopdracht.ui;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


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