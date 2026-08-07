package com.andiana.web.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Una opción de un desplegable: el id que se guarda y el texto que se ve. */
@Data
@AllArgsConstructor
public class OpcionSelectDto {

    private Integer id;
    private String etiqueta;
}
