package com.example.cashflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DistribucionActivity extends AppCompatActivity {

    private EditText arriendoEditText;
    private EditText alimentacionEditText;
    private EditText transporteEditText;
    private EditText serviciosBasicosEditText;
    private EditText educacionEditText;
    private EditText deudasEditText;
    private EditText ahorrosInversionesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isDarkModeEnabled()) {
            setTheme(R.style.Theme_Home_night);
        } else {
            setTheme(R.style.Theme_Home);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribucion);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_analytics);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_analytics:
                    return true;
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_payments:
                    startActivity(new Intent(getApplicationContext(), ListaGastosActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });


        // Obtener el presupuesto definido en las preferencias
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int presupuesto = Integer.parseInt(sharedPreferences.getString("key_presupuesto", "0"));
        int arriendo = sharedPreferences.getInt("key_arriendo", 0);
        float arriendoValue = presupuesto*((float) arriendo /100);
        int alimentacion = sharedPreferences.getInt("key_alimentacion", 0);
        float alimentacionValue = presupuesto*((float) alimentacion /100);
        int transporte = sharedPreferences.getInt("key_transporte", 0);
        float transporteValue = presupuesto*((float) transporte /100);
        int serviciosBasicos = sharedPreferences.getInt("key_serviciosBasicos", 0);
        float serviciosBasicosValue = presupuesto*((float) serviciosBasicos /100);
        int educacion = sharedPreferences.getInt("key_educacion", 0);
        float educacionValue = presupuesto*((float) educacion /100);
        int deudas = sharedPreferences.getInt("key_deudas", 0);
        float deudasValue = presupuesto*((float) deudas /100);
        int ahorrosInversiones = sharedPreferences.getInt("key_ahorrosInversiones", 0);
        float ahorrosInversionesValue = presupuesto*((float) ahorrosInversiones /100);
        // Mostrar el presupuesto en el TextView
        TextView presupuestoTextView = findViewById(R.id.presupuestoTextView);
        TextView arriendoPorcentajeTextView = findViewById(R.id.arriendoPorcentajeTextView);
        TextView alimentacionPorcentajeTextView = findViewById(R.id.alimentacionPorcentajeTextView);
        TextView transportePorcentajeTextView = findViewById(R.id.transportePorcentajeTextView);
        TextView serviciosBasicosPorcentajeTextView = findViewById(R.id.serviciosBasicosPorcentajeTextView);
        TextView educacionPorcentajeTextView = findViewById(R.id.educacionPorcentajeTextView);
        TextView deudasPorcentajeTextView = findViewById(R.id.deudasPorcentajeTextView);
        TextView ahorrosInversionesPorcentajeTextView = findViewById(R.id.ahorrosInversionesPorcentajeTextView);
        presupuestoTextView.setText("Presupuesto: " + presupuesto);
        arriendoPorcentajeTextView.setText("$"+ Integer.valueOf((int) arriendoValue));
        alimentacionPorcentajeTextView.setText("$"+ Integer.valueOf((int) alimentacionValue));
        transportePorcentajeTextView.setText("$"+ Integer.valueOf((int) transporteValue));
        serviciosBasicosPorcentajeTextView.setText("$"+ Integer.valueOf((int) serviciosBasicosValue));
        educacionPorcentajeTextView.setText("$"+ Integer.valueOf((int) educacionValue));
        deudasPorcentajeTextView.setText("$"+ Integer.valueOf((int) deudasValue));
        ahorrosInversionesPorcentajeTextView.setText("$"+ Integer.valueOf((int) ahorrosInversionesValue));

        // Obtener las referencias a los EditText
        arriendoEditText = findViewById(R.id.arriendoEditText);
        alimentacionEditText = findViewById(R.id.alimentacionEditText);
        transporteEditText = findViewById(R.id.transporteEditText);
        serviciosBasicosEditText = findViewById(R.id.serviciosBasicosEditText);
        educacionEditText = findViewById(R.id.educacionEditText);
        deudasEditText = findViewById(R.id.deudasEditText);
        ahorrosInversionesEditText = findViewById(R.id.ahorrosInversionesEditText);

        // Agregar OnClickListener al botón "Guardar"
        Button guardarButton = findViewById(R.id.guardarButton);
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarValoresDistribucion();
            }
        });
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

    private void guardarValoresDistribucion() {
        int arriendoTV = 0;
        int alimentacionTV = 0;
        int transporteTV = 0;
        int serviciosBasicosTV = 0;
        int educacionTV = 0;
        int deudasTV = 0;
        int ahorrosInversionesTV = 0;
        // Obtener los valores de distribución de los EditText
        if (!arriendoEditText.getText().toString().equals("")){
            arriendoTV = Math.min(Integer.parseInt(arriendoEditText.getText().toString()), 100);
        }
        if (!alimentacionEditText.getText().toString().equals("")){
            alimentacionTV = Math.min(Integer.parseInt(alimentacionEditText.getText().toString()), 100);
        }
        if (!transporteEditText.getText().toString().equals("")){
            transporteTV = Math.min(Integer.parseInt(transporteEditText.getText().toString()), 100);

        }
        if (!serviciosBasicosEditText.getText().toString().equals("")){
            serviciosBasicosTV = Math.min(Integer.parseInt(serviciosBasicosEditText.getText().toString()), 100);
        }
        if (!educacionEditText.getText().toString().equals("")){
            educacionTV = Math.min(Integer.parseInt(educacionEditText.getText().toString()), 100);

        }
        if (!deudasEditText.getText().toString().equals("")){
            deudasTV = Math.min(Integer.parseInt(deudasEditText.getText().toString()), 100);
        }
        if (!ahorrosInversionesEditText.getText().toString().equals("")){
            ahorrosInversionesTV = Math.min(Integer.parseInt(ahorrosInversionesEditText.getText().toString()), 100);
        }
        // Obtén los demás valores de distribución de los EditText aquí

        // Guardar los valores de distribución en las preferencias
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key_arriendo", arriendoTV);
        editor.putInt("key_alimentacion", alimentacionTV);
        editor.putInt("key_transporte", transporteTV);
        editor.putInt("key_serviciosBasicos", serviciosBasicosTV);
        editor.putInt("key_educacion", educacionTV);
        editor.putInt("key_deudas", deudasTV);
        editor.putInt("key_ahorrosInversiones", ahorrosInversionesTV);
        // Agrega los demás valores de distribución aquí

        editor.apply();

        // Obtener el presupuesto definido en las preferencias
        int presupuesto = Integer.parseInt(sharedPreferences.getString("key_presupuesto", "0"));
        int arriendo = sharedPreferences.getInt("key_arriendo", 0);
        float arriendoValue = presupuesto*((float) arriendo /100);
        int alimentacion = sharedPreferences.getInt("key_alimentacion", 0);
        float alimentacionValue = presupuesto*((float) alimentacion /100);
        int transporte = sharedPreferences.getInt("key_transporte", 0);
        float transporteValue = presupuesto*((float) transporte /100);
        int serviciosBasicos = sharedPreferences.getInt("key_serviciosBasicos", 0);
        float serviciosBasicosValue = presupuesto*((float) serviciosBasicos /100);
        int educacion = sharedPreferences.getInt("key_educacion", 0);
        float educacionValue = presupuesto*((float) educacion /100);
        int deudas = sharedPreferences.getInt("key_deudas", 0);
        float deudasValue = presupuesto*((float) deudas /100);
        int ahorrosInversiones = sharedPreferences.getInt("key_ahorrosInversiones", 0);
        float ahorrosInversionesValue = presupuesto*((float) ahorrosInversiones /100);
        // Mostrar el presupuesto en el TextView
        TextView presupuestoTextView = findViewById(R.id.presupuestoTextView);
        TextView arriendoPorcentajeTextView = findViewById(R.id.arriendoPorcentajeTextView);
        TextView alimentacionPorcentajeTextView = findViewById(R.id.alimentacionPorcentajeTextView);
        TextView transportePorcentajeTextView = findViewById(R.id.transportePorcentajeTextView);
        TextView serviciosBasicosPorcentajeTextView = findViewById(R.id.serviciosBasicosPorcentajeTextView);
        TextView educacionPorcentajeTextView = findViewById(R.id.educacionPorcentajeTextView);
        TextView deudasPorcentajeTextView = findViewById(R.id.deudasPorcentajeTextView);
        TextView ahorrosInversionesPorcentajeTextView = findViewById(R.id.ahorrosInversionesPorcentajeTextView);
        presupuestoTextView.setText("Presupuesto: " + presupuesto);
        arriendoPorcentajeTextView.setText("$"+ Integer.valueOf((int) arriendoValue));
        alimentacionPorcentajeTextView.setText("$"+ Integer.valueOf((int) alimentacionValue));
        transportePorcentajeTextView.setText("$"+ Integer.valueOf((int) transporteValue));
        serviciosBasicosPorcentajeTextView.setText("$"+ Integer.valueOf((int) serviciosBasicosValue));
        educacionPorcentajeTextView.setText("$"+ Integer.valueOf((int) educacionValue));
        deudasPorcentajeTextView.setText("$"+ Integer.valueOf((int) deudasValue));
        ahorrosInversionesPorcentajeTextView.setText("$"+ Integer.valueOf((int) ahorrosInversionesValue));

        // Mostrar un mensaje de éxito o realizar cualquier otra acción necesaria
        Toast.makeText(this, "Valores de distribución guardados", Toast.LENGTH_SHORT).show();
    }
}
