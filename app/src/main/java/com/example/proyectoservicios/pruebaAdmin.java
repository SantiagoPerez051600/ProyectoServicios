package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.proyectoservicios.models.Servicios;
import com.example.proyectoservicios.models.Solicitudes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class pruebaAdmin extends AppCompatActivity {
    ImageButton imageCerrar;
    Button aceptar;
    Button rechazar;
    private DatabaseReference Database;
    LinearLayout contenedor;
    public float tamaño = 15.0f;
    public ArrayList<Solicitudes> lista2 = new ArrayList<Solicitudes>();
    EnvioDeCorreos e = new EnvioDeCorreos();
    Agendar agendar = new Agendar();
    List<Solicitudes> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_admin);
        contenedor = findViewById(R.id.contenedor);
        imageCerrar = findViewById(R.id.cerarSesion);
        aceptar = findViewById(R.id.btn_aceptar);
        rechazar = findViewById(R.id.btn_rechazar);
        Database = FirebaseDatabase.getInstance().getReference();


        imageCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(pruebaAdmin.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aceptar();
            }
        });
        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rechazar();
            }
        });
        Database.child("Solicitud de cita").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    elements = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        String servi = ds.child("Servicio").getValue().toString();
                        String fecha = ds.child("Fecha").getValue().toString();
                        String hora = ds.child("Hora").getValue().toString();
                        String correo = ds.child("Email").getValue().toString();

                        elements.add(new Solicitudes(servi, fecha, hora, correo));
                    }

                    AdaptadorAdmin Adapter = new AdaptadorAdmin(elements, pruebaAdmin.this);
                    RecyclerView recyclerView = findViewById(R.id.list_recycler);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(pruebaAdmin.this));
                    recyclerView.setAdapter(Adapter);
                    lista2 = Adapter.prueba();
                }else{
                    elements = new ArrayList<>();
                    AdaptadorAdmin Adapter = new AdaptadorAdmin(elements, pruebaAdmin.this);
                    RecyclerView recyclerView = findViewById(R.id.list_recycler);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(pruebaAdmin.this));
                    recyclerView.setAdapter(Adapter);
                    lista2 = Adapter.prueba();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void aceptar() {


        for (Solicitudes cb : lista2) {
            e.aceptarOrechazarAgenda(cb.getServi(), cb.getCorreo(), "SOLICITUD DE AGENDAMIENTO ACEPTADA", "Su solicutud de agendamiento fue aceptada," +
                    "\npor favor llegar con 10 minutos de anticipacion," +
                    "\nen caso de querer cancelar la cita, hacerlo 2 horas antes de la hora programada.");
            agendar.aceptarCita(cb.getCorreo(), cb.getServi(), cb.getFecha(), cb.getHora());
        }
    }

    public void rechazar() {
        EnvioDeCorreos e = new EnvioDeCorreos();
        for (Solicitudes cb : lista2) {
            e.aceptarOrechazarAgenda(cb.getServi(), cb.getCorreo(), "SOLICITUD DE AGENDAMIENTO RECHAZADA", "Su solicutud de agendamiento fue rechazada" +
                    "\nsi tiene preguntas por favor comunicarse via WhatsApp," +
                    "\nel vinculo aparece en el apartado de agendamiento.");
            agendar.rechazarCita(cb.getCorreo(), cb.getServi(), cb.getFecha(), cb.getHora());
        }
    }
}