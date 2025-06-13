
package com.grupo.proyectointegradori.Controllers;

import com.grupo.proyectointegradori.entity.GastosCategoriaDTO;
import com.grupo.proyectointegradori.service.GastosCategoriaServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/inicio/reportes/gastos")
public class GastosCategoriaController {
    
    private final GastosCategoriaServicio servicio;
    
    public GastosCategoriaController(GastosCategoriaServicio servicio){
        this.servicio=servicio;
    }
    
    @GetMapping("/gastos-categoria")
    public List<GastosCategoriaDTO> generarReporte(
        @RequestParam int idCat1,
        @RequestParam int idCat2){
            return servicio.generarReporte(idCat1, idCat2);
        }
}
