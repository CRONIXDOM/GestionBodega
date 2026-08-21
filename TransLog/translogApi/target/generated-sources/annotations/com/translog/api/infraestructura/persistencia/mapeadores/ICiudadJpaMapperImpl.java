package com.translog.api.infraestructura.persistencia.mapeadores;

import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.infraestructura.persistencia.jpa.CiudadEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class ICiudadJpaMapperImpl implements ICiudadJpaMapper {

    @Override
    public Ciudad toDominio(CiudadEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Ciudad ciudad = new Ciudad();

        ciudad.setIdCiudad( entity.getIdCiudad() );
        ciudad.setNombre( entity.getNombre() );

        return ciudad;
    }

    @Override
    public CiudadEntity toEntity(Ciudad nuevoCiudad) {
        if ( nuevoCiudad == null ) {
            return null;
        }

        CiudadEntity ciudadEntity = new CiudadEntity();

        ciudadEntity.setIdCiudad( nuevoCiudad.getIdCiudad() );
        ciudadEntity.setNombre( nuevoCiudad.getNombre() );

        return ciudadEntity;
    }
}
