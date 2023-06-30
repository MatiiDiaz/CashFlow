package com.example.cashflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.cashflow.adaptadores.ListaGastosAdapter;
import com.example.cashflow.database.DBGasto;
import com.example.cashflow.entidades.Gasto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ListaGastosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ListaGastosAdapter adapter;
    RecyclerView listaGastos;
    ArrayList<Gasto> listaArrayGastos;
    ImageButton btn_add_gasto;
    SearchView svListaGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isDarkModeEnabled()) {
            setTheme(R.style.Theme_Home_night);
        } else {
            setTheme(R.style.Theme_Home);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gastos);
        btn_add_gasto = findViewById(R.id.btn_add_gasto);
        listaGastos = findViewById(R.id.listaGastos);
        svListaGastos = findViewById(R.id.svListaGastos);
        listaGastos.setLayoutManager(new LinearLayoutManager(this));

        DBGasto dbGasto = new DBGasto(ListaGastosActivity.this);
        listaArrayGastos = new ArrayList<>();

        adapter = new ListaGastosAdapter(dbGasto.mostrarGastos());
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
        svListaGastos.setOnQueryTextListener(this);
    }
    private boolean isDarkModeEnabled() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean temaActivado = sharedPreferences.getBoolean("key_tema", false);
        boolean darkActivado = sharedPreferences.getBoolean("key_dark", false);

        if (temaActivado) {
            return darkActivado; // Modo oscuro activado
        } else {
            int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            return nightModeFlags == Configuration.UI_MODE_NIGHT_YES; // Utilizar el modo del sistema
        }
    }
    private void agregarGasto() {
        // Aquí puedes iniciar una nueva actividad para la vista de configuración
        Intent intent = new Intent(ListaGastosActivity.this, AnadirGastoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}