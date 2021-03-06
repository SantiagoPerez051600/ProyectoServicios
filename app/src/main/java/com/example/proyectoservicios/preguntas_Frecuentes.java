package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class preguntas_Frecuentes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_frecuentes);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.thirdFragment);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.firstFragment:
                    Intent intent = new Intent(preguntas_Frecuentes.this, Listado.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.secondFragment:
                    intent = new Intent(preguntas_Frecuentes.this, ApiDog.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.thirdFragment:
                    intent = new Intent(preguntas_Frecuentes.this, preguntas_Frecuentes.class);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };
}