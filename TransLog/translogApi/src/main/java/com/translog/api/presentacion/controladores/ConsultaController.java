package com.translog.api.presentacion.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.translog.api.aplicacion.casosuso.entrada.IConsultaUseCase;
import com.translog.api.presentacion.dto.response.DespachoConEnviosDto;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

	private final IConsultaUseCase consultaUseCase;

	public ConsultaController(IConsultaUseCase consultaUseCase) {
		this.consultaUseCase = consultaUseCase;
	}

	@GetMapping("/despachos-con-mas-envios")
	public List<DespachoConEnviosDto> despachosConMasEnvios() {
		return consultaUseCase.despachosConMasEnvios();
	}

}
