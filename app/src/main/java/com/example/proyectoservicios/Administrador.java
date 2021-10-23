package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

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

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import io.grpc.SynchronizationContext;

public class Administrador extends AppCompatActivity {
    ImageButton imageCerrar;
    Button aceptar;
    Button rechazar;
    private DatabaseReference Database;
    LinearLayout contenedor;
    public float tamaño=15.0f;
    public ArrayList<check> lista = new ArrayList<check>();
    public ArrayList<check> lista2 = new ArrayList<check>();
    EnvioDeCorreos e = new EnvioDeCorreos();
    Agendar agendar = new Agendar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        contenedor = findViewById(R.id.contenedor);
        imageCerrar= findViewById(R.id.cerarSesion);
        aceptar = findViewById(R.id.btn_aceptar);
        rechazar = findViewById(R.id.btn_rechazar);
        Database = FirebaseDatabase.getInstance().getReference();
        imageCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Administrador.this, loginActivity.class);
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

                if(snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        lista.add(new check(ds.child("Fecha").getValue().toString(),""+ds.child("Servicio").getValue().toString(),ds.child("Hora").getValue().toString(),ds.child("Email").getValue().toString()));
                    }
                    for(check b:lista){
                        CheckBox bt = new CheckBox(getApplicationContext());
                        bt.setText(b.servicio+" para "+b.fecha+" a las "+b.hora);
                        bt.setTextSize(tamaño);
                        bt.setTextColor(Color.BLACK);

                        contenedor.addView(bt);
                        bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean x) {
                                if(bt.isChecked()==true){
                                    lista2.add(b);
                                }else if(bt.isChecked()==false){
                                    lista2.remove(b);
                                }
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
        public String email;
        public check(String fecha, String servicio, String hora,String email) {
            this.servicio = servicio;
            this.fecha = fecha;
            this.hora= hora;
            this.email= email;

        }
    }
    public void aceptar(){
        for(check cb: lista2){
            e.aceptarOrechazarAgenda(cb.servicio,cb.email,"SOLICITUD DE AGENDAMIENTO ACEPTADA","Su solicutud de agendamiento fue aceptada," +
                    "\npor favor llegar con 10 minutos de anticipacion," +
                    "\nen caso de querer cancelar la cita, hacerlo 2 horas antes de la hora programada.");
            agendar.aceptarCita(cb.email,cb.servicio, cb.fecha, cb.hora);
        }
    }
    public void rechazar(){
        EnvioDeCorreos e = new EnvioDeCorreos();
        for(check cb: lista2){
            e.aceptarOrechazarAgenda(cb.servicio,cb.email,"SOLICITUD DE AGENDAMIENTO RECHAZADA","Su solicutud de agendamiento fue rechazada" +
                    "\nsi tiene preguntas por favor comunicarse via WhatsApp," +
                    "\nel vinculo aparece en el apartado de agendamiento.");
            agendar.rechazarCita(cb.email,cb.servicio, cb.fecha, cb.hora);
        }
    }
}