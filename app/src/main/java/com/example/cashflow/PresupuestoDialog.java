package com.example.cashflow;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class PresupuestoDialog extends DialogFragment {

    public interface PresupuestoDialogListener {
        void onPresupuestoConfirmed(int presupuesto, String fecha);
    }

    private PresupuestoDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_presupuesto, null);

        EditText editTextPresupuesto = dialogView.findViewById(R.id.editTextPresupuesto);
        EditText editTextFecha = dialogView.findViewById(R.id.editTextFecha);
        ImageView imageViewCalendario = dialogView.findViewById(R.id.imageViewCalendario);

        imageViewCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(editTextFecha);
            }
        });

        builder.setView(dialogView)
                .setTitle("Ingresar Presupuesto")
                .setCancelable(false)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int presupuesto = Integer.parseInt(editTextPresupuesto.getText().toString());
                        String fecha = editTextFecha.getText().toString();

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

    private void showDatePicker(final EditText editTextFecha) {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog y configurarlo
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Actualizar el EditText con el día y el mes seleccionados
                String fechaSeleccionada = String.format(Locale.getDefault(), "%02d-%02d", dayOfMonth, monthOfYear + 1);
                editTextFecha.setText(fechaSeleccionada);
            }
        }, year, month, day);

        // Configurar el DatePickerDialog para mostrar solo el spinner de día y mes
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);

        // Mostrar el DatePickerDialog
        datePickerDialog.show();
    }

}
