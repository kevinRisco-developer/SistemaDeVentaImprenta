
package com.imprenta.sistemaventa.model;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GastosCategoriaServicio {
    private final GastosCategoriaRepositorio repositorio;
    
    public GastosCategoriaServicio(GastosCategoriaRepositorio repositorio){
        this.repositorio=repositorio;
    }
    
    public List<GastosCategoriaDTO> generarReporte(int idCat1, int idCat2){
        return repositorio.reporteGastosPorCategoria(idCat1, idCat2);
    }
}
