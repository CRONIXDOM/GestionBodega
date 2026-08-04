package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

import com.bodega.controlweb.model.dto.response.InventarioResponseDto;
import com.bodega.controlweb.model.dto.response.ProductoResponseDto;
import com.bodega.controlweb.service.IInventarioService;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.service.IProductoService;
import com.bodega.controlweb.service.ISolicitudService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	private static final int MAX_PRODUCTOS_RECIENTES = 6;

	@Autowired
	private IInventarioService servicioInventario;
	@Autowired
	private ILoteService servicioLote;
	@Autowired
	private ISolicitudService servicioSolicitud;
	@Autowired
	private IProductoService servicioProducto;

	@GetMapping("/")
	public String home(HttpSession session, Model model) {
		if (session.getAttribute("usuarioLogueado") == null) {
			return "redirect:/login";
		}

		List<InventarioResponseDto> inventario = servicioInventario.listarInventario();
		int productosDistintos = inventario.size();
		int unidadesDisponibles = inventario.stream().mapToInt(i -> i.getCantidadDisponible() == null ? 0 : i.getCantidadDisponible()).sum();
		int lotesActivos = servicioLote.listarLote().size();
		int solicitudesRegistradas = servicioSolicitud.listarSolicitud().size();

		model.addAttribute("productosDistintos", productosDistintos);
		model.addAttribute("unidadesDisponibles", unidadesDisponibles);
		model.addAttribute("lotesActivos", lotesActivos);
		model.addAttribute("solicitudesRegistradas", solicitudesRegistradas);

		// orden de registro: el idProducto autoincremental refleja el orden real en
		// que se fueron dando de alta, del mas reciente al mas antiguo.
		List<ProductoResponseDto> productosRecientes = servicioProducto.listarProducto().stream()
				.sorted(Comparator.comparing(ProductoResponseDto::getIdProducto).reversed())
				.limit(MAX_PRODUCTOS_RECIENTES)
				.toList();
		model.addAttribute("productosRecientes", productosRecientes);

		return "/Home/home";
	}
}
