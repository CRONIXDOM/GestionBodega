package com.translog.api.infraestructura.persistencia.mapeadores;

import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.infraestructura.persistencia.jpa.RutaEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IRutaJpaMapperImpl implements IRutaJpaMapper {

    @Override
    public Ruta toDominio(RutaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Ruta ruta = new Ruta();

        ruta.setIdRuta( entity.getIdRuta() );
        ruta.setIdCiudadOrigen( entity.getIdCiudadOrigen() );
        ruta.setIdCiudadDestino( entity.getIdCiudadDestino() );
        ruta.setDistanciaKm( entity.getDistanciaKm() );

        return ruta;
    }

    @Override
    public RutaEntity toEntity(Ruta nuevoRuta) {
        if ( nuevoRuta == null ) {
            return null;
        }

        RutaEntity rutaEntity = new RutaEntity();

        rutaEntity.setIdRuta( nuevoRuta.getIdRuta() );
        rutaEntity.setIdCiudadOrigen( nuevoRuta.getIdCiudadOrigen() );
        rutaEntity.setIdCiudadDestino( nuevoRuta.getIdCiudadDestino() );
        rutaEntity.setDistanciaKm( nuevoRuta.getDistanciaKm() );

        return rutaEntity;
    }
}
