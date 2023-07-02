package com.example.cashflow.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cashflow.AnadirGastoActivity;
import com.example.cashflow.R;
import com.example.cashflow.entidades.Gasto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaGastosAdapter extends RecyclerView.Adapter<ListaGastosAdapter.GastoViewHolder> {

    ArrayList<Gasto> listaGasto;
    ArrayList<Gasto> listaOriginal;
    public ListaGastosAdapter(ArrayList<Gasto> listaGasto){

        this.listaGasto = listaGasto;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaGasto);
    }

    @NonNull
    @Override
    public GastoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_gasto, null, false);
        return new GastoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GastoViewHolder holder, int position) {
        holder.tvNombre.setText(listaGasto.get(position).getNombre_gasto());
        holder.tvMonto.setText(listaGasto.get(position).getMonto_gasto());
        holder.tvFecha.setText(listaGasto.get(position).getFecha_gasto());
        String categoriaSeleccionada = listaGasto.get(position).getCategoria_gasto();
        switch (categoriaSeleccionada){
            case "Arriendo/Hipoteca":
                holder.ivGasto.setImageResource(R.drawable.house_fill);
                break;
            case "Alimentación":
                holder.ivGasto.setImageResource(R.drawable.food_fill);
                break;
            case "Transporte":
                holder.ivGasto.setImageResource(R.drawable.transport_fill);
                break;
            case "Servicios Básicos":
                holder.ivGasto.setImageResource(R.drawable.water_fill);
                break;
            case "Educación":
                holder.ivGasto.setImageResource(R.drawable.school_fill);
                break;
            case "Deudas":
                holder.ivGasto.setImageResource(R.drawable.debt_fill);
                break;
            case "Ahorro/Inversión":
                holder.ivGasto.setImageResource(R.drawable.savings_fill);
                break;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filtrado(String svListaGasto){
        int longitud = svListaGasto.length();
        if (longitud == 0){
            listaGasto.clear();
            listaGasto.addAll(listaOriginal);
        } else {
            List<Gasto> coleccion = listaGasto.stream().filter(i -> i.getNombre_gasto().toLowerCase().contains(svListaGasto.toLowerCase())).collect(Collectors.toList());
            listaGasto.clear();
            listaGasto.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filtradoPorCategoria(String categoria) {
        if (categoria.equals("Todos")) {
            listaGasto.clear();
            listaGasto.addAll(listaOriginal);
        } else {
            List<Gasto> coleccion = listaOriginal.stream()
                    .filter(gasto -> gasto.getCategoria_gasto().equals(categoria))
                    .collect(Collectors.toList());
            listaGasto.clear();
            listaGasto.addAll(coleccion);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listaGasto.size();
    }

    public class GastoViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvMonto, tvFecha;
        ImageView ivGasto;

        public GastoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvMonto = itemView.findViewById(R.id.tvMonto);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            ivGasto = itemView.findViewById(R.id.ivGasto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AnadirGastoActivity.class);
                    intent.putExtra("ID", listaGasto.get(getAdapterPosition()).getId_gasto());
                    context.startActivity(intent);
                }
            });
        }
    }
}
