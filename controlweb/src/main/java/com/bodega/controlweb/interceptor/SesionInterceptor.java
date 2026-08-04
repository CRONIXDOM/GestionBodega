package com.bodega.controlweb.interceptor;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bodega.controlweb.service.IUsuarioRolService;
import com.bodega.controlweb.util.CatalogoModulos;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Centraliza la protección de todas las rutas del front: exige sesión iniciada
 * y restringe cada sección (Productos, Lotes, Usuarios, etc.) a los roles que
 * la tengan habilitada en su checklist de módulos.
 */
@Component
public class SesionInterceptor implements HandlerInterceptor {

	@Autowired
	private IUsuarioRolService servicioUsuarioRol;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}

		String path = request.getRequestURI().substring(request.getContextPath().length());
		String modulo = extraerModulo(path);
		if (modulo == null || !CatalogoModulos.todasLasClaves().contains(modulo)) {
			// no corresponde a ningun modulo controlado (p.ej. la propia raiz "/"): siempre se permite
			return true;
		}

		@SuppressWarnings("unchecked")
		Set<String> modulosPermitidos = (Set<String>) session.getAttribute("modulosPermitidos");
		boolean tieneAcceso = modulosPermitidos != null && modulosPermitidos.contains(modulo);

		boolean sinRolesAsignadosAun;
		try {
			sinRolesAsignadosAun = servicioUsuarioRol.listarUsuarioRol().isEmpty();
		} catch (Exception ex) {
			// si el backend no responde, no bloqueamos por este chequeo de bootstrap
			sinRolesAsignadosAun = false;
		}
		// Bootstrap: si todavía no existe NINGÚN UsuarioRol en el sistema, se permite el acceso
		// (así el único usuario inicial puede crear usuarios/roles y asignarse el primero sin quedar bloqueado).
		if (!tieneAcceso && !sinRolesAsignadosAun) {
			session.setAttribute("mensajeError", "No tienes permisos para acceder a esa sección.");
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}
		return true;
	}

	private String extraerModulo(String path) {
		if (path == null || path.isBlank() || "/".equals(path)) {
			return null;
		}
		String sinBarraInicial = path.startsWith("/") ? path.substring(1) : path;
		int siguienteBarra = sinBarraInicial.indexOf('/');
		String primerSegmento = siguienteBarra >= 0 ? sinBarraInicial.substring(0, siguienteBarra) : sinBarraInicial;
		return primerSegmento.toLowerCase();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) throws Exception {
		// se limpia después de que la vista ya se renderizó, así el mensaje
		// (seteado antes del redirect a "/") se muestra una única vez.
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("mensajeError");
		}
	}
}
