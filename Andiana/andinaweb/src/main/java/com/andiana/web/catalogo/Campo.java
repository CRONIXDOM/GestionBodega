package com.andiana.web.catalogo;

import java.util.List;

/**
 * Un campo de una pantalla: como se llama en la API, como se le muestra al
 * usuario y de que tipo es. Con esta descripcion basta para dibujar tanto la
 * columna de la tabla como el control del formulario.
 */
public record Campo(String nombre, String etiqueta, Tipo tipo, boolean obligatorio,
		List<String> opciones, String seccionRelacionada) {

	public enum Tipo {
		/** No se puede escribir: o es la clave, o lo calcula el sistema. */
		SOLO_LECTURA,
		TEXTO, NUMERO, DECIMAL, FECHA, FECHA_HORA,
		/** Lista cerrada de valores, la misma que admite el CHECK de la tabla. */
		LISTA,
		/** Apunta a otra seccion: se elige de un desplegable y se muestra su nombre. */
		RELACION,
		SI_NO
	}

	private static Campo de(String nombre, String etiqueta, Tipo tipo, boolean obligatorio) {
		return new Campo(nombre, etiqueta, tipo, obligatorio, List.of(), null);
	}

	public static Campo id(String nombre) {
		return de(nombre, "ID", Tipo.SOLO_LECTURA, false);
	}

	/** Un dato que se muestra pero no se escribe, como el stock o la unidad. */
	public static Campo soloLectura(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.SOLO_LECTURA, false);
	}

	public static Campo texto(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.TEXTO, true);
	}

	public static Campo textoOpcional(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.TEXTO, false);
	}

	public static Campo numero(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.NUMERO, true);
	}

	public static Campo decimal(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.DECIMAL, true);
	}

	public static Campo decimalOpcional(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.DECIMAL, false);
	}

	public static Campo fecha(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.FECHA, true);
	}

	public static Campo fechaHora(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.FECHA_HORA, true);
	}

	public static Campo fechaHoraOpcional(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.FECHA_HORA, false);
	}

	public static Campo lista(String nombre, String etiqueta, String... opciones) {
		return new Campo(nombre, etiqueta, Tipo.LISTA, true, List.of(opciones), null);
	}

	public static Campo relacion(String nombre, String etiqueta, String seccion) {
		return new Campo(nombre, etiqueta, Tipo.RELACION, true, List.of(), seccion);
	}

	public static Campo siNo(String nombre, String etiqueta) {
		return de(nombre, etiqueta, Tipo.SI_NO, false);
	}
}
