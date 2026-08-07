package com.andiana.web.model.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class OrdenProduccionRequestDto {

    private Integer idOrden;
    private Integer idProducto;
    private LocalDate fechaProgramada;
    private BigDecimal cantidadProgramada;
    private String estado;
    private String responsable;
}
