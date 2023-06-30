package com.example.cashflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.cashflow.adaptadores.ListaGastosAdapter;
import com.example.cashflow.database.DBGasto;
import com.example.cashflow.entidades.Gasto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ListaGastosActivity extends AppCompatActivity {

    RecyclerView listaGastos;
    ArrayList<Gasto> listaArrayGastos;
    ImageButton btn_add_gasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gastos);
        btn_add_gasto = findViewById(R.id.btn_add_gasto);
        listaGastos = findViewById(R.id.listaGastos);
        listaGastos.setLayoutManager(new LinearLayoutManager(this));

        DBGasto dbGasto = new DBGasto(ListaGastosActivity.this);
        listaArrayGastos = new ArrayList<>();

        ListaGastosAdapter adapter = new ListaGastosAdapter(dbGasto.mostrarGastos());
        listaGastos.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_payments);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_payments:
                    return true;
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_analytics:
                    startActivity(new Intent(getApplicationContext(), DistribucionActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        btn_add_gasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarGasto();
            }
        });
    }
    private void agregarGasto() {
        // Aquí puedes iniciar una nueva actividad para la vista de configuración
        Intent intent = new Intent(ListaGastosActivity.this, AnadirGastoActivity.class);
        startActivity(intent);
    }
}