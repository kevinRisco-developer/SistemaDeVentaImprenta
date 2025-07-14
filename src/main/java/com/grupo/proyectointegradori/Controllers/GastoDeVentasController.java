package com.grupo.proyectointegradori.Controllers;

import com.grupo.proyectointegradori.entity.Cotizacion;
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
import com.grupo.proyectointegradori.response.GastoResponseActualizar;
import com.grupo.proyectointegradori.response.GastoResponse;
import com.grupo.proyectointegradori.service.CotizacionService;
import com.grupo.proyectointegradori.service.GastoDeVentasService;
import com.grupo.proyectointegradori.service.DetalleService;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/gastoDeVentas")
public class GastoDeVentasController {
    //@Autowired
    private GastoDeVentasRepository gastodeventarepository;
    private GastoDeVentasService gastoService;
    private DetalleService detalleService;
    private CotizacionService cotizacionService;
    
    public GastoDeVentasController(
            GastoDeVentasRepository gastodeventarepository, 
            GastoDeVentasService gastoService,
            DetalleService detalleService,
            CotizacionService cotizacionService
    ){
        this.detalleService=detalleService;
        this.gastoService=gastoService;
        this.gastodeventarepository=gastodeventarepository;
        this.cotizacionService=cotizacionService;
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
    
    @GetMapping("/inicio")
    public String inicioGastoDetalle(){
        return "formGastoDetalle";
    }

    @PostMapping("/insertar")
    public String insertarGastoDetalle(
        @RequestParam(value = "idDetalle", required = false) Long idDetalle,
        @RequestParam("costo") Double costo,
        @RequestParam("descripcion") String descripcion,
        Model model
    ){
        List<GastoResponse> resultado = gastoService.insertarGastoDetalle(idDetalle, costo, descripcion);
        model.addAttribute("tipoOperacion", "insertar");
        model.addAttribute("resultado", resultado);
        return "formGastoDetalle";
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
    
    @PostMapping("/actualizar")
    public String actualizarGastoDetalle(
        @RequestParam(value = "idCotizacion", required = false) Long idCotizacion,
        @RequestParam(value = "idDetalle", required = false) Long idDetalle,
        @RequestParam(value = "idGastoDeVentas", required = false) Long idGastoDeVentas,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("costo") Double costo,
        Model model
    ){
        List<GastoResponseActualizar> resultado = gastoService.actualizarGastoDetalle(idCotizacion, idDetalle, idGastoDeVentas, descripcion, costo);
        model.addAttribute("tipoOperacion", "actualizar");
        model.addAttribute("resultado", resultado);
        
        // Retornar los datos para que el formulario se mantenga visible
        model.addAttribute("accion", "insertar");
        model.addAttribute("idCotizacionSeleccionado", idCotizacion);
        model.addAttribute("idDetalleSeleccionado", idDetalle);
        model.addAttribute("idGastoSeleccionado", idGastoDeVentas);
        model.addAttribute("descripcion", descripcion);
        model.addAttribute("costo", costo);
        
        // Recargar las listas
        model.addAttribute("cotizaciones", cotizacionService.getCotizaciones());
        model.addAttribute("detalles", detalleService.getDetallesPorCotizacion(idCotizacion));
        model.addAttribute("gastos", gastoService.getGastosPorDetalle(idDetalle));
        
        return "resultadoGasto";
    }
    
    @GetMapping("/formularioActualizar")
    public String formularioActualizar(
        @RequestParam(value = "nroDocumento", required = false) String nroDocumento,
        @RequestParam(value = "idCotizacion", required = false) Long idCotizacion,
        @RequestParam(value = "idDetalle", required = false) Long idDetalle,
        @RequestParam(value = "idGastoDeVentas", required = false) Long idGastoDeVentas,
        @RequestParam(value = "descripcion", required = false) String descripcion,
        @RequestParam(value = "costo", required = false) Double costo,
        Model model
    ){
        model.addAttribute("accion", "actualizar");
        
        // Mostrar una lista de todos los nroDocumentos que hayan cotizado
        List<String> nroDocumentos = cotizacionService.getNroDocumentosConGastos();
        /*List<String> nroDocumentos = cotizacionService.getCotizaciones()
                .stream().map(c -> c.getNroDocumento()).distinct().toList();*/
        model.addAttribute("nroDocumentos", nroDocumentos);
        
        // Si se escogió un nroDocumento, mostrar sus cotizaciones asociadas
        if (nroDocumento != null){
            List<Cotizacion> cotizaciones = cotizacionService.getIdCotizacionesConGastosPorNroDocumento(nroDocumento);
            model.addAttribute("cotizaciones", cotizaciones);
            model.addAttribute("nroDocumentoSeleccionado", nroDocumento);
            
            // Si se escogió un idCotización, mostrar los detalles de la cotización
            if (idCotizacion != null){
                model.addAttribute("idCotizacionSeleccionado", idCotizacion);
                List<Detalle> detalles = detalleService.getDetallesConGastosPorCotizacion(idCotizacion);
                model.addAttribute("detalles", detalles);
                
                // Si se escogió un idDetalle, mostrar los gastos o ítems del detalle
                if (idDetalle != null){
                    model.addAttribute("idDetalleSeleccionado", idDetalle);
                    List<GastoDeVentas> gastos = gastoService.getGastosXDetalle(idDetalle);
                    model.addAttribute("gastos", gastos);
                    
                    // Si se escogió un idGastoDeVentas, mostrar sus atributos, si existen
                    if (idGastoDeVentas != null){
                        model.addAttribute("idGastoSeleccionado", idGastoDeVentas);
                        gastoService.getGastoById(idGastoDeVentas).ifPresent(gasto ->{
                            model.addAttribute("descripcion_gasto", gasto.getDescripcionGasto());
                            model.addAttribute("gasto", gasto.getCosto());
                        });
                    }
                }
            }
        }
        
        model.addAttribute("descripcion", descripcion);
        model.addAttribute("costo", costo);
        return "formGastoDetalle";
    }
     
    /*@PostMapping
    public GastoDeVentas insertGasto(@RequestBody GastoDeVentas gastodeventas) {
        return gastodeventarepository.save(gastodeventas);
    }*/
    
    /*@PutMapping("/{id}")
    public GastoDeVentas updateGasto(@PathVariable Long id, @RequestBody GastoDeVentas gastoventadetail) {
        GastoDeVentas gastoventa = gastodeventarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el gasto con id: " + id));
        gastoventa.setDescripcionGasto(gastoventadetail.getDescripcionGasto());
        gastoventa.setIdDetalle(gastoventadetail.getIdDetalle());
        gastoventa.setCosto(gastoventadetail.getCosto());
        return gastodeventarepository.save(gastoventa);
    }*/

    @DeleteMapping("/{id}")
    public String deleteGasto(@PathVariable Long id) {
        GastoDeVentas gastoventa = gastodeventarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el gasto con id: " + id));
        gastodeventarepository.delete(gastoventa);
        return "Se elimino el gasto con id: " + id;
    }
}
