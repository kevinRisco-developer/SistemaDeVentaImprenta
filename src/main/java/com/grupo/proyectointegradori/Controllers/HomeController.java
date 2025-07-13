package com.grupo.proyectointegradori.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo.proyectointegradori.repository.CategoriaRepository;
import com.grupo.proyectointegradori.repository.CotizacionRepository;
import com.grupo.proyectointegradori.repository.UsuarioRepository;
import com.grupo.proyectointegradori.entity.Categoria;
import com.grupo.proyectointegradori.entity.Cotizacion;
import com.grupo.proyectointegradori.entity.Usuario;

@Controller
public class HomeController {
    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository userRepository;

    // it GOES here!!
    @GetMapping("/")
    public String home(Model model) {
        List<Object[]> listCtz = cotizacionRepository.getTotalCotizaciones();
        model.addAttribute("listaCotizaciones", listCtz);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/verifyaccess")
    public String loginVendYAdm(@RequestParam("username") String user,
            @RequestParam("password") String pass, Model model) {

        Usuario usuario = userRepository.getUserByUserAndPass(user, pass);
        if (usuario == null) {
            model.addAttribute("error", "usuario o contraseña no correctos");
            return "login";
        }
        String typeUser = usuario.getTipoUsuario();
        String id = usuario.getNroDocumento();

        if (typeUser.equals("Vendedor")) {
            model.addAttribute("id", id); // el nro de documento del vendedor
            // send the list of Clients
            List<Usuario> listaCliente = userRepository.showOnlyClients();
            model.addAttribute("listaCliente", listaCliente);
            // pass the list of Categoria
            List<Categoria> listCat = categoriaRepository.findAll();
            model.addAttribute("listaCategoria", listCat);
            // I need the list of Cotizacion
            List<Cotizacion> listCotizacion = cotizacionRepository.mostrarCotizacionesPorVendedor(id);
            model.addAttribute("listaCotizacion", listCotizacion);
            return "indexVendedor";
        } else if (typeUser.equals("Admin")) {
            List<Object[]> listCtz = cotizacionRepository.getTotalCotizaciones();
            model.addAttribute("listaCotizaciones", listCtz);
            return "home";
        } else if (typeUser.equals("Cliente")) {
            model.addAttribute("usuario", usuario);
            return "indexCliente";
        } else {
            return "";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar";
    }

    @GetMapping("/categorias")
    public String categorias(Model model) {
        List<Object[]> listaCategorias = categoriaRepository.getCategorias();
        model.addAttribute("categorias", listaCategorias);
        return "categorias";
    }

    @GetMapping("/cotizacion/ver/{id}")
    public String cotizaciones(@PathVariable Long id, Model model) {
        List<Object[]> listDetallesCotizacion = cotizacionRepository.getDetallesCotizaciones(id);
        double totalPrecioPorCotizacion = cotizacionRepository.getPrecioPorCotizacion(id);
        String nombreVendedor = cotizacionRepository.getNombreVendedor(id);
        model.addAttribute("listaDetallesCotizaciones", listDetallesCotizacion);
        model.addAttribute("precioTotal", totalPrecioPorCotizacion);
        model.addAttribute("nombreVendedor", nombreVendedor);
        return "Cotizaciones";
    }

}
