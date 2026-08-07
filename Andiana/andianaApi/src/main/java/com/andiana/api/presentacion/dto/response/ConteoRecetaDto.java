package com.andiana.api.presentacion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Una linea de la consulta "numero de materias primas por receta". */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConteoRecetaDto {

	private Integer idReceta;
	private String producto;
	private Integer version;
	private Boolean activa;
	private Long totalMateriasPrimas;
}
