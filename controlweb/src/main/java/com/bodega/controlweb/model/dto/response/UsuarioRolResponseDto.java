package com.bodega.controlweb.model.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioRolResponseDto {

    private Integer idUsuarioRol;
    private LocalDate fechaAsignacion;
    private Integer idUsuario;
    private Integer idRol;
}
