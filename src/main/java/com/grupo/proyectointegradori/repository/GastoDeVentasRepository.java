package com.grupo.proyectointegradori.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo.proyectointegradori.entity.GastoDeVentas;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GastoDeVentasRepository extends JpaRepository<GastoDeVentas, Long>{
    @Query(value = "SELECT gv.idGastoDeVentas FROM gastodeventas gv", nativeQuery = true)
    List<Long> getIdGastoDeVentas();
    
    @Query(value = "CALL insertarGastoDetalle(:idDetalle, :costo, :descripcion)", nativeQuery = true)
    List<Object[]> getInsertarGastoDetalle(
        @Param("idDetalle") Long idDetalle, 
        @Param("costo") Double costo, 
        @Param("descripcion") String descripcion);
    
    @Query(value = "CALL actualizarGastoDetalle(:idCotizacion, :idDetalle, :idGastoDeVentas, :descripcion, :costo)", nativeQuery = true)
    List<Object[]> getActualizarGastoDetalle(
        @Param("idCotizacion") Long idCotizacion,
        @Param("idDetalle") Long idDetalle,
        @Param("idGastoDeVentas") Long idGastoDeVentas,
        @Param("descripcion") String descripcion,
        @Param("costo") Double costo);
    
    @Query("SELECT gv FROM GastoDeVentas gv WHERE gv.idDetalle = :idDetalle")
    List<GastoDeVentas> findByIdDetalle(@Param("idDetalle") Long idDetalle);
    
    @Query(value = """
           SELECT gv.*
           FROM gastodeventas gv
           WHERE gv.idDetalle = :idDetalle
           """, nativeQuery = true)
    List<GastoDeVentas> findGastosPorDetalle(@Param("idDetalle") Long idDetalle);
}
