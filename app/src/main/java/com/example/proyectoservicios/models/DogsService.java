package com.example.proyectoservicios.models;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogsService {
    @GET("images")
    Call<DogsResponse>obtenerListaDogs();

}
