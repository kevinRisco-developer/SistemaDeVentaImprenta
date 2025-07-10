package com.grupo.proyectointegradori.Controllers;

import com.grupo.proyectointegradori.entity.Detalle;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo.proyectointegradori.entity.GastoDeVentas;
import com.grupo.proyectointegradori.repository.GastoDeVentasRepository;
import com.grupo.proyectointegradori.response.GastoResponse;
import com.grupo.proyectointegradori.service.GastoDeVentasService;
import com.grupo.proyectointegradori.service.DetalleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/gastoDeVentas")
public class GastoDeVentasController {
    //@Autowired
    private GastoDeVentasRepository gastodeventarepository;
    private GastoDeVentasService gastoService;
    private DetalleService detalleService;
    
    public GastoDeVentasController(
            GastoDeVentasRepository gastodeventarepository, 
            GastoDeVentasService gastoService,
            DetalleService detalleService
    ){
        this.detalleService=detalleService;
        this.gastoService=gastoService;
        this.gastodeventarepository=gastodeventarepository;
    }

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
    
    @PostMapping("/insertar")
    public String insertarGastoDetalle(
        @RequestParam(value = "idDetalle", required = false) Long idDetalle,
        @RequestParam("costo") Double costo,
        @RequestParam("descripcion") String descripcion,
        Model model
    ){
        List<GastoResponse> resultado = gastoService.insertarGastoDetalle(idDetalle, costo, descripcion);
        model.addAttribute("resultado", resultado);
        return "resultadoGasto";
    }
    
    @GetMapping("/formularioInsertar")
    public String formularioInsertar(
            @RequestParam(value = "idDetalle", required = false) Long idDetalle,
            @RequestParam(value = "costo", required = false) Double costo,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            Model model){
        List<Detalle> detalles = detalleService.getDetalles();
        model.addAttribute("accion","insertar");
        model.addAttribute("detalles",detalles);
        
        if (idDetalle!=null){
            detalleService.getDetalleById(idDetalle).ifPresent(detalle ->{
                model.addAttribute("id_seleccionado",detalle.getIdDetalle());
                model.addAttribute("descripcion_detalle",detalle.getDescripcion());
            });
        }else{
            model.addAttribute("id_seleccionado", "");
            model.addAttribute("descripcion_detalle","");
        }
        model.addAttribute("costo", costo!=null?costo:"");
        model.addAttribute("descripcion", descripcion!=null?descripcion:"");
        return "formGastoDetalle";
    }
     
    /*@PostMapping
    public GastoDeVentas insertGasto(@RequestBody GastoDeVentas gastodeventas) {
        return gastodeventarepository.save(gastodeventas);
    }*/
    
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
