package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.proyectoservicios.models.Servicios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VentanaServicios extends AppCompatActivity {
    LinearLayout contenedor;
    private DatabaseReference Database;
    ArrayList<boton> lista = new ArrayList<boton>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_servicios);
        contenedor = (LinearLayout)findViewById(R.id.contenedor);
        Database = FirebaseDatabase.getInstance().getReference();
        //setServiciosFromFirebase();
        Database.child("servicios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for (DataSnapshot ds: snapshot.getChildren()) {

                        lista.add(new boton(1,ds.child("nombre").getValue().toString()));

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        for(boton b:lista){
            Button bt = new Button(getApplicationContext());
            bt.setText(b.servicio);
            bt.setId(b.codigo);
            bt.setTextColor(Color.BLACK);
            contenedor.addView(bt);
        }
    }
    private  void getServiciosFromFirebase(){

    }
    private  void setServiciosFromFirebase(){

        Database.child("servicios").push().child("nombre").setValue("Baño y peluquería");

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
