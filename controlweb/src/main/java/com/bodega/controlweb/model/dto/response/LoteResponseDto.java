package com.bodega.controlweb.model.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class LoteResponseDto {

    private Integer idLote;
    private String numeroLote;
    private LocalDate fechaIngreso;
    private LocalDate fechaVencimiento;
    private Integer cantidadLote;
    private Integer idProducto;
    private Integer idUbicacion;
    private Integer cantidadReservada;
    private Integer cantidadDisponible;
}
