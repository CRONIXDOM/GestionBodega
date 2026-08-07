package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.request.InventarioProductoRequestDto;
import com.andiana.web.model.dto.response.InventarioProductoResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IInventarioProductoService {

    List<InventarioProductoResponseDto> listarInventario();

    void guardarInventario(InventarioProductoRequestDto nuevo);

    InventarioProductoResponseDto buscarInventarioId(Integer id);

    void eliminarInventario(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
