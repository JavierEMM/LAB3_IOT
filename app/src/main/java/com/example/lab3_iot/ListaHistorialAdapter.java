package com.example.lab3_iot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3_iot.entity.Historial;
import com.example.lab3_iot.entity.Mascota;

import java.util.ArrayList;

public class ListaHistorialAdapter extends RecyclerView.Adapter<ListaHistorialAdapter.HistorialViewHolder> {

    private ArrayList<Historial> listaHistorial;
    private Context context;

    public void setListaHistorial(ArrayList<Historial> listaHistorial) {
        this.listaHistorial = listaHistorial;
    }

    class HistorialViewHolder extends RecyclerView.ViewHolder{
        Historial m;
        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv,parent,false); //item_rv=nombre del layout
        return new HistorialViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        Historial historial= listaHistorial.get(position);
        holder.m = historial;
        //cambiar segun el nombre de los textview del layout y falta agregar el atributo ruta
        TextView tvNombreMascota = holder.itemView.findViewById(R.id.mascota_rv);
        TextView tvGenero = holder.itemView.findViewById(R.id.genero_rv);
        TextView tvDuenho = holder.itemView.findViewById(R.id.dueno_rv);
        TextView tvDNI = holder.itemView.findViewById(R.id.dni_rv);
        TextView tvDescripcion = holder.itemView.findViewById(R.id.descripcion_rv);
        TextView tvRuta = holder.itemView.findViewById(R.id.ruta_rv);

        tvNombreMascota.setText(historial.getMascota().getNombre());
        tvGenero.setText(historial.getMascota().getGenero());
        tvDuenho.setText(historial.getMascota().getNombreDuenho());
        tvDNI.setText(String.valueOf(historial.getMascota().getDni()));
        tvDescripcion.setText(historial.getMascota().getDescripcion());
        tvRuta.setText((historial.getRuta() == null || historial.getRuta().equals("")) ? "-" : historial.getRuta());
    }

    @Override
    public int getItemCount() {
        return listaHistorial.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
