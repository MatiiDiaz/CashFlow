package com.example.cashflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cashflow.database.DBGasto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AnadirGastoActivity extends AppCompatActivity {

    EditText txtNombre, txtMonto;
    Button btn_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_gasto);

        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        txtNombre = findViewById(R.id.txtNombre);
        txtMonto = findViewById(R.id.txtMonto);
        btn_guardar = findViewById(R.id.btn_guardar);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fechaHoy = dateFormat.format(calendar.getTime());
                DBGasto dbGasto = new DBGasto(AnadirGastoActivity.this);
                long id = dbGasto.insertarGasto(txtNombre.getText().toString(), txtMonto.getText().toString(), fechaHoy);
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

    private void limpiar(){
        txtNombre.setText("");
        txtMonto.setText("");
    }
    private void volver(){
        Intent intent = new Intent(AnadirGastoActivity.this, ListaGastosActivity.class);
        startActivity(intent);
    }
}