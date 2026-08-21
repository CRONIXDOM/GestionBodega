package com.translog.api.presentacion.mapeadores;

import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.presentacion.dto.request.CiudadRequestDto;
import com.translog.api.presentacion.dto.response.CiudadResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class ICiudadDtoMapperImpl implements ICiudadDtoMapper {

    @Override
    public Ciudad toDomain(CiudadRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Ciudad ciudad = new Ciudad();

        ciudad.setIdCiudad( dto.getIdCiudad() );
        ciudad.setNombre( dto.getNombre() );

        return ciudad;
    }

    @Override
    public CiudadResponseDto toResponseDto(Ciudad ciudad) {
        if ( ciudad == null ) {
            return null;
        }

        CiudadResponseDto ciudadResponseDto = new CiudadResponseDto();

        ciudadResponseDto.setIdCiudad( ciudad.getIdCiudad() );
        ciudadResponseDto.setNombre( ciudad.getNombre() );

        return ciudadResponseDto;
    }
}
