package com.example.lab3_iot.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3_iot.R;

public class ListaMascotasAdapter extends RecyclerView.Adapter<ListaMascotasAdapter.MascotaViewHolder> {

    private Mascota[] listaMascotas;
    private Context context;

    class MascotaViewHolder extends RecyclerView.ViewHolder{
        Mascota m;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv,parent,false); //item_rv=nombre del layout
        return new MascotaViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        Mascota mascota = listaMascotas[position];
        holder .m = mascota;
        //cambiar segun el nombre de los textview del layout y falta agregar el atributo ruta
        TextView tvNombreMascota = holder.itemView.findViewById(R.id.mascota_rv);
        TextView tvGenero = holder.itemView.findViewById(R.id.genero_rv);
        TextView tvDuenho = holder.itemView.findViewById(R.id.dueno_rv);
        TextView tvDNI = holder.itemView.findViewById(R.id.dni_rv);
        TextView tvDescripcion = holder.itemView.findViewById(R.id.descripcion_rv);
        TextView tvRuta = holder.itemView.findViewById(R.id.ruta_rv);

        tvNombreMascota.setText(mascota.getNombreDuenho());
        tvGenero.setText(mascota.getGenero());
        tvDuenho.setText(mascota.getNombreDuenho());
        tvDNI.setText(mascota.getDni());
        tvDescripcion.setText(mascota.getDescripcion());
        //tvRuta.setText(mascota.getRuta());






    }

    @Override
    public int getItemCount() {
        return listaMascotas.length;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
