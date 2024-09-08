package com.example.miprimeraapp;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class StatisticsActivity extends AppCompatActivity {

    private TextView statisticsTextView;
    private ScrollView scrollView;
    private Button nuevoJuegoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Inicializar vistas
        statisticsTextView = findViewById(R.id.statisticsTextView);
        nuevoJuegoButton = findViewById(R.id.nuevoJuegoButton);

        // Cargar y mostrar estadísticas
        cargarEstadisticas();

        // Configuración de botón de Nuevo Juego
        nuevoJuegoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarNuevoJuego();
            }
        });
    }

    private void iniciarNuevoJuego() {
        Intent intent = new Intent(this, PlayingAhorcado.class);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

    private void cargarEstadisticas() {
        // Aquí puedes agregar el código para cargar las estadísticas y mostrarlas en statisticsTextView.
        // Por ejemplo:
        String estadisticas = "Juego 2: Terminó en 10s\nJuego 3: Canceló\nJuego 4: Terminó en 13s\nJuego 5: Terminó en 53s\nJuego 6: Terminó en 12s\nJuego 7: Terminó en 21s\nJuego 8: Canceló\nJuego 9: Canceló\nJuego 10: Terminó en 123s\nJuego 11: Terminó en 91s\nJuego 12: Terminó en 36s";
        statisticsTextView.setText(estadisticas);
    }

}
