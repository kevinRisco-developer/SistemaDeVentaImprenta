
package com.grupo.proyectointegradori.Controllers;

import com.grupo.proyectointegradori.entity.TotalCategoriaDTO;
import com.grupo.proyectointegradori.service.TotalCategoriaServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/inicio/reportes/cotizacion")
public class TotalCategoriaController {
    
    private final TotalCategoriaServicio servicio;
    
    public TotalCategoriaController(TotalCategoriaServicio servicio){
        this.servicio=servicio;
    }
    
    @GetMapping("/total-categoria")
    public List<TotalCategoriaDTO> generarReporte(
        @RequestParam String anio,
        @RequestParam(defaultValue="0") String mes){
            return servicio.generarReporte(anio,mes);
        }
}
