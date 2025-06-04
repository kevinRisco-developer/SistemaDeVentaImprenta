package com.grupo.proyectointegradori.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grupo.proyectointegradori.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query(value = "CALL reporteclientecompras(:anio, :mes)", nativeQuery = true)
    List<Object[]> getReporteclientecompras(@Param("anio") String anio, @Param("mes") String mes);

    java.util.Optional<Usuario> findByCorreo(String correo);
}
