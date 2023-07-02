package com.example.cashflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cashflow.database.DBGasto;
import com.example.cashflow.entidades.Gasto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AnadirGastoActivity extends AppCompatActivity {

    EditText txtNombre, txtMonto;
    Button btn_guardar,btn_actualizar, btn_eliminar;
    Spinner spnCategoria;
    ImageView ivNuevoGasto;
    Gasto gasto;
    int id = 0;
    boolean correcto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isDarkModeEnabled()) {
            setTheme(R.style.Theme_Home_night);
        } else {
            setTheme(R.style.Theme_Home);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_gasto);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        txtNombre = findViewById(R.id.txtNombre);
        txtMonto = findViewById(R.id.txtMonto);
        spnCategoria = findViewById(R.id.spnCategoria);
        ivNuevoGasto = findViewById(R.id.ivNuevoGasto);
        btn_guardar = findViewById(R.id.btn_guardar);
        btn_actualizar = findViewById(R.id.btn_actualizar);
        btn_eliminar = findViewById(R.id.btn_eliminar);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                id = 0;
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        final DBGasto dbGasto = new DBGasto(AnadirGastoActivity.this);
        if (id != 0){
            gasto = dbGasto.verGastos(String.valueOf(id));
        } else {
            gasto = null;
        }
        if (gasto != null){
            txtNombre.setText(gasto.getNombre_gasto());
            txtMonto.setText(gasto.getMonto_gasto());

            // Obtén la categoría seleccionada desde la base de datos
            String categoriaSeleccionada = gasto.getCategoria_gasto();

            // Establece el valor seleccionado en el Spinner
            if (categoriaSeleccionada != null) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnCategoria.getAdapter();
                int index = adapter.getPosition(categoriaSeleccionada);
                spnCategoria.setSelection(index);
            }

            btn_actualizar.setVisibility(View.VISIBLE);
            btn_eliminar.setVisibility(View.VISIBLE);
            btn_guardar.setVisibility(View.INVISIBLE);
        } else {
            limpiar();
            btn_actualizar.setVisibility(View.INVISIBLE);
            btn_eliminar.setVisibility(View.INVISIBLE);
            btn_guardar.setVisibility(View.VISIBLE);
        }
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();
                String monto = txtMonto.getText().toString();
                String fechaHoy = dateFormat.format(calendar.getTime());
                String categoria = spnCategoria.getSelectedItem().toString();
                if (!nombre.equals("") && !monto.equals("")){
                    correcto = dbGasto.editarGasto(String.valueOf(id), nombre, monto, fechaHoy, categoria);
                    if (correcto){
                        Toast.makeText(AnadirGastoActivity.this, "Registro Modificado", Toast.LENGTH_LONG).show();
                        limpiar();
                        volver();
                        correcto = false;
                    } else {
                        Toast.makeText(AnadirGastoActivity.this, "Error al Registrar", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AnadirGastoActivity.this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correcto = dbGasto.eliminarGasto(String.valueOf(id));
                if (correcto){
                    Toast.makeText(AnadirGastoActivity.this, "Registro Eliminado", Toast.LENGTH_LONG).show();
                    limpiar();
                    volver();
                    correcto = false;
                } else {
                    Toast.makeText(AnadirGastoActivity.this, "Error al Eliminar", Toast.LENGTH_LONG).show();
                }
            }
        });
        spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSeleccionada = parent.getItemAtPosition(position).toString();

                // Actualizar la imagen en ImageView según la categoría seleccionada
                switch (categoriaSeleccionada) {
                    case "Arriendo/Hipoteca":
                        ivNuevoGasto.setImageResource(R.drawable.house_fill);
                        break;
                    case "Alimentación":
                        ivNuevoGasto.setImageResource(R.drawable.food_fill);
                        break;
                    case "Transporte":
                        ivNuevoGasto.setImageResource(R.drawable.transport_fill);
                        break;
                    case "Servicios Básicos":
                        ivNuevoGasto.setImageResource(R.drawable.water_fill);
                        break;
                    case "Educación":
                        ivNuevoGasto.setImageResource(R.drawable.school_fill);
                        break;
                    case "Deudas":
                        ivNuevoGasto.setImageResource(R.drawable.debt_fill);
                        break;
                    case "Ahorro/Inversión":
                        ivNuevoGasto.setImageResource(R.drawable.savings_fill);
                        break;
                }
                // Continúa con el resto de categorías y sus imágenes

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se requiere ninguna acción si no se selecciona nada
            }
        });


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fechaHoy = dateFormat.format(calendar.getTime());
                String nombre = txtNombre.getText().toString();
                String monto = txtMonto.getText().toString();
                String categoria = spnCategoria.getSelectedItem().toString();
                DBGasto dbGasto = new DBGasto(AnadirGastoActivity.this);
                long id = dbGasto.insertarGasto(nombre, monto, fechaHoy, categoria);
                if (id > 0){
                    Toast.makeText(AnadirGastoActivity.this, "Registro Guardado", Toast.LENGTH_LONG).show();
                    limpiar();
                    volver();
                } else {
                    Toast.makeText(AnadirGastoActivity.this, "Error al Guardar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void limpiar(){
        txtNombre.setText("");
        txtMonto.setText("");
    }
    private void volver(){
        Intent intent = new Intent(AnadirGastoActivity.this, ListaGastosActivity.class);
        startActivity(intent);
    }

    private void verRegistro(){
        Intent intent = new Intent(this, ListaGastosActivity.class);
        startActivity(intent);
    }
}