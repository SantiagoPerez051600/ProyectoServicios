package com.example.proyectoservicios.models;

import java.util.ArrayList;

public class DogsResponse {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }

    private ArrayList<String> message ;



}
