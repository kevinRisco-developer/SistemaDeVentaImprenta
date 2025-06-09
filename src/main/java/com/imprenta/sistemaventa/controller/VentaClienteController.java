
package com.imprenta.sistemaventa.controller;

import com.imprenta.sistemaventa.model.VentaClienteDTO;
import com.imprenta.sistemaventa.model.VentaClienteServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/inicio/reportes/venta")
public class VentaClienteController {
    
    private final VentaClienteServicio servicio;
    
    public VentaClienteController(VentaClienteServicio servicio){
        this.servicio=servicio;
    }
    
    @GetMapping("/venta-cliente")
    public List<VentaClienteDTO> generarReporte(
        @RequestParam String nombre,
        @RequestParam(defaultValue="0")String anio,
        @RequestParam(defaultValue="0") String mes){
            return servicio.generarReporte(nombre,anio,mes);
        }
}
