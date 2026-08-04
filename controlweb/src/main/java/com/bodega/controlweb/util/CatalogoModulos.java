package com.bodega.controlweb.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Lista única de los módulos del sistema (debe reflejar las secciones del
 * menú lateral). Se usa para: armar el checklist de permisos al crear/editar
 * un Rol, y para que el interceptor de sesión y el propio menú sepan si el
 * usuario logueado tiene acceso a cada sección.
 */
public final class CatalogoModulos {

	public record ModuloOpcion(String clave, String etiqueta, String grupo) {
	}

	private static final List<ModuloOpcion> MODULOS = List.of(
			new ModuloOpcion("producto", "Productos", "Catálogo"),
			new ModuloOpcion("zona", "Zonas", "Catálogo"),
			new ModuloOpcion("ubicacion", "Ubicaciones", "Catálogo"),

			new ModuloOpcion("lote", "Lotes", "Inventario"),
			new ModuloOpcion("inventario", "Existencias", "Inventario"),

			new ModuloOpcion("solicitud", "Solicitudes", "Movimientos"),
			new ModuloOpcion("detallesolicitud", "Detalle Solicitud", "Movimientos"),
			new ModuloOpcion("entrega", "Entregas", "Movimientos"),
			new ModuloOpcion("detalleentrega", "Detalle Entrega", "Movimientos"),
			new ModuloOpcion("tipo", "Tipos de Movimiento", "Movimientos"),
			new ModuloOpcion("registro", "Registros", "Movimientos"),

			new ModuloOpcion("usuario", "Usuarios", "Accesos"),
			new ModuloOpcion("rol", "Roles", "Accesos"),
			new ModuloOpcion("usuariorol", "Usuario Rol", "Accesos"),
			new ModuloOpcion("credenciales", "Credenciales", "Accesos"));

	private CatalogoModulos() {
	}

	public static List<ModuloOpcion> todos() {
		return MODULOS;
	}

	public static List<String> todasLasClaves() {
		return MODULOS.stream().map(ModuloOpcion::clave).toList();
	}

	public static Map<String, List<ModuloOpcion>> agrupados() {
		Map<String, List<ModuloOpcion>> agrupados = new LinkedHashMap<>();
		for (ModuloOpcion modulo : MODULOS) {
			agrupados.computeIfAbsent(modulo.grupo(), g -> new java.util.ArrayList<>()).add(modulo);
		}
		return agrupados;
	}
}
