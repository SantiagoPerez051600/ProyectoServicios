package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoservicios.models.DogsResponse;
import com.example.proyectoservicios.models.DogsService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDog extends AppCompatActivity {
    private static final String TAG = "Dogs";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaDogsAdapter listaDogsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_dog);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.secondFragment);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] valores = {"affenpinscher","african","airedale","akita","appenzeller","basenji", "beagle", "bluetick"};



        ArrayAdapter<String> spinnerHead=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores);
        spinnerHead.setDropDownViewResource(R.layout.spiner_dropdown_item);
        spinner.setAdapter(spinnerHead);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLUE);
                 ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.DEFAULT);
                ((TextView) adapterView.getChildAt(0)).setTextSize(25);
                String raza = (String) adapterView.getItemAtPosition(position);
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://dog.ceo/api/breed/"+raza+"/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                obtenerDatos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });


    }
    private void obtenerDatos(){
        DogsService service = retrofit.create(DogsService.class);
        Call<DogsResponse> dogsResponse= service.obtenerListaDogs();

        dogsResponse.enqueue(new Callback<DogsResponse>() {
            @Override
            public void onResponse(Call<DogsResponse> call, Response<DogsResponse> response) {
                if(response.isSuccessful()){
                    DogsResponse dogsRespuesta = response.body();
                    ArrayList<String>listaDogs = dogsRespuesta.getMessage();
                    ArrayList<String>listaDogs6 = new ArrayList<>();



                    for( int i=0 ; i<7; i++){
                        Log.i(TAG, "Dog: "+listaDogs.get(i));
                        listaDogs6.add(listaDogs.get(i));
                    }
                    ListaDogsAdapter adapter = new ListaDogsAdapter(ApiDog.this, listaDogs);
                    RecyclerView recyclerView = findViewById(R.id.recycler);
                    recyclerView.setHasFixedSize(true);
                    GridLayoutManager layoutManager = new GridLayoutManager(ApiDog.this, 3);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);


                }else{
                    Log.e(TAG, " onResponse: "+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<DogsResponse> call, Throwable t) {
                Log.e(TAG, " onfailure: "+ t.getMessage());
            }
        });

    }
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.firstFragment:
                    Intent intent = new Intent(ApiDog.this, Listado.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.secondFragment:
                    intent = new Intent(ApiDog.this, ApiDog.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.thirdFragment:
                    intent = new Intent(ApiDog.this, preguntas_Frecuentes.class);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };
}