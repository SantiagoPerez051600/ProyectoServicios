package com.example.proyectoservicios;

import android.util.Log;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class DescriptionActivityTest extends TestCase {
    private DescriptionActivity oD;


    public void testValidarFechar() throws Exception{
            assertNotNull(oD.validarFechar(11,12,2021));
    }
}