package com.andiana.web.model.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RecetaProduccionResponseDto {

    private Integer idReceta;
    private Integer idProducto;
    private Integer version;
    private LocalDate fechaVigencia;
    private Boolean estado;
}
