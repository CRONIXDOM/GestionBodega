package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bodega.controlweb.model.dto.response.InventarioResponseDto;
import com.bodega.controlweb.service.IInventarioService;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.service.ISolicitudService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private IInventarioService servicioInventario;
	@Autowired
	private ILoteService servicioLote;
	@Autowired
	private ISolicitudService servicioSolicitud;

	@GetMapping("/")
	public String home(HttpSession session, Model model) {
		if (session.getAttribute("usuarioLogueado") == null) {
			return "redirect:/login";
		}

		java.util.List<InventarioResponseDto> inventario = servicioInventario.listarInventario();
		int productosDistintos = inventario.size();
		int unidadesDisponibles = inventario.stream().mapToInt(i -> i.getCantidadDisponible() == null ? 0 : i.getCantidadDisponible()).sum();
		int lotesActivos = servicioLote.listarLote().size();
		int solicitudesRegistradas = servicioSolicitud.listarSolicitud().size();

		model.addAttribute("productosDistintos", productosDistintos);
		model.addAttribute("unidadesDisponibles", unidadesDisponibles);
		model.addAttribute("lotesActivos", lotesActivos);
		model.addAttribute("solicitudesRegistradas", solicitudesRegistradas);

		return "/Home/home";
	}
}
