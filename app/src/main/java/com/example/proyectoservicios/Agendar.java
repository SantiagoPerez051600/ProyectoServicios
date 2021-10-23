package com.example.proyectoservicios;

import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.example.proyectoservicios.models.Servicios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Agendar {
    public DatabaseReference Database;

    public void crearCita(String fecha, String hora,String servicio,String email){
        Database = FirebaseDatabase.getInstance().getReference();
        Map<String,String> citas = new HashMap<>();
        citas.put("Fecha",""+fecha);
        citas.put("Hora",""+hora);
        citas.put("Servicio",""+servicio);
        citas.put("Email",""+email);
        Database.child("Solicitud de cita").push().setValue(citas);
    }
    public void aceptarCita(String correo, String servicio, String fecha,String hora){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref= database.getReference();
        Query query = ref.getParent();
        Database = FirebaseDatabase.getInstance().getReference();
        Database.child("Solicitud de cita").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        if(ds.child("Email").getValue().toString().equals(correo) && ds.child("Fecha").getValue().toString().equals(fecha) && ds.child("Servicio").getValue().toString().equals(servicio) ){
                            String sKey = ds.getKey();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref= database.getReference("Solicitud de cita").child(sKey);
                            ref.removeValue();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Database = FirebaseDatabase.getInstance().getReference();
        Map<String,String> citas = new HashMap<>();
        citas.put("Fecha",""+fecha);
        citas.put("Hora",""+hora);
        citas.put("Servicio",""+servicio);
        citas.put("Email",""+correo);
        Database.child("Citas Aceptadas").push().setValue(citas);
    }

    public void rechazarCita(String correo, String servicio, String fecha,String hora){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref= database.getReference();
        Query query = ref.getParent();
        Database = FirebaseDatabase.getInstance().getReference();
        Database.child("Solicitud de cita").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        if(ds.child("Email").getValue().toString().equals(correo) && ds.child("Fecha").getValue().toString().equals(fecha) && ds.child("Servicio").getValue().toString().equals(servicio) ){
                            String sKey = ds.getKey();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref= database.getReference("Solicitud de cita").child(sKey);
                            ref.removeValue();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Database = FirebaseDatabase.getInstance().getReference();
        Map<String,String> citas = new HashMap<>();
        citas.put("Fecha",""+fecha);
        citas.put("Hora",""+hora);
        citas.put("Servicio",""+servicio);
        citas.put("Email",""+correo);
        Database.child("Citas Rechazadas").push().setValue(citas);
    }


}
