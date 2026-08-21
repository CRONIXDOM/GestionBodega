package com.translog.api.infraestructura.persistencia.mapeadores;

import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.infraestructura.persistencia.jpa.DespachoEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IDespachoJpaMapperImpl implements IDespachoJpaMapper {

    @Override
    public Despacho toDominio(DespachoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Despacho despacho = new Despacho();

        despacho.setIdDespacho( entity.getIdDespacho() );
        despacho.setFechaDespacho( entity.getFechaDespacho() );
        despacho.setIdRuta( entity.getIdRuta() );
        despacho.setIdVehiculo( entity.getIdVehiculo() );
        despacho.setIdConductor( entity.getIdConductor() );
        despacho.setEstado( entity.getEstado() );

        return despacho;
    }

    @Override
    public DespachoEntity toEntity(Despacho nuevoDespacho) {
        if ( nuevoDespacho == null ) {
            return null;
        }

        DespachoEntity despachoEntity = new DespachoEntity();

        despachoEntity.setIdDespacho( nuevoDespacho.getIdDespacho() );
        despachoEntity.setFechaDespacho( nuevoDespacho.getFechaDespacho() );
        despachoEntity.setIdRuta( nuevoDespacho.getIdRuta() );
        despachoEntity.setIdVehiculo( nuevoDespacho.getIdVehiculo() );
        despachoEntity.setIdConductor( nuevoDespacho.getIdConductor() );
        despachoEntity.setEstado( nuevoDespacho.getEstado() );

        return despachoEntity;
    }
}
