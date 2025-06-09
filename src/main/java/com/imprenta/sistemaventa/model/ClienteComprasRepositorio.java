
package com.imprenta.sistemaventa.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteComprasRepositorio {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<ClienteComprasDTO> reporteClienteCompras(String anio, String mes){
        StoredProcedureQuery query=entityManager
        .createStoredProcedureQuery("reporteclientecompras")
        .registerStoredProcedureParameter("in_anio", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("in_mes", String.class, jakarta.persistence.ParameterMode.IN)
        .setParameter("in_anio", anio)
        .setParameter("in_mes", mes);
        List<Object[]> results=query.getResultList();
        List<ClienteComprasDTO> lista=new ArrayList<>();
        for(Object[] row : results){
            ClienteComprasDTO dto=new ClienteComprasDTO();
            dto.setNroDocumento((String) row[0]);
            dto.setNombres((String) row[1]);
            dto.setApellidos((String) row[2]);
            dto.setTotalComprado((double) row[3]);
            lista.add(dto);
        }
        return lista;
    }
}
