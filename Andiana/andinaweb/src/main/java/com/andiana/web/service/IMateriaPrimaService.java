package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.request.MateriaPrimaRequestDto;
import com.andiana.web.model.dto.response.MateriaPrimaResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IMateriaPrimaService {

    List<MateriaPrimaResponseDto> listarMateriaPrima();

    void guardarMateriaPrima(MateriaPrimaRequestDto nuevo);

    MateriaPrimaResponseDto buscarMateriaPrimaId(Integer id);

    void eliminarMateriaPrima(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
