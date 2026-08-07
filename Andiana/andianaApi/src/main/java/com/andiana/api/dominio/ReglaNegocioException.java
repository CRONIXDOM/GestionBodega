package com.andiana.api.dominio;

/**
 * Se lanza cuando los datos no cumplen una regla del negocio (falta un campo,
 * el stock no alcanza, el lote no esta aprobado...). Es una excepcion propia
 * del dominio para no tener que depender de las de ningun framework.
 */
public class ReglaNegocioException extends RuntimeException {

	public ReglaNegocioException(String mensaje) {
		super(mensaje);
	}
}
