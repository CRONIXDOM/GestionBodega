package com.bodega.controlweb.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.CredencialesRequestDto;
import com.bodega.controlweb.model.dto.request.UsuarioRequestDto;
import com.bodega.controlweb.model.dto.request.UsuarioRolRequestDto;
import com.bodega.controlweb.model.dto.response.UsuarioResponseDto;
import com.bodega.controlweb.model.dto.response.UsuarioRolResponseDto;
import com.bodega.controlweb.service.ICredencialesService;
import com.bodega.controlweb.service.IRolService;
import com.bodega.controlweb.service.IUsuarioRolService;
import com.bodega.controlweb.service.IUsuarioService;
import com.bodega.controlweb.util.MensajesError;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService servicioAPI;
    @Autowired
    private ICredencialesService servicioCredenciales;
    @Autowired
    private IUsuarioRolService servicioUsuarioRol;
    @Autowired
    private IRolService servicioRol;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listausuario", servicioAPI.listarUsuario());
        return "/Usuario/listarusuario";
    }

    @GetMapping("/nuevo")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioRequestDto());
        model.addAttribute("opcionesRol", servicioRol.listarOpciones());
        return "/Usuario/crearusuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute UsuarioRequestDto usuario, Model model) {
        try {
            return intentarGuardar(usuario);
        } catch (Exception ex) {
            // se devuelve el formulario con lo que el usuario ya habia escrito, en vez
            // de mandarlo a la pagina de error generica de Spring.
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", MensajesError.extraer(ex));
            model.addAttribute("opcionesRol", servicioRol.listarOpciones());
            return "/Usuario/crearusuario";
        }
    }

    private String intentarGuardar(UsuarioRequestDto usuario) {
        boolean esNuevo = usuario.getIdUsuario() == null;
        UsuarioResponseDto guardado = servicioAPI.guardarUsuario(usuario);

        if (esNuevo) {
            if (usuario.getContrasena() != null && !usuario.getContrasena().isBlank()) {
                CredencialesRequestDto credenciales = new CredencialesRequestDto();
                credenciales.setUsuario(usuario.getNombreUsuario());
                credenciales.setCorreo(usuario.getCorreo());
                credenciales.setContrasena(usuario.getContrasena());
                servicioCredenciales.guardarCredenciales(credenciales);
            }
            if (usuario.getIdRol() != null) {
                asignarRol(guardado.getIdUsuario(), usuario.getIdRol(), null);
            }
        } else if (usuario.getIdRol() != null) {
            Optional<UsuarioRolResponseDto> asignacionActual = buscarAsignacion(guardado.getIdUsuario());
            Integer idUsuarioRolExistente = asignacionActual.map(UsuarioRolResponseDto::getIdUsuarioRol).orElse(null);
            if (asignacionActual.isEmpty() || !usuario.getIdRol().equals(asignacionActual.get().getIdRol())) {
                asignarRol(guardado.getIdUsuario(), usuario.getIdRol(), idUsuarioRolExistente);
            }
        }
        return "redirect:/usuario";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        UsuarioResponseDto encontrado = servicioAPI.buscarUsuarioId(id);
        UsuarioRequestDto usuario = new UsuarioRequestDto();
        usuario.setIdUsuario(encontrado.getIdUsuario());
        usuario.setNombreUsuario(encontrado.getNombreUsuario());
        usuario.setApellidoUsuario(encontrado.getApellidoUsuario());
        usuario.setEstado(encontrado.getEstado());
        buscarAsignacion(id).ifPresent(a -> usuario.setIdRol(a.getIdRol()));

        model.addAttribute("usuario", usuario);
        model.addAttribute("opcionesRol", servicioRol.listarOpciones());
        return "/Usuario/crearusuario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        servicioAPI.eliminarUsuario(id);
        return "redirect:/usuario";
    }

    private Optional<UsuarioRolResponseDto> buscarAsignacion(Integer idUsuario) {
        List<UsuarioRolResponseDto> asignaciones = servicioUsuarioRol.listarUsuarioRol();
        return asignaciones.stream().filter(a -> idUsuario.equals(a.getIdUsuario())).findFirst();
    }

    private void asignarRol(Integer idUsuario, Integer idRol, Integer idUsuarioRolExistente) {
        UsuarioRolRequestDto usuarioRol = new UsuarioRolRequestDto();
        usuarioRol.setIdUsuarioRol(idUsuarioRolExistente);
        usuarioRol.setIdUsuario(idUsuario);
        usuarioRol.setIdRol(idRol);
        usuarioRol.setFechaAsignacion(LocalDate.now());
        servicioUsuarioRol.guardarUsuarioRol(usuarioRol);
    }
}
