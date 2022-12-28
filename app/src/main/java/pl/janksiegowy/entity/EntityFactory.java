package pl.janksiegowy.entity;

import pl.janksiegowy.contact.Contact;
import pl.janksiegowy.contact.ContactDto;
import pl.janksiegowy.entity.EntityType.*;
import pl.janksiegowy.entity.dto.EntityDto;
import pl.janksiegowy.entity.revenue.Revenue;
import pl.janksiegowy.entity.shareholder.Shareholders;

class EntityFactory implements EntityTypeVisitor<Entity> {

    Entity from( EntityDto source) {
        return update( source.getType().accept( this), source);
    }

    EntityDto to( Entity entity) {
        return EntityDto.builder( entity.getType(), entity.getDate())
                .entityId( entity.getEntityId())
                .taxNumber( entity.getTaxNumber())
                .name( entity.getName())
                .address( entity.getAddress())
                .postcode( entity.getPostcode())
                .town( entity.getTown())
                .country( entity.getCountry())
                .supplier( entity.isSupplier())
                .customer( entity.isCustomer())
                .build();
    }

    Entity update( Entity entity, EntityDto source) {
        return entity
                .setDate( source.getDate())
                .setTaxNumber( source.getTaxNumber())
                .setEntityId( source.getEntityId())
                .setName( source.getName())
                .setAddress(source.getAddress())
                .setPostcode( source.getPostcode())
                .setTown( source.getTown())
                .setCountry( source.getCountry())
                .setSupplier( source.isSupplier())
                .setCustomer( source.isCustomer());
    }

    @Override
    public Entity visitContact() {
        return new Contact();
    }

    @Override
    public Entity visitRevenue() {
        return new Revenue();
    }

    @Override
    public Entity visitShareholders() {
        return new Shareholders();
    }

}
