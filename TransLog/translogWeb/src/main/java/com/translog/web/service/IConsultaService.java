package com.translog.web.service;

import java.util.List;

import com.translog.web.model.dto.response.DespachoConEnviosDto;

public interface IConsultaService {

    List<DespachoConEnviosDto> despachosConMasEnvios();
}
