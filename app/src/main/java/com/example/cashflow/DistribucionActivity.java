package com.example.cashflow;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribucion);

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

    private void guardarValoresDistribucion() {
        // Obtener los valores de distribución de los EditText
        int arriendoTV = Integer.parseInt(arriendoEditText.getText().toString());
        int alimentacionTV = Integer.parseInt(alimentacionEditText.getText().toString());
        int transporteTV = Integer.parseInt(transporteEditText.getText().toString());
        int serviciosBasicosTV = Integer.parseInt(serviciosBasicosEditText.getText().toString());
        int educacionTV = Integer.parseInt(educacionEditText.getText().toString());
        int deudasTV = Integer.parseInt(deudasEditText.getText().toString());
        int ahorrosInversionesTV = Integer.parseInt(ahorrosInversionesEditText.getText().toString());
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
