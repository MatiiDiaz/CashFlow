package com.example.cashflow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class PresupuestoDialog extends DialogFragment {

    private Calendar selectedDate;

    public interface PresupuestoDialogListener {
        void onPresupuestoConfirmed(String presupuesto, String fecha);
    }

    private PresupuestoDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_presupuesto, null);

        EditText editTextPresupuesto = dialogView.findViewById(R.id.editTextPresupuesto);
        EditText editTextFecha = dialogView.findViewById(R.id.editTextFecha);
        ImageView imageViewCalendario = dialogView.findViewById(R.id.imageViewCalendario);

        // Cambiar el comportamiento al hacer clic en el icono del calendario
        imageViewCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateOptions(editTextFecha);
            }
        });
        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateOptions(editTextFecha);
            }
        });

        builder.setView(dialogView)
                .setTitle("Ingresar Presupuesto")
                .setCancelable(false)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String presupuesto = editTextPresupuesto.getText().toString();
                        if (presupuesto.isEmpty()) {
                            presupuesto = "0";
                        }
                        String fecha = editTextFecha.getText().toString();
                        if (fecha.isEmpty()) {
                            fecha = "5"; // Establecer el valor predeterminado, "Pago el 5"
                        }
                        if (listener != null) {
                            listener.onPresupuestoConfirmed(presupuesto, fecha);
                        }
                    }
                });

        return builder.create();
    }

    public void setPresupuestoDialogListener(PresupuestoDialogListener listener) {
        this.listener = listener;
    }

    private void showDateOptions(final EditText editTextFecha) {
    final String[] options = {"5", "10", "15", "20", "25", "30"};
    final String[] displayOptions = {"Pago el 5", "Pago el 10", "Pago el 15", "Pago el 20", "Pago el 25", "Pago el 30"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccionar fecha")
                .setItems(displayOptions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Obtener la opción seleccionada
                        String selectedOption = options[which];

                        // Actualizar el EditText con la opción seleccionada
                        editTextFecha.setText(options[which]);

                        // Obtener el día de la opción seleccionada
                        int day = Integer.parseInt(selectedOption);
                        // Establecer la fecha seleccionada
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.DAY_OF_MONTH, day);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Restablecer la fecha seleccionada
                        selectedDate = null;
                        editTextFecha.setText("");
                    }
                });

        builder.create().show();
    }

}