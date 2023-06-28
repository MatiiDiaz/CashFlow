package com.example.cashflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PresupuestoDialog.PresupuestoDialogListener {

    private TextView tvPresupuesto;
    private TextView tvFechaPresupuesto;
    private boolean presupuestoDefinido = false;
    private PresupuestoDialog presupuestoDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isDarkModeEnabled()) {
            setTheme(R.style.Theme_Home_night);
        } else {
            setTheme(R.style.Theme_Home);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        tvPresupuesto = findViewById(R.id.tvPresupuesto);
        tvFechaPresupuesto = findViewById(R.id.tvFechaPresupuesto);
        presupuestoDefinido = true;

        Button btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirConfiguracion();
            }
        });

        if (presupuestoDefinido) {
            // Mostrar la vista principal
            mostrarVistaPrincipal();
        } else {
            // Mostrar el diálogo de presupuesto
            showPresupuestoDialog();
        }
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

    private void showPresupuestoDialog() {
        presupuestoDialog = new PresupuestoDialog();
        presupuestoDialog.setPresupuestoDialogListener(this);
        presupuestoDialog.show(getSupportFragmentManager(), "PresupuestoDialog");
    }

    @Override
    public void onPresupuestoConfirmed(String presupuesto, String fecha) {
        // Ocultar el diálogo de presupuesto
        presupuestoDialog.dismiss();

        // Guardar el presupuesto y la fecha en las preferencias
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key_presupuesto", presupuesto);
        editor.putString("key_fecha", fecha);
        editor.apply();

        // Mostrar la vista principal
        mostrarVistaPrincipal();
    }

    private void mostrarVistaPrincipal() {
        String presupuesto = sharedPreferences.getString("key_presupuesto", "0");
        String fecha = sharedPreferences.getString("key_fecha", "5");

        // Actualizar los TextView con los valores guardados en las preferencias
        tvPresupuesto.setText(String.valueOf(presupuesto));
        tvFechaPresupuesto.setText(String.valueOf(fecha));

        // Verificar si el presupuesto es igual a 0 y mostrar el diálogo de presupuesto
        if (presupuesto.equals("0")) {
            showPresupuestoDialog();
        }
    }

    private void abrirConfiguracion() {
        // Aquí puedes iniciar una nueva actividad para la vista de configuración
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void updateViewFromPreferences() {
        String presupuesto = sharedPreferences.getString("key_presupuesto", "0");
        String fecha = sharedPreferences.getString("key_fecha", "5");

        // Actualizar los TextView con los valores guardados en las preferencias
        tvPresupuesto.setText(String.valueOf(presupuesto));
        tvFechaPresupuesto.setText(String.valueOf(fecha));

        // Verificar si el presupuesto es igual a 0 y mostrar el diálogo de presupuesto
        if (presupuesto.equals("0")) {
            showPresupuestoDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateViewFromPreferences();
        if (isDarkModeEnabled()) {
            setTheme(R.style.Theme_Home_night);
        } else {
            setTheme(R.style.Theme_Home);
        }
    }

}