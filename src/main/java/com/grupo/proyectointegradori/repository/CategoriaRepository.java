package com.grupo.proyectointegradori.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grupo.proyectointegradori.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(value = "CALL totalCategoriaxMesAnio(:anio, :mes)", nativeQuery = true)
    List<Object[]> getTotalCategoriaxMesAnio(@Param("anio") String anio, @Param("mes") String mes);
    
    @Query(value = "CALL reporteGastosPorCategoria(:idCat1, :idCat2)", nativeQuery = true)
    List<Object[]> getReporteGastosPorCategoria(@Param("idCat1") int idCat1, @Param("idCat2") int idCat2);
}