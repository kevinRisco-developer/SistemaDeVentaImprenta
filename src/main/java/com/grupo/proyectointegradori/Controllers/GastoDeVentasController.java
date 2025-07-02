package com.grupo.proyectointegradori.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo.proyectointegradori.entity.GastoDeVentas;
import com.grupo.proyectointegradori.repository.GastoDeVentasRepository;

@RestController
@RequestMapping("/gastoDeVentas")
public class GastoDeVentasController {
    @Autowired
    private GastoDeVentasRepository gastodeventarepository;

    @GetMapping
    public List<GastoDeVentas> getAll() {
        return gastodeventarepository.findAll();
    }

    @GetMapping("/{id}")
    public GastoDeVentas getGastoDeVentasById(@PathVariable Long id) {
        GastoDeVentas gastodeventas = gastodeventarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el gasto con id: " + id));
        return gastodeventas;
    }

    @PostMapping
    public GastoDeVentas insertGasto(@RequestBody GastoDeVentas gastodeventas) {
        return gastodeventarepository.save(gastodeventas);
    }

    @PutMapping("/{id}")
    public GastoDeVentas updateGasto(@PathVariable Long id, @RequestBody GastoDeVentas gastoventadetail) {
        GastoDeVentas gastoventa = gastodeventarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el gasto con id: " + id));
        gastoventa.setDescripcionGasto(gastoventadetail.getDescripcionGasto());
        gastoventa.setIdDetalle(gastoventadetail.getIdDetalle());
        gastoventa.setCosto(gastoventadetail.getCosto());
        return gastodeventarepository.save(gastoventa);
    }

    @DeleteMapping("/{id}")
    public String deleteGasto(@PathVariable Long id) {
        GastoDeVentas gastoventa = gastodeventarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el gasto con id: " + id));
        gastodeventarepository.delete(gastoventa);
        return "Se elimino el gasto con id: " + id;
    }
}
