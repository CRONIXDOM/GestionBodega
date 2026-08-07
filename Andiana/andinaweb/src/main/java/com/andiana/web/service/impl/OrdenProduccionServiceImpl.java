package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.request.OrdenProduccionRequestDto;
import com.andiana.web.model.dto.response.OrdenProduccionResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.model.dto.response.ProductoResponseDto;
import com.andiana.web.service.IOrdenProduccionService;
import com.andiana.web.service.IProductoService;

@Service
public class OrdenProduccionServiceImpl implements IOrdenProduccionService {

    private final WebClient webCliente;
    private final IProductoService servicioProducto;

    public OrdenProduccionServiceImpl(WebClient webCliente, IProductoService servicioProducto) {
        this.webCliente = webCliente;
        this.servicioProducto = servicioProducto;
    }

    @Override
    public List<OrdenProduccionResponseDto> listarOrden() {
        return webCliente.get().uri("/orden").retrieve()
                .bodyToFlux(OrdenProduccionResponseDto.class).collectList().block();
    }

    @Override
    public void guardarOrden(OrdenProduccionRequestDto nuevo) {
        webCliente.post().uri("/orden").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public OrdenProduccionResponseDto buscarOrdenId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/orden/buscarId/{id}").build(id))
                .retrieve().bodyToMono(OrdenProduccionResponseDto.class).block();
    }

    @Override
    public void eliminarOrden(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/orden/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    /** Una orden se reconoce por su producto y su fecha, no por su número. */
    @Override
    public List<OpcionSelectDto> listarOpciones() {
        List<ProductoResponseDto> productos = servicioProducto.listarProducto();

        return listarOrden().stream()
                .map(op -> new OpcionSelectDto(op.getIdOrden(), "Orden " + op.getIdOrden() + " · "
                        + nombreDelProducto(productos, op.getIdProducto()) + " · " + op.getFechaProgramada()))
                .toList();
    }

    private String nombreDelProducto(List<ProductoResponseDto> productos, Integer idProducto) {
        return productos.stream()
                .filter(p -> p.getIdProducto().equals(idProducto))
                .map(p -> p.getNombre() + " " + p.getPresentacion())
                .findFirst().orElse("(producto eliminado)");
    }
}
