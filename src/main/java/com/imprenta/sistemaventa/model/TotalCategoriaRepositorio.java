
package com.imprenta.sistemaventa.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TotalCategoriaRepositorio {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<TotalCategoriaDTO> reporteTotalxCategoria(String anio, String mes){
        StoredProcedureQuery query=entityManager
        .createStoredProcedureQuery("totalCategoriaxMesAnio")
        .registerStoredProcedureParameter("in_fechaA", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("in_fechaM", String.class, jakarta.persistence.ParameterMode.IN)
        .setParameter("in_fechaA", anio)
        .setParameter("in_fechaM", mes);
        List<Object[]> results=query.getResultList();
        List<TotalCategoriaDTO> lista=new ArrayList<>();
        for(Object[] row : results){
            TotalCategoriaDTO dto=new TotalCategoriaDTO();
            dto.setNombre((String) row[0]);
            dto.setTotalCotizado((double) row[1]);
            lista.add(dto);
        }
        return lista;
    }
}
