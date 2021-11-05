package com.example.proyectoservicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.proyectoservicios.models.DogsResponse;
import com.example.proyectoservicios.models.DogsService;

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


        retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/breed/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();

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
}