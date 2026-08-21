package com.translog.api.infraestructura.persistencia.mapeadores;

import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.infraestructura.persistencia.jpa.ConductorEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:36+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IConductorJpaMapperImpl implements IConductorJpaMapper {

    @Override
    public Conductor toDominio(ConductorEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Conductor conductor = new Conductor();

        conductor.setIdConductor( entity.getIdConductor() );
        conductor.setNombre( entity.getNombre() );
        conductor.setLicencia( entity.getLicencia() );
        conductor.setEstado( entity.getEstado() );

        return conductor;
    }

    @Override
    public ConductorEntity toEntity(Conductor nuevoConductor) {
        if ( nuevoConductor == null ) {
            return null;
        }

        ConductorEntity conductorEntity = new ConductorEntity();

        conductorEntity.setIdConductor( nuevoConductor.getIdConductor() );
        conductorEntity.setNombre( nuevoConductor.getNombre() );
        conductorEntity.setLicencia( nuevoConductor.getLicencia() );
        conductorEntity.setEstado( nuevoConductor.getEstado() );

        return conductorEntity;
    }
}
