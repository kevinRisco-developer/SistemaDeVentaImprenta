package com.grupo.proyectointegradori.Controllers;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo.proyectointegradori.entity.Categoria;
import com.grupo.proyectointegradori.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/categoriaContro")
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping()
    public List<Categoria> getAllCategoria() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Categoria getCategoriaById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria: " + id + " no encontrada"));
    }

    @PostMapping("actualizar/{id}")
    public String updateCategoria(@PathVariable Long id, @ModelAttribute Categoria categoriaDetail) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria: " + id + " no encontrada"));
        categoria.setNombre(categoriaDetail.getNombre());
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/categoria/eliminar/{id}") // eliminó con GET
    public String deleteCategoria(@PathVariable Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria: " + id + " no encontrada"));
        categoriaRepository.delete(categoria);
        return "redirect:/categorias";
    }

    @PostMapping("/insertar")
    public String insertarCategoria(@ModelAttribute Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/totalanioxmes")
    public List<Object[]> getTotalCategoriaXAnioYMes(
            @RequestParam("anio") String anio,
            @RequestParam("mes") String mes) {
        return categoriaRepository.getTotalCategoriaxMesAnio(anio, mes);
    }

    @GetMapping("/gastoscategoria")
    public List<Object[]> generarReporte(
            @RequestParam int idCat1,
            @RequestParam int idCat2) {
        return categoriaRepository.getReporteGastosPorCategoria(idCat1, idCat2);
    }

    @GetMapping("/formularioActualizar/{id}")
    public String enviarAFormulario(Model model, @PathVariable Long id) {
        String accionFormulario = "actualizar";
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria " + id + " no encontrada"));
        model.addAttribute("id", categoria.getIdCategoria());
        model.addAttribute("nombre", categoria.getNombre());
        model.addAttribute("accion", accionFormulario);
        return "formCategoria";
    }

    @GetMapping("/formularioInsertar")
    public String formularioInsertar(Model model) {
        model.addAttribute("accion", "insertar");
        model.addAttribute("nombre", "");
        return "formCategoria";
    }
}
