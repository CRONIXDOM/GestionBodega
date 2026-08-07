package com.andiana.api.aplicacion.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Las comprobaciones que se repiten en todos los casos de uso. Java puro: no
 * usa validadores de ningun framework para que las reglas del negocio vivan
 * enteras aqui dentro.
 */
public final class Validaciones {

	private Validaciones() {
	}

	/** Recorta, junta los espacios de en medio y pasa a mayusculas. */
	public static String normalizar(String valor) {
		if (valor == null) {
			return null;
		}
		String limpio = valor.trim().replaceAll("\\s+", " ");
		return limpio.isEmpty() ? null : limpio.toUpperCase();
	}

	public static void obligatorio(String valor, String campo) {
		if (valor == null || valor.isBlank()) {
			throw new RuntimeException("El campo " + campo + " es obligatorio");
		}
	}

	public static void obligatorio(Object valor, String campo) {
		if (valor == null) {
			throw new RuntimeException("El campo " + campo + " es obligatorio");
		}
	}

	public static void mayorQueCero(Integer valor, String campo) {
		obligatorio(valor, campo);
		if (valor <= 0) {
			throw new RuntimeException("El campo " + campo + " debe ser mayor que cero");
		}
	}

	public static void mayorQueCero(BigDecimal valor, String campo) {
		obligatorio(valor, campo);
		if (valor.signum() <= 0) {
			throw new RuntimeException("El campo " + campo + " debe ser mayor que cero");
		}
	}

	/** El valor tiene que ser uno de la lista; si no, se dice cuales valen. */
	public static void unoDe(String valor, String campo, String... admitidos) {
		List<String> lista = Arrays.asList(admitidos);
		if (valor == null || !lista.contains(valor)) {
			throw new RuntimeException(
					"El campo " + campo + " debe ser uno de estos valores: " + String.join(", ", lista));
		}
	}

	public static void enRango(BigDecimal valor, String campo, double minimo, double maximo) {
		obligatorio(valor, campo);
		if (valor.doubleValue() < minimo || valor.doubleValue() > maximo) {
			throw new RuntimeException("El campo " + campo + " debe estar entre " + minimo + " y " + maximo);
		}
	}

	/**
	 * Las cantidades vienen de la base con los decimales de la columna
	 * (NUMERIC(12,2)), asi que "800" llega como "800.00". Para los mensajes que
	 * lee una persona se quitan los ceros que no aportan nada.
	 */
	public static String legible(BigDecimal valor) {
		if (valor == null) {
			return "0";
		}
		return valor.stripTrailingZeros().toPlainString();
	}

	/**
	 * Rechaza el valor si ya lo usa OTRO registro. Al editar hay que excluir el
	 * propio registro, o guardarlo sin cambios chocaria consigo mismo.
	 */
	public static <T> void noRepetido(List<T> existentes, Function<T, Integer> obtenerId,
			Function<T, String> obtenerValor, Integer idActual, String valor, String descripcion) {
		if (valor == null) {
			return;
		}
		boolean repetido = existentes.stream()
				.filter(otro -> idActual == null || !idActual.equals(obtenerId.apply(otro)))
				.anyMatch(otro -> valor.equalsIgnoreCase(obtenerValor.apply(otro)));
		if (repetido) {
			throw new RuntimeException("Ya existe " + descripcion + " \"" + valor + "\"");
		}
	}
}
