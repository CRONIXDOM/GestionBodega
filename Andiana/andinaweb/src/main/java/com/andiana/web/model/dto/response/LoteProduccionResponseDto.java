package com.andiana.web.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LoteProduccionResponseDto {

    private Integer idLote;
    private Integer idOrden;
    private String numeroLote;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private BigDecimal cantidadProducida;
    private String estado;
}
