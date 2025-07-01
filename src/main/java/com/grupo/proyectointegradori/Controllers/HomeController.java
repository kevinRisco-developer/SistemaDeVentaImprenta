package com.grupo.proyectointegradori.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.grupo.proyectointegradori.repository.CategoriaRepository;
import com.grupo.proyectointegradori.repository.CotizacionRepository;

@Controller
public class HomeController {
    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    // it GOES here!!
    @GetMapping("/")
    public String home(Model model) {
        List<Object[]> listCtz = cotizacionRepository.getTotalCotizaciones();
        model.addAttribute("listaCotizaciones", listCtz);
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
    public String categorias(Model model) {
        List<Object[]> listaCategorias = categoriaRepository.getCategorias();
        model.addAttribute("categorias", listaCategorias);
        return "categorias";
    }

    @GetMapping("/cotizacion/ver/{id}")
    public String cotizaciones(@PathVariable Long id, Model model) {
        List<Object[]> listDetallesCotizacion = cotizacionRepository.getDetallesCotizaciones(id);
        double totalPrecioPorCotizacion = cotizacionRepository.getPrecioPorCotizacion(id);
        String nombreVendedor = cotizacionRepository.getNombreVendedor(id);
        model.addAttribute("listaDetallesCotizaciones", listDetallesCotizacion);
        model.addAttribute("precioTotal", totalPrecioPorCotizacion);
        model.addAttribute("nombreVendedor", nombreVendedor);
        return "Cotizaciones";
    }

}
