package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Administrador extends AppCompatActivity {
    private DatabaseReference Database;
    LinearLayout contenedor;
    public float tamaño=15.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        contenedor = findViewById(R.id.contenedor);
        Database = FirebaseDatabase.getInstance().getReference();
        Database.child("Solicitud de cita").addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<check> lista = new ArrayList<check>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        lista.add(new check(ds.child("Fecha").getValue().toString(),""+ds.child("Servicio").getValue().toString(),ds.child("Hora").getValue().toString()));
                    }
                    for(check b:lista){
                        CheckBox bt = new CheckBox(getApplicationContext());
                        bt.setText(b.servicio+" para "+b.fecha+" a las "+b.hora);
                        bt.setTextSize(tamaño);
                        bt.setTextColor(Color.BLACK);
                        contenedor.addView(bt);
                        bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            }
                        });

                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    class check{
        public String servicio;
        public String fecha;
        public String hora;
        public check(String fecha, String servicio, String hora) {
            this.servicio = servicio;
            this.fecha = fecha;
            this.hora= hora;
        }
    }

}