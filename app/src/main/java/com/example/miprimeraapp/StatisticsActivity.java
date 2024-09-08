package com.example.miprimeraapp;

import android.os.Bundle;

import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class StatisticsActivity extends AppCompatActivity {

    private TextView statisticsTextView;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Inicializar vistas
        statisticsTextView = findViewById(R.id.statisticsTextView);
        scrollView = findViewById(R.id.scrollView);

        // Cargar y mostrar estadísticas
        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
    }


}
