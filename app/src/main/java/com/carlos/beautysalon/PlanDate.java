package com.carlos.beautysalon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.String.format;

public class PlanDate extends AppCompatActivity {

    // Constantes
    int STARTHOUR = 9;
    String[] SESSION_TYPE =  {"Aplicacion de pestañas", "Colorimetría del cabello", "Manicura", "Maquillaje y peinado"};

    // Campos
    public Spinner spinnerType, spinnerDate, spinnerTime;
    public TextView textViewType, textViewDate, textViewTime;

    // Adaptador para spinner
    ArrayList<String> spinnerArray;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_date);

        textViewType = findViewById(R.id.textViewType);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);

        // Colocar tipo de sesión
        fillTypes();
        // Colocar fechas en spinner
        fillDates();
        // Colocar horas en spinner
        fillTime();
    }

    // Métodos públicos
    // Método para guardar la fecha agendada
    public void buttonSave(View view) {
        if (spinnerValidations()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog);
            builder.setTitle(R.string.confirm_date);
            builder.setMessage(getDialogText());
            builder.setPositiveButton("Guardar", (dialog, which) -> {
                // Hacer cosas aqui al hacer clic en el boton de aceptar
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Atrás", (dialog, which) -> {

            });
            builder.show();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private String getDialogText() {
        String type = spinnerType.getSelectedItem().toString().trim();
        String date = spinnerDate.getSelectedItem().toString().trim();
        String time = spinnerTime.getSelectedItem().toString().trim();

        return "Sesión: " + type + "\n" +
                "Fecha: " + date + "\n" +
                "Hora: " + time;
    }

    private boolean spinnerValidations() {

        if (spinnerType.getSelectedItem().toString().trim().equals("<Selecciona un tipo de sesión>")) {
            textViewType.setVisibility(View.VISIBLE);
        } else {
            textViewType.setVisibility(View.GONE);
        }

        if (spinnerDate.getSelectedItem().toString().trim().equals("<Selecciona una fecha>")) {
            textViewDate.setVisibility(View.VISIBLE);
        } else {
            textViewDate.setVisibility(View.GONE);
        }

        if (spinnerTime.getSelectedItem().toString().trim().equals("<Selecciona una hora>")) {
            textViewTime.setVisibility(View.VISIBLE);
        } else {
            textViewTime.setVisibility(View.GONE);
        }

        return textViewType.getVisibility() != View.VISIBLE && textViewDate.getVisibility() != View.VISIBLE && textViewTime.getVisibility() != View.VISIBLE;
    }

    // Métodos privados
    // Método que llena los tipos de sesión
    private void fillTypes() {
        spinnerArray = new ArrayList<>();

        spinnerArray.add("<Selecciona un tipo de sesión>");

        Collections.addAll(spinnerArray, SESSION_TYPE);

        spinnerType = findViewById(R.id.spinnerType);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                spinnerArray
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerType.setAdapter(adapter);

        /*
        spinnerType = findViewById(R.id.spinnerType);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinnerType.setAdapter(spinnerArrayAdapter);
        */
    }

    // Método que llena las fechas
    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillDates() {
        Calendar calendar = Calendar.getInstance();
        spinnerArray = new ArrayList<>();

        spinnerArray.add("<Selecciona una fecha>");

        String month = "";
        int monthNumber = calendar.get(Calendar.MONTH);

        switch (monthNumber) {
            case 0: month = "enero";
                break;
            case 1: month = "febrero";
                break;
            case 2: month = "marzo";
                break;
            case 3: month = "abril";
                break;
            case 4: month = "mayo";
                break;
            case 5: month = "junio";
                break;
            case 6: month = "julio";
                break;
            case 7: month = "agosto";
                break;
            case 8: month = "septiembre";
                break;
            case 9: month = "octubre";
                break;
            case 10: month = "noviembre";
                break;
            case 11: month = "diciembre";
                break;
        }

        int i = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, 1);

        while (i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            String date = format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "/" + month + "/" + calendar.get(Calendar.YEAR);
            calendar.add(Calendar.DATE, 1);
            spinnerArray.add(date);
            i++;
        }

        spinnerDate = findViewById(R.id.spinnerDate);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                spinnerArray
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerDate.setAdapter(adapter);

        /*
        spinnerDate = findViewById(R.id.spinnerDate);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinnerDate.setAdapter(spinnerArrayAdapter);
        */
    }

    // Método que llena las horas
    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillTime() {
        spinnerArray = new ArrayList<>();

        spinnerArray.add("<Selecciona una hora>");

        for (int i = STARTHOUR; i <= 20; i++) {
            String hour = format("%02d", i) + ":00 hrs";
            spinnerArray.add(hour);
        }

        spinnerTime = findViewById(R.id.spinnerTime);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                spinnerArray
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerTime.setAdapter(adapter);

        /*
        spinnerTime = findViewById(R.id.spinnerTime);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinnerTime.setAdapter(spinnerArrayAdapter);
        */
    }
}