package com.translog.api.presentacion.mapeadores;

import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.presentacion.dto.request.RutaRequestDto;
import com.translog.api.presentacion.dto.response.RutaResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IRutaDtoMapperImpl implements IRutaDtoMapper {

    @Override
    public Ruta toDomain(RutaRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Ruta ruta = new Ruta();

        ruta.setIdRuta( dto.getIdRuta() );
        ruta.setIdCiudadOrigen( dto.getIdCiudadOrigen() );
        ruta.setIdCiudadDestino( dto.getIdCiudadDestino() );
        ruta.setDistanciaKm( dto.getDistanciaKm() );

        return ruta;
    }

    @Override
    public RutaResponseDto toResponseDto(Ruta ruta) {
        if ( ruta == null ) {
            return null;
        }

        RutaResponseDto rutaResponseDto = new RutaResponseDto();

        rutaResponseDto.setIdRuta( ruta.getIdRuta() );
        rutaResponseDto.setIdCiudadOrigen( ruta.getIdCiudadOrigen() );
        rutaResponseDto.setIdCiudadDestino( ruta.getIdCiudadDestino() );
        rutaResponseDto.setDistanciaKm( ruta.getDistanciaKm() );

        return rutaResponseDto;
    }
}
