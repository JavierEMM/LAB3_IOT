package com.example.lab3_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lab3_iot.entity.Historial;
import com.example.lab3_iot.entity.Mascota;
import com.example.lab3_iot.entity.listaMascotas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    public ArrayList<Mascota> mascotas;
    String genero;
    ArrayList<Historial> historials;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Intent intent = getIntent();
        if(intent.getSerializableExtra("lista") != null){
            mascotas = (ArrayList<Mascota>) intent.getSerializableExtra("lista");
            Log.d("msg","EXISTE MASCOTAS");
            for(Mascota mascota : mascotas){
                Log.d("msg", String.valueOf(mascota.getDni()));
            }
        }else{
            mascotas = new ArrayList<>();
            Log.d("msg","NO EXISTE MASCOTAS");
        }
        if(intent.getSerializableExtra("historial") != null){
            historials = (ArrayList<Historial>) intent.getSerializableExtra("historial");
        }else{
            historials = new ArrayList<>();
        }
        List<String> genderSpinner = new ArrayList<>();
        genderSpinner.add("F-M");
        genderSpinner.add("F");
        genderSpinner.add("M");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,genderSpinner);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        ((Button) findViewById(R.id.btnregistrar)).setOnClickListener(view -> {
            returnNuevaMascota();
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String opcionSpinner = spinner.getSelectedItem().toString();
                if (opcionSpinner == "F-M") {
                    genero = "-";
                } else {
                    genero = opcionSpinner;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                genero = "-";
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            Intent intent1 = new Intent(RegistroActivity.this,MainActivity.class);
            intent1.putExtra("lista", (Serializable) mascotas);
            intent1.putExtra("historial",(Serializable) historials);
            startActivity(intent1);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void returnNuevaMascota(){
        EditText nombre = findViewById(R.id.nombre);
        EditText nombreDuenho = findViewById(R.id.nombreDuenho);
        EditText dni = findViewById(R.id.dni);
        EditText descripcion = findViewById(R.id.descripcion);
        boolean guardar = true;

        if (nombre.getText().toString().equals("") || nombre.getText().toString() == null) {
            nombre.setError("Ingrese el nombre de la mascota");
            guardar = false;
        }
        if (nombreDuenho.getText().toString().equals("") || nombreDuenho.getText().toString() == null) {
            nombreDuenho.setError("Ingrese el nombre del dueño");
            guardar = false;
        }
        if (dni.getText().toString().equals("") || dni.getText().toString() == null) {
            dni.setError("Ingrese el DNI del dueño");
            guardar = false;
        } else if (dni.getText().toString().length() != 8) {
            dni.setError("Ingrese un DNI válido");
            guardar = false;
        }
        if (descripcion.getText().toString().equals("") || descripcion.getText().toString() == null) {
            descripcion.setError("Ingrese una descripción");
            guardar = false;
        }

        if (guardar) {
            Mascota mascota = new Mascota(nombre.getText().toString(),genero,nombreDuenho.getText().toString(),Integer.parseInt(dni.getText().toString()),descripcion.getText().toString());
            mascotas.add(mascota);

            Intent intent = new Intent(RegistroActivity.this,MainActivity.class);
            intent.putExtra("lista", mascotas);
            intent.putExtra("historial", historials);
            startActivity(intent);
        }
    }



}