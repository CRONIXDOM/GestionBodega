package com.andiana.web.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class InventarioProductoResponseDto {

    private Integer idInventario;
    private Integer idLote;
    private BigDecimal cantidad;
    private String ubicacion;
    private LocalDate fechaIngreso;
}
