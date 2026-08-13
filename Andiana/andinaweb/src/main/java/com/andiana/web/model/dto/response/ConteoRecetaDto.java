package com.andiana.web.model.dto.response;

import lombok.Data;

@Data
public class ConteoRecetaDto {

    private Integer idReceta;
    private String producto;
    private Integer version;
    private Boolean activa;
    private Long totalMateriasPrimas;
}
