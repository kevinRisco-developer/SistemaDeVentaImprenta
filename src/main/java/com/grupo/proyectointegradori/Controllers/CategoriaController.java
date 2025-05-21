package com.grupo.proyectointegradori.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo.proyectointegradori.entity.Categoria;
import com.grupo.proyectointegradori.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> getAllCategoria() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Categoria getCategoriaById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria: " + id + " no encontrada"));
    }

    @PutMapping("/{id}")
    public Categoria updateCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetail) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria: " + id + " no encontrada"));
        categoria.setNombre(categoriaDetail.getNombre());
        return categoriaRepository.save(categoria);
    }

    @DeleteMapping("/{id}")
    public String deleteCategoria(@PathVariable Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria: " + id + " no encontrada"));
        categoriaRepository.delete(categoria);
        return "categoria con id: " + id + " fue eliminado";
    }

    @GetMapping("/totalanioxmes")
    public List<Object[]> getTotalCategoriaXAnioYMes(
            @RequestParam("anio") String anio,
            @RequestParam("mes") String mes) {
        return categoriaRepository.getTotalCategoriaxMesAnio(anio, mes);
    }
}
