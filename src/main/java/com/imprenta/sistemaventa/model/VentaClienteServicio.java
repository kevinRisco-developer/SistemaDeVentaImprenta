
package com.imprenta.sistemaventa.model;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VentaClienteServicio {
    private final VentaClienteRepositorio repositorio;
    
    public VentaClienteServicio(VentaClienteRepositorio repositorio){
        this.repositorio=repositorio;
    }
    
    public List<VentaClienteDTO> generarReporte(String nombre, String anio, String mes){
        return repositorio.reporteVentaCliente(nombre, anio, mes);
    }
}
