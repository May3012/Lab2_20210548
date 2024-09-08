package com.example.miprimeraapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Random;

public class PlayingAhorcado extends AppCompatActivity {

    private String[] words = {"FIBRA", "REDES", "ANTENA", "PROPA", "CLOUD", "TELECO"};
    private String palabraAdivinar;
    private int intentos;
    private static final int MAX_INTENTOS = 6;

    private LinearLayout wordLayout;
    private GridLayout teclado1, teclado2;
    private Button nuevoJuegoButton;
    private TextView mensajeFinal;
    private Chronometer cronometro; // Cronómetro para el tiempo

    private ImageView cabeza, torso, brazo_derecho, brazo_izquierdo, pierna_izquierda, pierna_derecha;

    private ArrayList<String> correctLetters = new ArrayList<>();
    private ArrayList<String> usedLetters = new ArrayList<>();

    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Para el menú
        setContentView(R.layout.activity_game);
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);

        // Inicializar vistas y elementos
        wordLayout = findViewById(R.id.words);
        teclado1 = findViewById(R.id.letrasGridLayout);
        teclado2 = findViewById(R.id.letrasGridLayout2);
        nuevoJuegoButton = findViewById(R.id.nuevoJuegoButton);
        mensajeFinal = findViewById(R.id.mensajeFinal);
        cronometro = findViewById(R.id.cronometro); // Inicializar el cronómetro

        // Inicializar ImageViews del ahorcado
        cabeza = findViewById(R.id.cabeza);
        torso = findViewById(R.id.torso);
        brazo_derecho = findViewById(R.id.brazo_derecho);
        brazo_izquierdo = findViewById(R.id.brazo_izquierdo);
        pierna_izquierda = findViewById(R.id.pierna_izquierda);
        pierna_derecha = findViewById(R.id.pierna_derecha);

        // Inicializar random
        random = new Random();

        // Configuración de botón de Nuevo Juego
        nuevoJuegoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarNuevoJuego();
            }
        });

        // Iniciar juego
        iniciarNuevoJuego();
    }

    //lógica del juego Ahorcado
    private void iniciarNuevoJuego() {
        palabraAdivinar = words[random.nextInt(words.length)];
        intentos = 0;
        correctLetters.clear();
        usedLetters.clear();

        // Se reinicia el cronometro
        // Fuentes: https://es.stackoverflow.com/questions/594328/quiero-detectar-el-tiempo-de-mi-cronometro-java-kotlin y chatgpt
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();

        // se vacía o se limpia el jeugo una vez terminado o ganado
        palabraFinalVisible(false, "");
        limpiarAhorcado();
        limpiarTeclado();

        // Empleamos otra función para mostrar la palabra
        mostrarPalabra();


        // Ayuda para depurar. Fuente: https://es.stackoverflow.com/questions/115972/android-uso-b%C3%A1sico-de-toast-maketext
        Toast.makeText(this, "Nuevo juego iniciado!", Toast.LENGTH_SHORT).show();
    }


    private void mostrarPalabra() {
        wordLayout.removeAllViews();
        for (int i = 0; i < palabraAdivinar.length(); i++) {
            TextView letraView = new TextView(this);
            letraView.setText("_ ");
            letraView.setTextSize(24);
            letraView.setTextColor(Color.BLACK); // Cambiamos el color de las palabras elegidas por el usuario
            wordLayout.addView(letraView);
        }
    }

    // Reiniciamos el juego "limpiando" la interfaz
    private void limpiarAhorcado() {
        cabeza.setVisibility(View.INVISIBLE);
        torso.setVisibility(View.INVISIBLE);
        brazo_derecho.setVisibility(View.INVISIBLE);
        brazo_izquierdo.setVisibility(View.INVISIBLE);
        pierna_izquierda.setVisibility(View.INVISIBLE);
        pierna_derecha.setVisibility(View.INVISIBLE);
    }

    // Con esta función se habilita nuevamente los teclados
    private void limpiarTeclado() {
        // Teclado 1
        for (int i = 0; i < teclado1.getChildCount(); i++) {
            Button btn = (Button) teclado1.getChildAt(i);
            btn.setEnabled(true);
            btn.setBackgroundColor(Color.LTGRAY);
            btn.setTextColor(Color.BLACK);
        }

        // Teclado 2
        for (int i = 0; i < teclado2.getChildCount(); i++) {
            Button btn = (Button) teclado2.getChildAt(i);
            btn.setEnabled(true);
            btn.setBackgroundColor(Color.LTGRAY);
            btn.setTextColor(Color.BLACK);
        }
    }

    // Función que maneja la selección de una letra
    public void seleccionarLetra(View view) {
        Button letraButton = (Button) view;
        String letraSeleccionada = letraButton.getText().toString().toUpperCase();

        // Para evitar que se use la misma letra:
        if (usedLetters.contains(letraSeleccionada)) {
            Toast.makeText(this, "Ya has usado esta letra.", Toast.LENGTH_SHORT).show(); //Log
            return;
        }

        usedLetters.add(letraSeleccionada);
        letraButton.setEnabled(false);
        letraButton.setBackgroundColor(Color.DKGRAY);
        letraButton.setTextColor(Color.WHITE);

        if (palabraAdivinar.contains(letraSeleccionada)) {
            // Letra correcta
            correctLetters.add(letraSeleccionada);
            actualizarPalabra(letraSeleccionada);
        } else {
            // Letra incorrecta
            intentos++;
            actualizarAhorcado();
        }

        verificarEstadoJuego();
    }
    //Función para mostrar la palabra correcta al final de la partida
    private void actualizarPalabra(String letra) {
        for (int i = 0; i < palabraAdivinar.length(); i++) {
            if (palabraAdivinar.charAt(i) == letra.charAt(0)) {
                TextView letraView = (TextView) wordLayout.getChildAt(i);
                letraView.setText(letra + " ");
            }
        }
    }

    //Función para actualizar la imagen del ahoracado a medida que se van perdiendo intentos
    private void actualizarAhorcado() {
        switch (intentos) {
            case 1:
                cabeza.setVisibility(View.VISIBLE);
                break;
            case 2:
                torso.setVisibility(View.VISIBLE);
                break;
            case 3:
                brazo_derecho.setVisibility(View.VISIBLE);
                break;
            case 4:
                brazo_izquierdo.setVisibility(View.VISIBLE);
                break;
            case 5:
                pierna_izquierda.setVisibility(View.VISIBLE);
                break;
            case 6:
                pierna_derecha.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    // Función para verificar si el jugador ganó o perdió
    private void verificarEstadoJuego() {
        if (intentos >= MAX_INTENTOS) {
            // Jugador perdió
            cronometro.stop(); // Detener cronómetro
            palabraFinalVisible(true, "¡Has perdido! La palabra era: " + palabraAdivinar);
            deshabilitarTeclado();
        } else if (verificarGanador()) {
            // Jugador ganó
            // Empleamos el uso de una IA para poder implmenetar el cronometro
            cronometro.stop(); // Detener cronómetro
            long tiempoTotal = (SystemClock.elapsedRealtime() - cronometro.getBase()) / 1000;
            palabraFinalVisible(true, "¡Felicidades! ¡Has ganado!\nTerminó en " + tiempoTotal + " segundos");
            deshabilitarTeclado(); // finalmente se deshabilita el teclado
        }
    }

    private boolean verificarGanador() {
        for (int i = 0; i < palabraAdivinar.length(); i++) {
            if (!correctLetters.contains(String.valueOf(palabraAdivinar.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    // Palabra visible
    private void palabraFinalVisible(boolean visible, String mensaje) {
        if (visible) {
            mensajeFinal.setVisibility(View.VISIBLE);
            mensajeFinal.setText(mensaje);
        } else {
            mensajeFinal.setVisibility(View.GONE);
            mensajeFinal.setText("");
        }
    }
    //Para esta funció se usó la fuente: https://stackoverflow.com/questions/10242800/android-difference-between-getcount-and-getchildcount-in-listview
    private void deshabilitarTeclado() {
        for (int i = 0; i < teclado1.getChildCount(); i++) {
            teclado1.getChildAt(i).setEnabled(false);
        }
        for (int i = 0; i < teclado2.getChildCount(); i++) {
            teclado2.getChildAt(i).setEnabled(false);
        }
    }

    //App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    // Para dirigirnos a estadísticas
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Manejar la selección de los ítems del menú
        int id = item.getItemId();
        if (id == R.id.statistics) {
            //  actividad de estadísticas
            Intent intent = new Intent(this, StatisticsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
