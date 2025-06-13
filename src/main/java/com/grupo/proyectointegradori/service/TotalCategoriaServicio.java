
package com.grupo.proyectointegradori.service;

import com.grupo.proyectointegradori.repository.TotalCategoriaRepositorio;
import com.grupo.proyectointegradori.entity.TotalCategoriaDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TotalCategoriaServicio {
    private final TotalCategoriaRepositorio repositorio;
    
    public TotalCategoriaServicio(TotalCategoriaRepositorio repositorio){
        this.repositorio=repositorio;
    }
    
    public List<TotalCategoriaDTO> generarReporte(String anio, String mes){
        return repositorio.reporteTotalxCategoria(anio, mes);
    }
}
