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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo.proyectointegradori.entity.Usuario;
import com.grupo.proyectointegradori.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));
        return usuario;
    }

    @PostMapping
    public Usuario insertUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable String id, @RequestBody Usuario usuarioDetail) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));
        usuario.setTipoDocumento(usuarioDetail.getTipoDocumento());
        usuario.setNombres(usuarioDetail.getNombres());
        usuario.setApellidos(usuarioDetail.getApellidos());
        usuario.setCorreo(usuarioDetail.getCorreo());
        usuario.setContrasena(usuarioDetail.getContrasena());
        usuario.setTipoUsuario(usuario.getTipoUsuario());
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));
        usuarioRepository.delete(usuario);
        return "Se elimino el usuario con id: " + id;
    }

    @GetMapping("/reporteclientecompras")
    public List<Object[]> getReporteClienteCompras(
            @RequestParam("anio") String anio,
            @RequestParam("mes") String mes) {
        return usuarioRepository.getReporteclientecompras(anio, mes);
    }
    
    @GetMapping("/reporteventacliente")
    public List<Object[]> getReporteVentaCliente(
            @RequestParam("nombre") String nombre,
            @RequestParam("anio") String anio,
            @RequestParam("mes") String mes){
                return usuarioRepository.getReporteVentaCliente(nombre,anio,mes);
    }
    
}
