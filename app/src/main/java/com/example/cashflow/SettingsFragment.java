package com.example.cashflow;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registra el escucha de cambios en SharedPreferences
        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Deregistra el escucha de cambios en SharedPreferences
        PreferenceManager.getDefaultSharedPreferences(requireContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("key_tema") || key.equals("key_dark")) {
            // Actualiza la interfaz de usuario según los cambios en las preferencias
            updateUI();
        }
    }

    private void updateUI() {
        // Obtiene una referencia al ImageView de tu interfaz de usuario
        ImageView imageView = requireView().findViewById(R.id.imageView3);

        if (isDarkModeEnabled()) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_night);
        }
    }

    private boolean isDarkModeEnabled() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean temaActivado = sharedPreferences.getBoolean("key_tema", false);
        boolean darkActivado = sharedPreferences.getBoolean("key_dark", false);

        if (temaActivado) {
            if (darkActivado) {
                return true; // Modo oscuro activado
            } else {
                int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                return nightModeFlags == Configuration.UI_MODE_NIGHT_YES; // Utilizar el modo del sistema
            }
        } else {
            return false; // Modo normal
        }
    }
}
