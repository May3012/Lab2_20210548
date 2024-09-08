package com.example.miprimeraapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Definimos los objetos que usaremos
    private EditText editTextName;
    private Button botonPlay;
    private TextView tituloV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buscar los objetos en el layout
        editTextName = findViewById(R.id.inputText);
        botonPlay = findViewById(R.id.buttonTeleGame);
        tituloV = findViewById(R.id.titulo);

        // Registrar el Context Menu en el título
        registerForContextMenu(tituloV);

        // Desactivar el botón de Jugar al inicio
        botonPlay.setEnabled(false);

        // Listener para habilitar el botón cuando el nombre se haya ingresado
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Habilitar el botón solo si se ha ingresado un nombre
                botonPlay.setEnabled(!charSequence.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Listener del botón para iniciar el juego
        botonPlay.setOnClickListener(view -> iniciarJuegoAhorcado());
    }

    // Iniciar el juego del ahorcado
    private void iniciarJuegoAhorcado() {
        Intent intent = new Intent(this, PlayingAhorcado.class);
        // Obtener el nombre del jugador
        String namePlayer = editTextName.getText().toString();
        intent.putExtra("namePlayer", namePlayer);
        startActivity(intent);
    }

    // Creación del Context Menu para cambiar el color del título
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    // Manejar las opciones del Context Menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.green) {
            tituloV.setTextColor(Color.GREEN);
            return true;
        } else if (item.getItemId() == R.id.red) {
            tituloV.setTextColor(Color.RED);
            return true;
        } else if (item.getItemId() == R.id.purple) {
            tituloV.setTextColor(Color.MAGENTA);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}