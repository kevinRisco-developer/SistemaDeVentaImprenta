
package com.imprenta.sistemaventa.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GastosCategoriaRepositorio {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<GastosCategoriaDTO> reporteGastosPorCategoria(int idCat1, int idCat2){
        StoredProcedureQuery query=entityManager
        .createStoredProcedureQuery("reporteGastosPorCategoria")
        .registerStoredProcedureParameter("id_categoria1", int.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("id_categoria2", int.class, jakarta.persistence.ParameterMode.IN)
        .setParameter("id_categoria1", idCat1)
        .setParameter("id_categoria2", idCat2);
        List<Object[]> results=query.getResultList();
        List<GastosCategoriaDTO> lista=new ArrayList<>();
        for(Object[] row : results){
            GastosCategoriaDTO dto=new GastosCategoriaDTO();
            dto.setIdCategoria(row[0]!=null?((Number) row[0]).intValue():null);
            dto.setNombre((String) row[1]);
            dto.setGastoTotal(row[2]!=null?((Number) row[2]).doubleValue():0.0);
            dto.setMensaje((String) row[3]);
            lista.add(dto);
        }
        return lista;
    }
}
