package com.grupo.proyectointegradori.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grupo.proyectointegradori.entity.Cotizacion;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
    @Query(value = "CALL mostrarCotizaciones()", nativeQuery = true)
    List<Object[]> getTotalCotizaciones();

    @Query(value = "CALL mostrarDetallesCotizaciones(:id)", nativeQuery = true)
    List<Object[]> getDetallesCotizaciones(@Param("id") Long id);

    @Query(value = "CALL totalPrecioPorCotizacion(:id)", nativeQuery = true)
    double getPrecioPorCotizacion(@Param("id") Long id);

    @Query(value = "CALL mostarVendedorPorCotizacion(:id)", nativeQuery = true)
    String getNombreVendedor(@Param("id") Long id);
    
    @Query(value = "SELECT c.idCotizacion FROM cotizacion c ORDER BY c.idCotizacion ASC", nativeQuery = true)
    List<Long> getIdCotizaciones();
}
