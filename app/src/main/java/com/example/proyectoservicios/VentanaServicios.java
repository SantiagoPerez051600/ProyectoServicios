package com.example.proyectoservicios;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class VentanaServicios extends AppCompatActivity {
    LinearLayout contenedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_servicios);
        contenedor = (LinearLayout)findViewById(R.id.contenedor);
        ArrayList<boton> lista = new ArrayList<boton>();
        lista.add(new boton(1,"pelqueria"));
        lista.add(new boton(2,"pelqueria1"));
        lista.add(new boton(3,"pelqueria2"));

        for(boton b:lista){
            Button bt = new Button(getApplicationContext());
            bt.setText(b.servicio);
            bt.setId(b.codigo);
            bt.setTextColor(Color.BLACK);
            contenedor.addView(bt);
        }
    }
    class boton{
        public int codigo;
        public String servicio;
        public boton(int codigo, String servicio) {
            this.codigo = codigo;
            this.servicio = servicio;
        }
    }



}
