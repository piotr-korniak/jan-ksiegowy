package pl.janksiegowy.entity;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import pl.janksiegowy.entity.dto.EntityDto;
import pl.janksiegowy.entity.dto.EntityQueryDto;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
class EntityInitializer  {

    private final ResourceLoader loader;
    private final EntityQueryRepository entity;
    private final EntityFacade facade;

    @SneakyThrows
    public void init() {
        Date histDate= new Date();
        SimpleDateFormat DMRR= new SimpleDateFormat( "--- dd.MM.yyyy");

        for( String line: readContacts()) {
            String[] fields= getFields( line);
            if( fields[0].startsWith( "--- ")) { // Set date
                histDate= DMRR.parse( fields[0]);
                continue;
            }

            EntityType type= EntityType.valueOf( fields[0]);
            String taxNumber= fields[2].replaceAll("[^\\d]", "");
            EntityRole role= EntityRole.values()[Integer.parseInt( fields[6])];
            String country= fields.length>7? fields[7]: "PL"; // PL default

            Optional<EntityQueryDto> dto= entity.findByTaxNumberAndCountryAndType( taxNumber, country, type);

            // Optimization, omit if Entity already exists
            if( dto.isPresent()&& !dto.get().getDate().before( histDate))
                continue;

            EntityDto.EntityDtoBuilder source= EntityDto.builder( type, histDate)
                    .name( fields[1])
                    .taxNumber( taxNumber)
                    .address( fields[3])
                    .postcode( fields[4])
                    .town( fields[5])
                    .supplier( role.isSupplier())
                    .customer( role.isCustomer())
                    .country( country);

            if( dto.isPresent()) // Update
                        source.entityId( dto.get().getEntityId());

            facade.save( source.build());
        }
    }

    @SneakyThrows
    private String[] readContacts() {
        return new String( loader
                .getResource("classpath:data/contacts.txt")
                .getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .split( "; *["+ System.lineSeparator()+ "]*");
    }

   private String[] getFields( String row){
        String[] result= row.split( "(?<!\\\\), *");

        for( int n=0; n<result.length; n++)	// nie zmienia jak nie trzeba
            result[n]= result[n].replaceAll( "\\\\,", ",");
        return result;

    }

    enum EntityRole {
        NONE,
        SUPPLIER {
            @Override public boolean isSupplier() {
                return true;
            }
        },
        CUSTOMER {
            @Override public boolean isCustomer() {
                return true;
            }
        },
        BOTH {
            @Override public boolean isSupplier() {
                return true;
            }

            @Override public boolean isCustomer() {
                return true;
            }
        };

        public boolean isSupplier() {
            return false;
        };
        public boolean isCustomer() {
            return false;
        };
    }
}
