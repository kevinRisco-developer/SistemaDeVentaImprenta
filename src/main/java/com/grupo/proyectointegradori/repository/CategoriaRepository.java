package com.grupo.proyectointegradori.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grupo.proyectointegradori.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(value = "CALL totalCategoriaxMesAnio(:anio, :mes)", nativeQuery = true)
    List<Object[]> getTotalCategoriaxMesAnio(@Param("mes") String mes, @Param("anio") String anio);
}
