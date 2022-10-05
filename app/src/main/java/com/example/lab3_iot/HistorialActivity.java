package com.example.lab3_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lab3_iot.entity.Historial;
import com.example.lab3_iot.entity.Mascota;

import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity {

    ArrayList<Mascota> mascotas;
    ArrayList<Historial> historials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Intent intent = getIntent();
        if(intent.getSerializableExtra("lista") != null){
            mascotas = (ArrayList<Mascota>) intent.getSerializableExtra("lista");
        }else{
            mascotas = new ArrayList<>();
        }
        if(intent.getSerializableExtra("historial") != null){
            historials = (ArrayList<Historial>) intent.getSerializableExtra("historial");
        }else{
            historials = new ArrayList<>();
        }
        ListaHistorialAdapter listaHistorialAdapter = new ListaHistorialAdapter();
        listaHistorialAdapter.setListaHistorial(historials);
        listaHistorialAdapter.setContext(HistorialActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setAdapter(listaHistorialAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistorialActivity.this));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            Intent intent1 = new Intent(HistorialActivity.this,MainActivity.class);
            intent1.putExtra("lista", mascotas);
            intent1.putExtra("historial", historials);
            startActivity(intent1);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}