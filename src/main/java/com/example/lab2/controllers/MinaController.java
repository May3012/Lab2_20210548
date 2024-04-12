package com.example.lab2.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class MinaController {

    @GetMapping (value="/buscaminas")
    public String paginaHome() {
        return "jugar";
    }
    @GetMapping (value="/jugar")
    public String paginaJugar() {
        return "minar";
    }

    @PostMapping("/buscaminas")
    public String guardar(@RequestParam("inputfilas3") String filas,
                          @RequestParam("inputcolumnas3") String columnas,
                          @RequestParam("inputIntentos3") String intentos,
                          @RequestParam("inputBombas3") String bombas,
                          @RequestParam("inputPosicionBomba") String poscionBomba,Model model){
        model.addAttribute("filas",filas);
        model.addAttribute("columnas",columnas);
        model.addAttribute("intentos",intentos);
        model.addAttribute("bombas",bombas);
        model.addAttribute("poscionBomba",poscionBomba);
        return "minar";
    }



}