package com.example.cashflow;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PresupuestoDialog.PresupuestoDialogListener {

    private TextView tvPresupuesto;
    private TextView tvFechaPresupuesto;
    private boolean presupuestoDefinido = false;
    private PresupuestoDialog presupuestoDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isDarkModeEnabled()) {
            setTheme(R.style.Theme_Home_night);
        } else {
            setTheme(R.style.Theme_Home);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPresupuesto = findViewById(R.id.tvPresupuesto);
        tvFechaPresupuesto = findViewById(R.id.tvFechaPresupuesto);
        presupuestoDefinido = isPresupuestoDefinido();

        if (presupuestoDefinido) {
            // Mostrar la vista principal
            setContentView(R.layout.activity_main);
        } else {
            // Mostrar el diálogo de presupuesto
            showPresupuestoDialog();
        }
    }
    private boolean isDarkModeEnabled() {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
    private boolean isPresupuestoDefinido() {
        // Lógica para verificar si el presupuesto está definido
        return false; // Cambiar esto con tu propia lógica
    }
    private void showPresupuestoDialog() {
        presupuestoDialog = new PresupuestoDialog();
        presupuestoDialog.setPresupuestoDialogListener(this);
        presupuestoDialog.show(getSupportFragmentManager(), "PresupuestoDialog");
    }

    @Override
    public void onPresupuestoConfirmed(int presupuesto, String fecha) {
        // Ocultar el diálogo de presupuesto
        presupuestoDialog.dismiss();
        // Actualizar el valor del presupuesto en el TextView
        tvPresupuesto.setText(String.valueOf(presupuesto));

        // Obtener la fecha actual y formatearla
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String fechaActual = dateFormat.format(calendar.getTime());

        // Actualizar el valor de la fecha en el TextView
        tvFechaPresupuesto.setText(fechaActual);
        presupuestoDefinido = true;
    }
}