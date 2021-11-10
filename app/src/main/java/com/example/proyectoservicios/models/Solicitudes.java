package com.example.proyectoservicios.models;

public class Solicitudes {
    public String servi, fecha, hora, correo;

    public Solicitudes(String servi, String fecha, String hora, String correo) {
        this.servi = servi;
        this.fecha = fecha;
        this.hora = hora;
        this.correo = correo;
    }

    public String getServi() {
        return servi;
    }

    public void setServi(String servi) {
        this.servi = servi;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


}
