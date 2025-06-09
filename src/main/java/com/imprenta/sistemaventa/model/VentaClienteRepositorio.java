
package com.imprenta.sistemaventa.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VentaClienteRepositorio {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<VentaClienteDTO> reporteVentaCliente(String nombre, String anio, String mes){
        StoredProcedureQuery query=entityManager
        .createStoredProcedureQuery("reporteVentaCliente")
        .registerStoredProcedureParameter("nombre", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("anio", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("mes", String.class, jakarta.persistence.ParameterMode.IN)
        .setParameter("nombre", nombre)
        .setParameter("anio", anio)
        .setParameter("mes", mes);
        List<Object[]> results=query.getResultList();
        List<VentaClienteDTO> lista=new ArrayList<>();
        for(Object[] row : results){
            VentaClienteDTO dto=new VentaClienteDTO();
            dto.setNroDocumento((String) row[0]);
            dto.setCorreo((String) row[1]);
            dto.setFecha((String) row[2]);
            dto.setCantidadVendida(((Number) row[3]).intValue());
            dto.setTotalVendido(((Number) row[4]).doubleValue());
            lista.add(dto);
        }
        return lista;
    }
}
