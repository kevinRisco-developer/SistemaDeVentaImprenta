package com.grupo.proyectointegradori.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar";
    }

    @GetMapping("/categorias")
    public String categorias() {
        return "categorias";
    }
}
