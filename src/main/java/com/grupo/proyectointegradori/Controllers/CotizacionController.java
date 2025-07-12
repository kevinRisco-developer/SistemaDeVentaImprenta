package com.grupo.proyectointegradori.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo.proyectointegradori.entity.Cotizacion;
import com.grupo.proyectointegradori.repository.CotizacionRepository;

@RestController
@RequestMapping("/cotizacion")
public class CotizacionController {
    @Autowired
    private CotizacionRepository cotizacionRepository;

    @GetMapping
    public List<Cotizacion> getAllCotizacion() {
        return cotizacionRepository.findAll();
    }

    @PostMapping
    public Cotizacion insertCotizacion(@RequestBody Cotizacion cotizacion) {
        return cotizacionRepository.save(cotizacion);
    }

    @GetMapping("/{id}")
    public Cotizacion getCotizacionById(@PathVariable Long id) {
        return cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotizacion con id " + id + " no encontrado"));
    }

    @PutMapping("/{id}")
    public Cotizacion updateCotizacion(@PathVariable Long id, @RequestBody Cotizacion cotizacionDetail) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotizacion con id " + id + " no encontrado"));
        cotizacion.setNroDocumento(cotizacionDetail.getNroDocumento());
        cotizacion.setFecha(cotizacionDetail.getFecha());
        cotizacion.setEstado(cotizacionDetail.getEstado());
        cotizacion.setIdGastoDeVentas(cotizacionDetail.getIdGastoDeVentas());
        return cotizacionRepository.save(cotizacion);
    }

    @DeleteMapping("/{id}")
    public String deleteCotizacion(@PathVariable Long id) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotizacion con id " + id + " no encontrado"));
        cotizacionRepository.delete(cotizacion);
        return "Cotizacion con id: " + id + " eliminada!";
    }

    // @GetMapping("/cotizaciones")
    // public String getTotalCotizaciones(Model model) {
    // List<Object[]> listaCotizaciones =
    // cotizacionRepository.getTotalCotizaciones();
    // model.addAttribute("cotizaciones", listaCotizaciones);
    // return "home.html";
    // }
}
