package com.example.lab3_iot.entity;

import java.util.ArrayList;

public class listaMascotas {

    public static ArrayList<Mascota> listaMascotas= new ArrayList<>();

    public static ArrayList<Mascota> getListaMascotas(){return listaMascotas;}

    private static String genero(int iterar){
        String genero[] = new String[]{
                "Macho","Hembra"
        };
        return genero[iterar];
    }

    public static void anadirMascota(Mascota mascota){
        listaMascotas.add(mascota);
    }

    public static ArrayList<String> MascotaVistaPrincipal(){
        ArrayList<String> listaparametros=new ArrayList<>();
        String parametros = "";
        for(Mascota mascota: listaMascotas){
            parametros="";
            parametros+="Nombre: "+mascota.getNombre()+"\n";
            parametros+="Genero: "+genero(mascota.getGenero()+0)+"\n";
            parametros+="Nombre Dueño: "+mascota.getNombreDuenho()+"\n";
            parametros+="DNI: "+mascota.getDni()+"\n";
            parametros+="Descripcion: "+mascota.getDescripcion()+"\n";
            listaparametros.add(parametros);
        }
        return listaparametros;
    }

}
