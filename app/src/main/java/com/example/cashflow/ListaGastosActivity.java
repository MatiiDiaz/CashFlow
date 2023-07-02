package com.example.cashflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cashflow.adaptadores.ListaGastosAdapter;
import com.example.cashflow.database.DBGasto;
import com.example.cashflow.entidades.Gasto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ListaGastosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    TextView tvMensaje, tvMensaje2;
    ListaGastosAdapter adapter;
    RecyclerView listaGastos;
    ArrayList<Gasto> listaArrayGastos;
    ImageButton btn_add_gasto;
    SearchView svListaGastos;
    Spinner spn_categorias;
    String categoriaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isDarkModeEnabled()) {
            setTheme(R.style.Theme_Home_night);
        } else {
            setTheme(R.style.Theme_Home);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gastos);
        tvMensaje = findViewById(R.id.tvMensaje);
        tvMensaje2 = findViewById(R.id.tvMensaje2);
        btn_add_gasto = findViewById(R.id.btn_add_gasto);
        listaGastos = findViewById(R.id.listaGastos);
        svListaGastos = findViewById(R.id.svListaGastos);
        listaGastos.setLayoutManager(new LinearLayoutManager(this));

        DBGasto dbGasto = new DBGasto(ListaGastosActivity.this);
        listaArrayGastos = new ArrayList<>();
        listaArrayGastos = dbGasto.mostrarGastos();

        if (listaArrayGastos.isEmpty()) {
            tvMensaje.setVisibility(View.VISIBLE);
            svListaGastos.setVisibility(View.INVISIBLE);
            spn_categorias.setVisibility(View.INVISIBLE);
        } else {
            adapter = new ListaGastosAdapter(listaArrayGastos);
            listaGastos.setAdapter(adapter);
            tvMensaje.setVisibility(View.INVISIBLE);
            svListaGastos.setVisibility(View.VISIBLE);
            spn_categorias = findViewById(R.id.spn_categorias);
            ArrayAdapter<CharSequence> categoriasAdapter = ArrayAdapter.createFromResource(this,
                    R.array.categorias_array, android.R.layout.simple_spinner_item);
            categoriasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_categorias.setAdapter(categoriasAdapter);
            spn_categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    categoriaSeleccionada = parent.getItemAtPosition(position).toString();
                    filtrarPorCategoria(categoriaSeleccionada);
                    Log.d("hola", String.valueOf(listaArrayGastos));
                    if (listaArrayGastos.isEmpty()){
                        tvMensaje2.setVisibility(View.VISIBLE);
                    } else {
                        tvMensaje2.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    filtrarPorCategoria("Todos");
                }
            });
        }

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

    private void filtrarPorCategoria(String categoria) {
        if (categoria.equals("Todos")) {
            adapter.filtrado(""); // Mostrar todos los datos sin filtrar
        } else {
            adapter.filtradoPorCategoria(categoria); // Filtrar por categoría seleccionada
        }
    }
}