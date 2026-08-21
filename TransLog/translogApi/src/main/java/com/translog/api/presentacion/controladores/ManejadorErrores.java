package com.translog.api.presentacion.controladores;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Los rechazos de las reglas de negocio salen como un 400 con el motivo en
 * "message", para que translogWeb pueda mostrarselo al usuario tal cual en vez
 * de una pagina de error.
 */
@RestControllerAdvice
public class ManejadorErrores {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> reglaIncumplida(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
	}

}
