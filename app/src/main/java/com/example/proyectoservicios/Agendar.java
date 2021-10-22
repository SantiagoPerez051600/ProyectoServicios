package com.example.proyectoservicios;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Agendar {
    public DatabaseReference Database;

    public void crearCita(String fecha, String hora,String servicio){
        Database = FirebaseDatabase.getInstance().getReference();
        Map<String,String> citas = new HashMap<>();
        citas.put("Fecha",""+fecha);
        citas.put("Hora",""+hora);
        citas.put("Servicio",""+servicio);
        Database.child("Solicitud de cita").push().setValue(citas);
    }
    public void rechazarCita(){

    }


}
