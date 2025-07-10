package com.grupo.proyectointegradori.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo.proyectointegradori.entity.Detalle;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface DetalleRepository extends JpaRepository<Detalle, Long>{
    @Query(value = "SELECT d.idDetalle FROM detalle d", nativeQuery = true)
    List<Long> getIdDetalles();
}
