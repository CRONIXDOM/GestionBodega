package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.request.ProductoRequestDto;
import com.andiana.web.model.dto.response.ProductoResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IProductoService {

    List<ProductoResponseDto> listarProducto();

    void guardarProducto(ProductoRequestDto nuevo);

    ProductoResponseDto buscarProductoId(Integer id);

    void eliminarProducto(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
