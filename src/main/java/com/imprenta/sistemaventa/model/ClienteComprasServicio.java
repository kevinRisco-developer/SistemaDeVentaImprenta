
package com.imprenta.sistemaventa.model;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteComprasServicio {
    private final ClienteComprasRepositorio repositorio;
    
    public ClienteComprasServicio(ClienteComprasRepositorio repositorio){
        this.repositorio=repositorio;
    }
    
    public List<ClienteComprasDTO> generarReporte(String anio, String mes){
        return repositorio.reporteClienteCompras(anio, mes);
    }
}
