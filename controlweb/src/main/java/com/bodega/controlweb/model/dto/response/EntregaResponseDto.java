package com.bodega.controlweb.model.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EntregaResponseDto {

    private Integer idEntrega;
    private LocalDate fechaEntrega;
    private String responsableEntrega;
}
