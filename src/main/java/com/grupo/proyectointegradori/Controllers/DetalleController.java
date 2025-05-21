package com.grupo.proyectointegradori.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo.proyectointegradori.entity.Detalle;
import com.grupo.proyectointegradori.repository.DetalleRepository;

@RestController
@RequestMapping("/detalle")
public class DetalleController {
    @Autowired 
    private DetalleRepository detalleRepository;
    @GetMapping
    public List<Detalle> getAllDetalle(){
        return detalleRepository.findAll();
    }
    @PostMapping
    public Detalle insertDetalle(@RequestBody Detalle detalle){
        return detalleRepository.save(detalle);
    }
    @GetMapping("/{id}")
    public Detalle getById(@PathVariable Long id){
        Detalle detalle=detalleRepository.findById(id)
        .orElseThrow(()->new RuntimeException("No se encontro detalle con id: "+id));
        return detalle;
    }
    @PutMapping("/{id}")
    public Detalle updateDetalle(@PathVariable Long id, @RequestBody Detalle detalleDetail){
        Detalle detalle= detalleRepository.findById(id)
        .orElseThrow(()->new RuntimeException("No se encontro el detalle con id: "+id));
        detalle.setIdCotizacion(detalleDetail.getIdCotizacion());
        detalle.setCantidad(detalleDetail.getCantidad());
        detalle.setDescripcion(detalleDetail.getDescripcion());
        detalle.setPrecio(detalleDetail.getPrecio());
        detalle.setIdCategoria(detalleDetail.getIdCategoria());
        return detalleRepository.save(detalle);
    }
    @DeleteMapping("/{id}")
    public String deleteDetalle(@PathVariable Long id){
        Detalle detalle=detalleRepository.findById(id)
        .orElseThrow(()->new RuntimeException("No se encontró el detalle con id: "+id));
        detalleRepository.delete(detalle);
        return "Se elimino el detalle con id: "+id;
    }
}
