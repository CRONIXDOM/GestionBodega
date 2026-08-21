package com.translog.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.translog.api.presentacion.dto.response.DespachoConEnviosDto;

public interface IConsultaUseCase {

	/**
	 * Los despachos con mayor cantidad de envios asociados, de mas a menos.
	 * Solo entran los despachos que cumplen la regla de negocio.
	 */
	List<DespachoConEnviosDto> despachosConMasEnvios();

}
