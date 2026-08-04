package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.response.InventarioResponseDto;

public interface IInventarioService {

    List<InventarioResponseDto> listarInventario();
}
