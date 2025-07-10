package com.grupo.proyectointegradori.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo.proyectointegradori.entity.GastoDeVentas;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GastoDeVentasRepository extends JpaRepository<GastoDeVentas, Long>{
    @Query(value = "CALL insertarGastoDetalle(:idDetalle, :costo, :descripcion)", nativeQuery = true)
    List<Object[]> getInsertarGastoDetalle(
            @Param("idDetalle") Long idDetalle, 
            @Param("costo") Double costo, 
            @Param("descripcion") String descripcion);
}
