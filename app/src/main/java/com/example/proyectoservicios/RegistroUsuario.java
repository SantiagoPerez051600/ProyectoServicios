package com.example.proyectoservicios;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;

public class RegistroUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        final ImageView imageView= findViewById(R.id.imagen);
        imageView.setColorFilter(Color.parseColor("#3E64FF"), PorterDuff.Mode.SRC_IN);
    }
}