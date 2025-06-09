
package com.imprenta.sistemaventa.controller;

import com.imprenta.sistemaventa.model.ClienteComprasDTO;
import com.imprenta.sistemaventa.model.ClienteComprasServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/inicio/reportes/compra")
public class ClienteComprasController {
    
    private final ClienteComprasServicio servicio;
    
    public ClienteComprasController(ClienteComprasServicio servicio){
        this.servicio=servicio;
    }
    
    @GetMapping("/cliente-compras")
    public List<ClienteComprasDTO> generarReporte(
        @RequestParam String anio,
        @RequestParam(defaultValue="0") String mes){
            return servicio.generarReporte(anio,mes);
        }
}
