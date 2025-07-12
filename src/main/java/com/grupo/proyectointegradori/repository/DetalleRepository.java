package com.grupo.proyectointegradori.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo.proyectointegradori.entity.Detalle;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetalleRepository extends JpaRepository<Detalle, Long>{
    @Query(value = "SELECT d.idDetalle FROM detalle d", nativeQuery = true)
    List<Long> getIdDetalles();
    
    @Query(value = "SELECT d FROM detalle d WHERE d.idDetalle = :idDetalle", nativeQuery = true)
    List<Detalle> getDetalleById(@Param("idDetalle") Long idDetalle);
    
    @Query("SELECT d FROM Detalle d WHERE d.idCotizacion = :idCotizacion")
    List<Detalle> findByIdCotizacion(@Param("idCotizacion") Long idCotizacion);
    
    @Query(value = """
                   SELECT DISTINCT d.*
                   FROM detalle d
                   JOIN gastodeventas gv ON gv.idDetalle=d.idDetalle
                   WHERE d.idCotizacion = :idCotizacion
                   """, nativeQuery = true)
    List<Detalle> findDetallesConGastosPorCotizacion(@Param("idCotizacion") Long idCotizacion);
}
