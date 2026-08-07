package com.andiana.web.catalogo;

import java.util.List;

/**
 * Una pantalla de gestion completa.
 *
 * @param clave         lo que va en la URL: /producto, /receta...
 * @param rutaApi       el mismo recurso en andianaApi
 * @param titulo        el nombre en plural, para el menu y la cabecera
 * @param singular      el nombre en singular, para los botones y los mensajes
 * @param tabla         la tabla de la base a la que corresponde, como referencia
 * @param clavePrimaria nombre del campo id en la API
 * @param etiqueta      campo que se usa para nombrar una fila cuando otra
 *                      pantalla la referencia (por ejemplo el numero de lote)
 * @param campos        todo lo que se muestra y se pide
 */
public record Seccion(String clave, String rutaApi, String titulo, String singular, String tabla,
		String clavePrimaria, String etiqueta, List<Campo> campos) {

	/** Los campos que el usuario puede escribir en el formulario. */
	public List<Campo> camposEditables() {
		return campos.stream().filter(c -> c.tipo() != Campo.Tipo.SOLO_LECTURA).toList();
	}

	/** Las secciones de las que esta depende, para cargar sus desplegables. */
	public List<String> seccionesRelacionadas() {
		return campos.stream().map(Campo::seccionRelacionada).filter(java.util.Objects::nonNull).distinct().toList();
	}
}
