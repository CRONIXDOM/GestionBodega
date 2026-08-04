package com.bodega.controlweb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bodega.controlweb.model.dto.response.CredencialesResponseDto;
import com.bodega.controlweb.model.dto.response.RolResponseDto;
import com.bodega.controlweb.model.dto.response.UsuarioResponseDto;
import com.bodega.controlweb.model.dto.response.UsuarioRolResponseDto;
import com.bodega.controlweb.service.ICredencialesService;
import com.bodega.controlweb.service.IRolService;
import com.bodega.controlweb.service.IUsuarioRolService;
import com.bodega.controlweb.service.IUsuarioService;
import com.bodega.controlweb.util.CatalogoModulos;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private ICredencialesService servicioCredenciales;
	@Autowired
	private IUsuarioService servicioUsuario;
	@Autowired
	private IUsuarioRolService servicioUsuarioRol;
	@Autowired
	private IRolService servicioRol;

	@GetMapping("/login")
	public String mostrarLogin() {
		return "/Login/login";
	}

	@PostMapping("/login")
	public String procesarLogin(@RequestParam String usuario, @RequestParam String contrasena,
			HttpSession session, Model model) {
		List<CredencialesResponseDto> candidatos = servicioCredenciales.buscarPorUsuario(usuario);
		Optional<CredencialesResponseDto> credencial = candidatos.stream()
				.filter(c -> c.getContrasena() != null && c.getContrasena().equals(contrasena))
				.findFirst();

		if (credencial.isEmpty()) {
			model.addAttribute("error", "Usuario o contraseña incorrectos");
			return "/Login/login";
		}

		session.setAttribute("usuarioLogueado", credencial.get().getUsuario());
		resolverPermisos(credencial.get().getUsuario(), session);
		return "redirect:/";
	}

	private void resolverPermisos(String nombreUsuario, HttpSession session) {
		List<String> roles = new ArrayList<>();
		Set<String> modulosPermitidos = new HashSet<>();
		try {
			List<UsuarioResponseDto> usuarios = servicioUsuario.listarUsuario();
			Optional<UsuarioResponseDto> usuario = usuarios.stream()
					.filter(u -> u.getNombreUsuario() != null && u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
					.findFirst();

			if (usuario.isPresent()) {
				session.setAttribute("idUsuarioLogueado", usuario.get().getIdUsuario());
				List<Integer> idsRol = servicioUsuarioRol.listarUsuarioRol().stream()
						.filter(ur -> usuario.get().getIdUsuario().equals(ur.getIdUsuario()))
						.map(UsuarioRolResponseDto::getIdRol)
						.toList();
				List<RolResponseDto> rolesAsignados = servicioRol.listarRol().stream()
						.filter(r -> idsRol.contains(r.getIdRol()))
						.toList();
				roles = rolesAsignados.stream().map(RolResponseDto::getNombreRol).toList();

				// un rol con "modulos" en null nunca fue configurado con el checklist
				// (rol creado antes de esta funcionalidad, o migrado desde datos viejos):
				// se le mantiene acceso total para no romper instalaciones existentes.
				// Un rol con "modulos" en cadena vacia SI fue configurado a proposito sin
				// marcar nada, y ese rol no habilita ningun modulo.
				boolean tieneRolSinConfigurar = rolesAsignados.stream().anyMatch(r -> r.getModulos() == null);
				if (tieneRolSinConfigurar) {
					modulosPermitidos.addAll(CatalogoModulos.todasLasClaves());
				} else {
					for (RolResponseDto rol : rolesAsignados) {
						if (rol.getModulos() != null && !rol.getModulos().isBlank()) {
							modulosPermitidos.addAll(Arrays.asList(rol.getModulos().split(",")));
						}
					}
				}
			}
		} catch (Exception ex) {
			// si el backend de roles falla, el login (usuario/contraseña) no debe verse afectado;
			// simplemente se deja al usuario sin roles ni modulos resueltos.
			roles = new ArrayList<>();
			modulosPermitidos = new HashSet<>();
		}
		session.setAttribute("rolesLogueado", roles);
		session.setAttribute("modulosPermitidos", modulosPermitidos);
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
