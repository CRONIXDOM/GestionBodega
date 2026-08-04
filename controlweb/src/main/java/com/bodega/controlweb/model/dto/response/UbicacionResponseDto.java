package com.bodega.controlweb.model.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UbicacionResponseDto {

    private Integer idUbicacion;
    private String codigoUbicacion;
    private String cantidadUbicacion;
    private LocalDate fechaUbicacion;
    private Integer idZona;
}
