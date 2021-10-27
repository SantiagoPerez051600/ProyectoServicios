package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VentanaServicios extends AppCompatActivity {
    LinearLayout contenedor;
    private DatabaseReference Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_servicios);
        contenedor = findViewById(R.id.contenedor);
        Database = FirebaseDatabase.getInstance().getReference();
        ArrayList<boton> lista = new ArrayList<boton>();
        //setServiciosFromFirebase();

        Database.child("servicios").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        lista.add(new boton(1,""+ds.child("nombre").getValue().toString(),ds.getKey()));
                    }
                    for(boton b:lista){
                        Button bt = new Button(getApplicationContext());
                        bt.setText(b.servicio);
                        bt.setId(b.codigo);
                        bt.setTextColor(Color.BLACK);
                        contenedor.addView(bt);
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String aux;
                                aux = bt.getText().toString();
                                Log.d("STATE",""+aux);
                                siguiente(view, b.key);


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
    public void siguiente(View view , String key){
        Bundle parametro = new Bundle();
        parametro.putString("key",key);
        Intent siguiente = new Intent(this, Servicio.class);
        siguiente.putExtras(parametro);
        startActivity(siguiente);

    }
    private  void getServiciosFromFirebase(){

    }
    private  void setServiciosFromFirebase(){
        Map<String,String>servicios = new HashMap<>();
        servicios.put("nombre","Vacunas");
        servicios.put("precio","$30.000");
        servicios.put("Descripcion","El precio varia segun la vacuna que se requiera para el animal");
        Database.child("servicios").push().setValue(servicios);
        ///////////////////////////////////////////
        Map<String,String>servicios1 = new HashMap<>();
        servicios1.put("nombre","Peluqueria");
        servicios1.put("precio","$45.000");
        servicios1.put("Descripcion","Se realiza todo tipo de corte segun el pelo de su mascota");
        Database.child("servicios").push().setValue(servicios1);
        ///////////////////////////////////////////
        Map<String,String>servicios2 = new HashMap<>();
        servicios2.put("nombre","Corte De Uñas");
        servicios2.put("precio","$5.000");
        servicios2.put("Descripcion","Se corta y se liman");
        Database.child("servicios").push().setValue(servicios2);
        ///////////////////////////////////////////
        Map<String,String>servicios3 = new HashMap<>();
        servicios3.put("nombre","Urgencia");
        servicios3.put("precio","$0");
        servicios3.put("Descripcion","El precio final depende del procedimiento realizado en la mascota");
        Database.child("servicios").push().setValue(servicios3);
        ///////////////////////////////////////////
        Map<String,String>servicios4 = new HashMap<>();
        servicios4.put("nombre","Labaratorio clinico");
        servicios4.put("precio","$0");
        servicios4.put("Descripcion","El precio final depende del procedimiento realizado en la mascota");
        Database.child("servicios").push().setValue(servicios4);
        ///////////////////////////////////////////
        Map<String,String>servicios5 = new HashMap<>();
        servicios5.put("nombre","Consulta Medica General");
        servicios5.put("precio","$45.000");
        servicios5.put("Descripcion","Pago unica vez");
        Database.child("servicios").push().setValue(servicios5);
        ///////////////////////////////////////////
        Map<String,String>servicios6 = new HashMap<>();
        servicios6.put("nombre","Desparacitacion");
        servicios6.put("precio","$20.000");
        servicios6.put("Descripcion","Dependiendo del peso de la mascota se suministra la cantidad");
        Database.child("servicios").push().setValue(servicios6);
        ///////////////////////////////////////////
        Map<String,String>servicios7 = new HashMap<>();
        servicios7.put("nombre","Baño");
        servicios7.put("precio","$40.000");
        servicios7.put("Descripcion","El baño incluye corte de uñas y limpieza de oidos");
        Database.child("servicios").push().setValue(servicios7);
        ///////////////////////////////////////////
        Map<String,String>servicios8 = new HashMap<>();
        servicios8.put("nombre","Radiologia");
        servicios8.put("precio","$80.000");
        servicios8.put("Descripcion","Se realiza un procedimiento de rayos x");
        Database.child("servicios").push().setValue(servicios8);
        ///////////////////////////////////////////
        Map<String,String>servicios9 = new HashMap<>();
        servicios9.put("nombre","Odontologia");
        servicios9.put("precio","$50.000");
        servicios9.put("Descripcion","El procedimiento incluye la anestecia");
        Database.child("servicios").push().setValue(servicios9);
        ///////////////////////////////////////////

    }
    class boton{
        public int codigo;
        public String servicio;
        public String key;
        public boton(int codigo, String servicio, String key) {
            this.codigo = codigo;
            this.servicio = servicio;
            this.key= key;
        }
    }



}
