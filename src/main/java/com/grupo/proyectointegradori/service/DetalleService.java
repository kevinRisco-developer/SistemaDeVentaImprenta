package com.grupo.proyectointegradori.service;

import com.grupo.proyectointegradori.entity.Detalle;
import com.grupo.proyectointegradori.repository.DetalleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleService {
    @Autowired
    private DetalleRepository detalleRepository;
    
    public List<Long> getIdDetalles(){
        return detalleRepository.getIdDetalles();
    }
    
    public List<Detalle> getDetalles(){
        return detalleRepository.findAll();
    }
    
    public Optional<Detalle> getDetalleById(Long idDetalle){
        return detalleRepository.findById(idDetalle);
    }
    
    public Optional<Detalle> getDetallesPorCotizacion(Long idCotizacion){
        return detalleRepository.findByIdCotizacion(idCotizacion);
    }
}
