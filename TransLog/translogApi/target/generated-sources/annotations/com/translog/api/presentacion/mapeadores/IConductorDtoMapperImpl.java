package com.translog.api.presentacion.mapeadores;

import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.presentacion.dto.request.ConductorRequestDto;
import com.translog.api.presentacion.dto.response.ConductorResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IConductorDtoMapperImpl implements IConductorDtoMapper {

    @Override
    public Conductor toDomain(ConductorRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Conductor conductor = new Conductor();

        conductor.setIdConductor( dto.getIdConductor() );
        conductor.setNombre( dto.getNombre() );
        conductor.setLicencia( dto.getLicencia() );
        conductor.setEstado( dto.getEstado() );

        return conductor;
    }

    @Override
    public ConductorResponseDto toResponseDto(Conductor conductor) {
        if ( conductor == null ) {
            return null;
        }

        ConductorResponseDto conductorResponseDto = new ConductorResponseDto();

        conductorResponseDto.setIdConductor( conductor.getIdConductor() );
        conductorResponseDto.setNombre( conductor.getNombre() );
        conductorResponseDto.setLicencia( conductor.getLicencia() );
        conductorResponseDto.setEstado( conductor.getEstado() );

        return conductorResponseDto;
    }
}
