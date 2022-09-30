package com.example.lab3_iot.entity;

import java.io.Serializable;

public class Mascota implements Serializable{

    private String nombre;
    private int genero;
    private String nombreDuenho;
    private int dni;
    private String descripcion;

    public Mascota(String nombre, int genero, String nombreDuenho, int dni, String descripcion) {
        this.nombre = nombre;
        this.genero = genero;
        this.nombreDuenho = nombreDuenho;
        this.dni = dni;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public String getNombreDuenho() {
        return nombreDuenho;
    }

    public void setNombreDuenho(String nombreDuenho) {
        this.nombreDuenho = nombreDuenho;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
