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

}