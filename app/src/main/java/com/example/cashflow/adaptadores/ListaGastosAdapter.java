package com.example.cashflow.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cashflow.R;
import com.example.cashflow.entidades.Gasto;

import java.util.ArrayList;

public class ListaGastosAdapter extends RecyclerView.Adapter<ListaGastosAdapter.GastoViewHolder> {

    ArrayList<Gasto> listaGasto;
    public ListaGastosAdapter(ArrayList<Gasto> listaGasto){
        this.listaGasto = listaGasto;
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
    }

    @Override
    public int getItemCount() {
        return listaGasto.size();
    }

    public class GastoViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvMonto, tvFecha;

        public GastoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvMonto = itemView.findViewById(R.id.tvMonto);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
