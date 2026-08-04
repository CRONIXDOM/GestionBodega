package com.bodega.controlweb.interceptor;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bodega.controlweb.service.IUsuarioRolService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Centraliza la protección de todas las rutas del front: exige sesión iniciada
 * y restringe las rutas administrativas (Usuario, Rol, UsuarioRol, Credenciales)
 * a usuarios con el rol "Administrador".
 */
@Component
public class SesionInterceptor implements HandlerInterceptor {

	private static final Set<String> RUTAS_ADMIN = Set.of("/usuario", "/rol", "/usuariorol", "/credenciales");

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
		boolean esRutaAdmin = RUTAS_ADMIN.stream().anyMatch(path::startsWith);
		if (esRutaAdmin) {
			@SuppressWarnings("unchecked")
			List<String> roles = (List<String>) session.getAttribute("rolesLogueado");
			boolean esAdmin = roles != null && roles.stream().anyMatch(r -> r.equalsIgnoreCase("Administrador"));

			boolean sinRolesAsignadosAun;
			try {
				sinRolesAsignadosAun = servicioUsuarioRol.listarUsuarioRol().isEmpty();
			} catch (Exception ex) {
				// si el backend no responde, no bloqueamos por este chequeo de bootstrap
				sinRolesAsignadosAun = false;
			}
			// Bootstrap: si todavía no existe NINGÚN UsuarioRol en el sistema, se permite el acceso
			// (así el único usuario inicial puede crear usuarios/roles y asignarse el primero sin quedar bloqueado).
			// En cuanto exista al menos un UsuarioRol, solo entra quien tenga rol "Administrador".
			if (!esAdmin && !sinRolesAsignadosAun) {
				session.setAttribute("mensajeError", "No tienes permisos para acceder a esa sección.");
				response.sendRedirect(request.getContextPath() + "/");
				return false;
			}
		}
		return true;
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
