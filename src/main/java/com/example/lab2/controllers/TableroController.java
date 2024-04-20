package com.example.lab2.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TableroController {

    //variables
    private int filas;
    private int columnas;
    private int bombas;
    private int intentos;
    private String coordenadas;
    private int[][] tablero;
    private boolean[][] visitado;

    @GetMapping("/buscaminas")
    public String mostrarFormulario() {
        return "configuracionTablero";
    }

    @PostMapping("/buscaminas")
    public String iniciarJuego(@RequestParam("filas") int filas,
                               @RequestParam("columnas") int columnas,
                               @RequestParam("bombas") int bombas,
                               @RequestParam("intentos") int intentos,
                               @RequestParam("bombasCoordenadas") String bombasCoordenadas,
                               Model model) {

        String[] coordenadas = bombasCoordenadas.split("\\s+");
        for (String coordenada : coordenadas) {
            // eliminamos los paréntesis y separamos la coordenada en fila y columna
            String[] partes = coordenada.replaceAll("[()]", "").split(",");

            // a enteros
            int fila = Integer.parseInt(partes[0]);
            int columna = Integer.parseInt(partes[1]);

        }

        //Inicializamos variables
        this.filas = filas;
        this.columnas = columnas;
        this.bombas = bombas;
        this.intentos = intentos;
        this.tablero = new int[filas][columnas];
        this.visitado = new boolean[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = 0;
                visitado[i][j] = false;
            }
        }

        // Funcion para colocar las bombas en las celdas
        for (String coordenada : coordenadas) {
            String[] partes = coordenada.replaceAll("[()]", "").split(",");
            int fila = Integer.parseInt(partes[0]);
            int columna = Integer.parseInt(partes[1]);
            tablero[fila][columna] = -1; // Marcamos la celda como bomba (-1)
            // Incrementamos los números alrededor de la bomba
            for (int i = fila - 1; i <= fila + 1; i++) {
                for (int j = columna - 1; j <= columna + 1; j++) {
                    if (i >= 0 && i < filas && j >= 0 && j < columnas && tablero[i][j] != -1) {
                        tablero[i][j]++;
                    }
                }
            }
        }

        // Mandamos a la vista
        model.addAttribute("tablero", tablero);
        model.addAttribute("visited", visitado);
        model.addAttribute("intentos", intentos);

        return "explotarMinas";
    }


    @PostMapping("/jugar")
    public String mostrarMina(@RequestParam("coordenada") String coordenada, Model model){
        String[] parts = coordenada.split(" ");
        String part1 = parts[0];
        String part2 = parts[1];

        int fila = Integer.parseInt(part1);
        int columna = Integer.parseInt(part2);

        if (tablero[fila][columna] == -1) {
            intentos--;
            if (intentos == 0) {
                return "explotarMinas";
            }
        } else {
            // Si la celda no es una bomba, mostrar el número y explorar celdas aledañas si es 0
            revelarCeldas(fila, columna);
        }

        model.addAttribute("tablero", tablero);
        model.addAttribute("visited", visitado);
        model.addAttribute("intentos", intentos);

        return "explotarMinas";
    }

    private void revelarCeldas(int fila, int columna) {
        if (visitado[fila][columna]) {
            return;
        }
        visitado[fila][columna] = true;
        if (tablero[fila][columna] == 0) {
            for (int i = fila - 1; i <= fila + 1; i++) {
                for (int j = columna - 1; j <= columna + 1; j++) {
                    if (i >= 0 && i < filas && j >= 0 && j < columnas) {
                        revelarCeldas(i, j);
                    }
                }
            }
        }
    }
}
