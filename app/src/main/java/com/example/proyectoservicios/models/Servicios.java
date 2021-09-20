package com.example.proyectoservicios.models;

public class Servicios {

    String nombre, descripcion, URLfoto, precio;

    public Servicios() {
    }

    public Servicios(String nombre, String descripcion, String URLfoto, String precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.URLfoto = URLfoto;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getURLfoto() {
        return URLfoto;
    }

    public void setURLfoto(String URLfoto) {
        this.URLfoto = URLfoto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
