package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bodega.controlweb.model.dto.request.CredencialesRequestDto;
import com.bodega.controlweb.model.dto.response.CredencialesResponseDto;
import com.bodega.controlweb.service.ICredencialesService;
import com.bodega.controlweb.util.MensajesError;

import jakarta.servlet.http.HttpSession;

/**
 * Cambio obligatorio de contraseña para las cuentas que el administrador creó
 * con una clave temporal. Mientras la marca siga activa el interceptor no deja
 * salir de esta pantalla, así que la única forma de continuar es definir una
 * contraseña propia (o cerrar sesión).
 */
@Controller
@RequestMapping("/cambiar-contrasena")
public class CambioContrasenaController {

    private static final int LARGO_MINIMO = 6;

    @Autowired
    private ICredencialesService servicioCredenciales;

    @GetMapping
    public String mostrarFormulario(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "Login/cambiarcontrasena";
    }

    @PostMapping
    public String cambiar(@RequestParam String nuevaContrasena, @RequestParam String confirmarContrasena,
            HttpSession session, Model model) {

        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));

        Integer idCredenciales = (Integer) session.getAttribute("idCredencialesLogueado");
        if (idCredenciales == null) {
            model.addAttribute("error", "No se pudo identificar tu cuenta, vuelve a iniciar sesión.");
            return "Login/cambiarcontrasena";
        }
        if (nuevaContrasena == null || nuevaContrasena.isBlank()) {
            model.addAttribute("error", "La nueva contraseña es obligatoria.");
            return "Login/cambiarcontrasena";
        }
        if (nuevaContrasena.length() < LARGO_MINIMO) {
            model.addAttribute("error", "La contraseña debe tener al menos " + LARGO_MINIMO + " caracteres.");
            return "Login/cambiarcontrasena";
        }
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            model.addAttribute("error", "Las contraseñas ingresadas no coinciden.");
            return "Login/cambiarcontrasena";
        }

        try {
            CredencialesResponseDto actual = servicioCredenciales.buscarCredencialesId(idCredenciales);
            if (actual == null) {
                model.addAttribute("error", "No se encontró tu cuenta, vuelve a iniciar sesión.");
                return "Login/cambiarcontrasena";
            }
            if (nuevaContrasena.equals(actual.getContrasena())) {
                model.addAttribute("error", "La nueva contraseña debe ser distinta de la temporal.");
                return "Login/cambiarcontrasena";
            }

            CredencialesRequestDto actualizar = new CredencialesRequestDto();
            actualizar.setIdCredenciales(actual.getIdCredenciales());
            actualizar.setUsuario(actual.getUsuario());
            actualizar.setCorreo(actual.getCorreo());
            actualizar.setContrasena(nuevaContrasena);
            // deja de ser temporal: a partir de aquí el usuario entra directo al panel
            actualizar.setContrasenaTemporal(false);
            servicioCredenciales.guardarCredenciales(actualizar);
        } catch (Exception ex) {
            model.addAttribute("error", MensajesError.extraer(ex));
            return "Login/cambiarcontrasena";
        }

        session.removeAttribute("debeCambiarContrasena");
        return "redirect:/";
    }
}
