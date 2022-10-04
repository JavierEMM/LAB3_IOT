package com.example.lab3_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.lab3_iot.entity.Mascota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListaMascotasAdapter adapter;

    ArrayList<Mascota> listaMascotas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent2 = getIntent();

        if(intent2.getSerializableExtra("lista") != null){
            listaMascotas = (ArrayList<Mascota>) intent2.getSerializableExtra("lista");
        }else{
            listaMascotas = new ArrayList<>();
        }
        ((Button) findViewById(R.id.btnRegistro)).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,RegistroActivity.class);
            intent.putExtra("lista",listaMascotas);
            startActivity(intent);
        });
        ((Button) findViewById(R.id.btnHistorial)).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,HistorialActivity.class);
            intent.putExtra("lista",listaMascotas);
            startActivity(intent);
        });
        ((Button) findViewById(R.id.btnEmergencia)).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,EmergenciaActivity.class);
            intent.putExtra("lista",listaMascotas);
            startActivity(intent);
        });
    }

}