package com.example.lab3_iot.entity;

import java.io.Serializable;

public class Historial implements Serializable{

    private Mascota mascota;
    private String ruta;

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
