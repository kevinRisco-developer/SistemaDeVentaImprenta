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
    private GastoDeVentasRepository gatodeventarepository;

    @GetMapping
    public List<GastoDeVentas> getAll() {
        return gatodeventarepository.findAll();
    }

    @GetMapping("/{id}")
    public GastoDeVentas getGastoDeVentasById(@PathVariable Long id) {
        GastoDeVentas gastodeventas = gatodeventarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el gasto con id: " + id));
        return gastodeventas;
    }

    @PostMapping
    public GastoDeVentas insertGasto(@RequestBody GastoDeVentas gastodeventas) {
        return gatodeventarepository.save(gastodeventas);
    }

    @PutMapping("/{id}")
    public GastoDeVentas updateGasto(@PathVariable Long id, @RequestBody GastoDeVentas gastoventadetail) {
        GastoDeVentas gastoventa = gatodeventarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el gasto con id: " + id));
        gastoventa.setDescripcionGasto(gastoventadetail.getDescripcionGasto());
        gastoventa.setIdDetalle(gastoventadetail.getIdDetalle());
        gastoventa.setCosto(gastoventadetail.getCosto());
        return gatodeventarepository.save(gastoventa);
    }

    @DeleteMapping("/{id}")
    public String deleteGasto(@PathVariable Long id) {
        GastoDeVentas gastoventa = gatodeventarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el gasto con id: " + id));
        gatodeventarepository.delete(gastoventa);
        return "Se elimino el gasto con id: " + id;
    }
}
