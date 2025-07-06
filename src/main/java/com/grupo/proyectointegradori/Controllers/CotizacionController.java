package com.grupo.proyectointegradori.Controllers;

import org.springframework.ui.Model;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grupo.proyectointegradori.DTO.DetalleDTO;
import com.grupo.proyectointegradori.entity.Cotizacion;
import com.grupo.proyectointegradori.entity.Detalle;
import com.grupo.proyectointegradori.entity.EmailSenderService;
import com.grupo.proyectointegradori.entity.Usuario;
import com.grupo.proyectointegradori.repository.CotizacionRepository;
import com.grupo.proyectointegradori.repository.DetalleRepository;
import com.grupo.proyectointegradori.repository.UsuarioRepository;

@Controller
@RequestMapping("/cotizacion")
public class CotizacionController {
    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DetalleRepository detalleRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping
    public List<Cotizacion> getAllCotizacion() {
        return cotizacionRepository.findAll();
    }

    @PostMapping("/insertarConDetalles")
    @ResponseBody
    public ResponseEntity<?> guardarCotizacionConDetalles(@RequestBody CotizacionConDetallesDTO dto) {
        try {
            // Crear la cotización
            Usuario cliente = usuarioRepository.findById(dto.getCotizacion().getNombreCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            Cotizacion cot = new Cotizacion();
            cot.setNroDocumento(cliente.getNroDocumento());
            cot.setFecha(dto.getCotizacion().getFecha());
            cot.setEstado("");
            cot.setNroDocVendedor(dto.getCotizacion().getIdVendedor());
            cot.setDiasCredito(dto.getCotizacion().getDays());
            cot.setIdVenta("");

            Cotizacion saved = cotizacionRepository.save(cot);

            // Guardar los detalles
            for (DetalleDTO detalleDto : dto.getDetalles()) {
                Detalle det = new Detalle();
                det.setIdCotizacion(saved.getIdCotizacion()); // ¡clave!
                det.setCantidad(detalleDto.getCantidad());
                det.setDescripcion(detalleDto.getDescripcion());
                det.setPrecio(detalleDto.getPrecio());
                det.setIdCategoria(detalleDto.getIdCategoria());
                detalleRepository.save(det);
            }

            return ResponseEntity.ok("Guardado con éxito");

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Error al guardar: " + ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Cotizacion getCotizacionById(@PathVariable Long id) {
        return cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotizacion con id " + id + " no encontrado"));
    }

    @PutMapping("/{id}")
    public Cotizacion updateCotizacion(@PathVariable Long id, @RequestBody Cotizacion cotizacionDetail) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotizacion con id " + id + " no encontrado"));
        cotizacion.setNroDocumento(cotizacionDetail.getNroDocumento());
        cotizacion.setFecha(cotizacionDetail.getFecha());
        cotizacion.setEstado(cotizacionDetail.getEstado());
        cotizacion.setIdVenta(cotizacionDetail.getIdVenta());
        return cotizacionRepository.save(cotizacion);
    }

    @DeleteMapping("/{id}")
    public String deleteCotizacion(@PathVariable Long id) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotizacion con id " + id + " no encontrado"));
        cotizacionRepository.delete(cotizacion);
        return "Cotizacion con id: " + id + " eliminada!";
    }

    @PostMapping("/enviarCorreo")
    public String enviarCorreo(@RequestParam("descripcionPreCotizacion") String descripcion,
            @RequestParam("nroDocumento") String nroDocumento,
            Model model) {
        Usuario usuario = usuarioController.getUsuarioById(nroDocumento);
        if (usuario != null) {
            String cuerpo = """
                    El cliente escribió: %s

                    Datos del cliente:
                    - Nro Documento: %s
                    - Nombres Completos: %s %s
                    - Tipo de Documento: %s
                    - Correo: %s
                    """.formatted(
                    descripcion,
                    usuario.getNroDocumento(),
                    usuario.getNombres(),
                    usuario.getApellidos(),
                    usuario.getTipoDocumento(),
                    usuario.getCorreo());
            // Enviar correo
            emailSenderService.sendEmail(
                    "coorpkdrisco.002@gmail.com", // destino real o del usuario
                    "PRE-COTIZACIÓN",
                    cuerpo);
        }
        model.addAttribute("usuario", usuario);
        return "indexCliente";
    }

}
