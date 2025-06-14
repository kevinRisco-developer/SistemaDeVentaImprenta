
package com.grupo.proyectointegradori.service;

import com.grupo.proyectointegradori.repository.VentaClienteRepositorio;
import com.grupo.proyectointegradori.entity.VentaClienteDTO;
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
