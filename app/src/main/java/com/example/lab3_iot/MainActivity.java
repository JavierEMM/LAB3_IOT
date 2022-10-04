package com.example.lab3_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String,Object> listaMascotas = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btnRegistro)).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,RegistroActivity.class);
            intent.putExtra("listamascotas",listaMascotas);
            startActivity(intent);
        });
        ((Button) findViewById(R.id.btnHistorial)).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,HistorialActivity.class);
            intent.putExtra("listamascotas",listaMascotas);
            startActivity(intent);
        });
        ((Button) findViewById(R.id.btnEmergencia)).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,EmergenciaActivity.class);
            intent.putExtra("listamascotas",listaMascotas);
            startActivity(intent);
        });
    }

}