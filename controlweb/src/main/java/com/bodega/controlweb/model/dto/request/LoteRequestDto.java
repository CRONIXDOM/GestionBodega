package com.bodega.controlweb.model.dto.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class LoteRequestDto {

    private Integer idLote;
    private String numeroLote;
    private LocalDate fechaIngreso;
    private LocalDate fechaVencimiento;
    private Integer cantidadLote;
    private Integer idProducto;
    private Integer idUbicacion;
}
