package com.grupo.proyectointegradori.service;

import com.grupo.proyectointegradori.entity.Cotizacion;
import com.grupo.proyectointegradori.repository.CotizacionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CotizacionService {
    @Autowired
    private CotizacionRepository cotizacionRepository;
    
    public List<Long> getIdCotizaciones(){
        return cotizacionRepository.getIdCotizaciones();
    }
    
    public List<Cotizacion> getCotizaciones(){
        return cotizacionRepository.findAll();
    }
    
    public Optional<Cotizacion> getCotizacionById(Long idCotizacion){
        return cotizacionRepository.findById(idCotizacion);
    }
}
