package com.example.cashflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    private ImageView imageView;
    protected void onCreate(Bundle savedInstanceState){
        // Antes de la llamada a super.onCreate(), establece el tema según el modo actual
        super.onCreate(savedInstanceState);
        if (isDarkModeEnabled()) {
            setTheme(R.style.Theme_Splash_night);
        } else {
            setTheme(R.style.Theme_Splash);
        }

        setContentView(R.layout.activity_splash);

        // Obtén la referencia al ImageView
        imageView = findViewById(R.id.imageView3);

        // Cambia la imagen según el modo nocturno
        if (isDarkModeEnabled()) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_night);
        }
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 2000);
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
}