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
		/** No se muestra en la tabla ni se puede escribir: es la clave. */
		ID,
		TEXTO, NUMERO, DECIMAL, FECHA,
		/** Lista cerrada de valores. */
		LISTA,
		/** Apunta a otra seccion: se elige de un desplegable y se muestra su nombre. */
		RELACION,
		SI_NO
	}

	public static Campo id(String nombre) {
		return new Campo(nombre, "ID", Tipo.ID, false, List.of(), null);
	}

	public static Campo texto(String nombre, String etiqueta) {
		return new Campo(nombre, etiqueta, Tipo.TEXTO, true, List.of(), null);
	}

	public static Campo textoOpcional(String nombre, String etiqueta) {
		return new Campo(nombre, etiqueta, Tipo.TEXTO, false, List.of(), null);
	}

	public static Campo numero(String nombre, String etiqueta) {
		return new Campo(nombre, etiqueta, Tipo.NUMERO, true, List.of(), null);
	}

	public static Campo decimal(String nombre, String etiqueta) {
		return new Campo(nombre, etiqueta, Tipo.DECIMAL, true, List.of(), null);
	}

	public static Campo fecha(String nombre, String etiqueta) {
		return new Campo(nombre, etiqueta, Tipo.FECHA, true, List.of(), null);
	}

	public static Campo lista(String nombre, String etiqueta, String... opciones) {
		return new Campo(nombre, etiqueta, Tipo.LISTA, true, List.of(opciones), null);
	}

	public static Campo relacion(String nombre, String etiqueta, String seccion) {
		return new Campo(nombre, etiqueta, Tipo.RELACION, true, List.of(), seccion);
	}

	public static Campo siNo(String nombre, String etiqueta) {
		return new Campo(nombre, etiqueta, Tipo.SI_NO, false, List.of(), null);
	}

	/** El stock no se escribe: lo calculan los movimientos de inventario. */
	public static Campo soloLectura(String nombre, String etiqueta) {
		return new Campo(nombre, etiqueta, Tipo.ID, false, List.of(), null);
	}
}
