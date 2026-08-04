package com.bodega.controlweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bodega.controlweb.model.dto.request.CredencialesRequestDto;
import com.bodega.controlweb.model.dto.response.CredencialesResponseDto;
import com.bodega.controlweb.service.ICredencialesService;

@Controller
@RequestMapping("/recuperar")
public class RecuperacionController {

	private final ICredencialesService servicioCredenciales;

	public RecuperacionController(ICredencialesService servicioCredenciales) {
		this.servicioCredenciales = servicioCredenciales;
	}

	@GetMapping
	public String mostrarFormulario() {
		return "Login/recuperar";
	}

	@PostMapping("/buscar")
	public String buscarCuenta(@RequestParam String correo, Model model) {
		Optional<CredencialesResponseDto> cuenta = buscarPorCorreo(correo);

		if (cuenta.isEmpty()) {
			model.addAttribute("error", "No se encontró ninguna cuenta con ese correo.");
			return "Login/recuperar";
		}

		model.addAttribute("cuenta", cuenta.get());
		return "Login/recuperar";
	}

	@PostMapping("/restablecer")
	public String restablecerContrasena(@RequestParam Integer idCredenciales, @RequestParam String correo,
			@RequestParam String nuevaContrasena, @RequestParam String confirmarContrasena, Model model,
			RedirectAttributes redirectAttributes) {

		if (nuevaContrasena == null || nuevaContrasena.isBlank() || !nuevaContrasena.equals(confirmarContrasena)) {
			model.addAttribute("error", "Las contraseñas ingresadas no coinciden.");
			model.addAttribute("cuenta", servicioCredenciales.buscarCredencialesId(idCredenciales));
			return "Login/recuperar";
		}

		CredencialesResponseDto actual = servicioCredenciales.buscarCredencialesId(idCredenciales);
		if (actual == null || actual.getCorreo() == null || !actual.getCorreo().equalsIgnoreCase(correo.trim())) {
			model.addAttribute("error", "No se pudo validar la cuenta, vuelve a intentarlo.");
			return "Login/recuperar";
		}

		CredencialesRequestDto actualizar = new CredencialesRequestDto();
		actualizar.setIdCredenciales(actual.getIdCredenciales());
		actualizar.setUsuario(actual.getUsuario());
		actualizar.setCorreo(actual.getCorreo());
		actualizar.setContrasena(nuevaContrasena);
		servicioCredenciales.guardarCredenciales(actualizar);

		redirectAttributes.addFlashAttribute("exito",
				"Contraseña actualizada. Tu usuario es \"" + actual.getUsuario() + "\", ya puedes iniciar sesión.");
		return "redirect:/login";
	}

	private Optional<CredencialesResponseDto> buscarPorCorreo(String correo) {
		if (correo == null || correo.isBlank()) {
			return Optional.empty();
		}
		List<CredencialesResponseDto> todas = servicioCredenciales.listarCredenciales();
		return todas.stream().filter(c -> c.getCorreo() != null && c.getCorreo().equalsIgnoreCase(correo.trim()))
				.findFirst();
	}
}
