package com.example.proyectoservicios;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoservicios.models.Servicios;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Database = FirebaseDatabase.getInstance().getReference();
        init();
    }

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

                        elements.add(new Servicios(nombre,descripcion,"#000000",precio));
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
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("Servicio", item);
        startActivity(intent);
    }

}
