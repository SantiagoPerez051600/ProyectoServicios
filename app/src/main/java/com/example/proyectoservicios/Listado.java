package com.example.proyectoservicios;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoservicios.models.Servicios;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Listado extends AppCompatActivity {
    List<Servicios> elements;
    private DatabaseReference Database;

    ImageButton imageCerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Database = FirebaseDatabase.getInstance().getReference();
        imageCerrar= findViewById(R.id.cerarSesion);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        imageCerrar.setColorFilter(Color.parseColor("#3E64FF"), PorterDuff.Mode.SRC_IN);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        imageCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Listado.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        init();
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.firstFragment:
                    Intent intent = new Intent(Listado.this, Listado.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.secondFragment:
                     intent = new Intent(Listado.this, ApiDog.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.thirdFragment:
                    intent = new Intent(Listado.this, preguntas_Frecuentes.class);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };
    public void init(){
        Database.child("servicios").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    elements =  new ArrayList<>();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        String nombre = ds.child("nombre").getValue().toString();
                        String descripcion= ds.child("Descripcion").getValue().toString();
                        String precio = ds.child("precio").getValue().toString();
                        String URL = ds.child("URL").getValue().toString();
                        elements.add(new Servicios(nombre,descripcion,URL,precio));
                    }
                    CardsAdapter cardsAdapter = new CardsAdapter(elements, Listado.this, new CardsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Servicios item) {
                            moveToDescription(item);
                        }
                    });
                    RecyclerView recyclerView = findViewById(R.id.list_recycler);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Listado.this));
                    recyclerView.setAdapter(cardsAdapter);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void moveToDescription(Servicios item){
        if(item.getNombre().equals("Vacunas") || item.getNombre().equals("Urgencia") || item.getNombre().equals("Labaratorio clinico") || item.getNombre().equals("Consulta Medica General")){
            Intent intent = new Intent(this, descripcion_vacuna.class);
            intent.putExtra("Servicio", item);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, DescriptionActivity.class);
            intent.putExtra("Servicio", item);
            startActivity(intent);
        }

    }

}
