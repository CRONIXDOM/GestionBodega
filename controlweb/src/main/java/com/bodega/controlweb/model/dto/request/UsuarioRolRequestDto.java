package com.bodega.controlweb.model.dto.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioRolRequestDto {

    private Integer idUsuarioRol;
    private LocalDate fechaAsignacion;
    private Integer idUsuario;
    private Integer idRol;
}
