package com.grupo.proyectointegradori.service;

import com.grupo.proyectointegradori.repository.GastoDeVentasRepository;
import com.grupo.proyectointegradori.response.GastoResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoDeVentasService {
    
    @Autowired
    private GastoDeVentasRepository gastoRepository;
    
    public List<GastoResponse> insertarGastoDetalle(Long idDetalle, Double costo, String descripcion){
        
        List<Object[]> resultado = gastoRepository.getInsertarGastoDetalle(idDetalle, costo, descripcion);
        
        List<GastoResponse> responseList = new ArrayList<>();
        
        for (Object[] row : resultado){
            Long id = (row[0] instanceof Number) ? ((Number) row[0]).longValue() : null;
            Double gasto = (row[1] instanceof Number) ? ((Number) row[1]).doubleValue() : null;
            String desc = row[2] != null ? row[2].toString() : null;
            String msg = row[3] != null ? row[3].toString() : null;
            
            System.out.println("Mensaje: " +msg); //para debug/log
            responseList.add(new GastoResponse(id, gasto, desc, msg));
        }
        return responseList;
    }
}
