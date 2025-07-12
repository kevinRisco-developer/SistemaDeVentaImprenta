package com.grupo.proyectointegradori.service;

import com.grupo.proyectointegradori.entity.GastoDeVentas;
import com.grupo.proyectointegradori.repository.GastoDeVentasRepository;
import com.grupo.proyectointegradori.response.GastoResponseActualizar;
import com.grupo.proyectointegradori.response.GastoResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoDeVentasService {
    
    @Autowired
    private GastoDeVentasRepository gastoRepository;
    
    public List<Long> getIdGastoDeVentas(){
        return gastoRepository.getIdGastoDeVentas();
    }
    
    public List<GastoDeVentas> getGastos(){
        return gastoRepository.findAll();
    }
    
    public Optional<GastoDeVentas> getGastoById(Long idGastoDeVentas){
        return gastoRepository.findById(idGastoDeVentas);
    }
    
    public List<GastoDeVentas> getGastosPorDetalle(Long idDetalle){
        return gastoRepository.findByIdDetalle(idDetalle);
    }
    
    public List<GastoDeVentas> getGastosXDetalle(Long idDetalle){
        return gastoRepository.findGastosPorDetalle(idDetalle);
    }
    
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
    
    public List<GastoResponseActualizar> actualizarGastoDetalle(
            Long idCotizacion, Long idDetalle, Long idGastoDeVentas, String descripcion, Double costo){
        List<Object[]> resultado = gastoRepository.getActualizarGastoDetalle(
                idCotizacion, idDetalle, idGastoDeVentas, descripcion, costo);
        List<GastoResponseActualizar> responseList = new ArrayList<>();
        
        for (Object[] row : resultado){
            Long id_cotiza = (row[0] instanceof Number) ? ((Number) row[0]).longValue() : null;
            Long id_detalle = (row[1] instanceof Number) ? ((Number) row[1]).longValue() : null;
            Long id_gasto = (row[2] instanceof Number) ? ((Number) row[2]).longValue() : null;
            Double gasto = (row[3] instanceof Number) ? ((Number) row[3]).doubleValue() : null;
            String desc = row[4] != null ? row[4].toString() : null;
            String msg = row[5] != null ? row[5].toString() : null;
            
            System.out.println("Mensaje: " +msg); //para debug/log
            responseList.add(new GastoResponseActualizar(id_cotiza, id_detalle, id_gasto, gasto, desc, msg));
        }
        return responseList;
    }
}
