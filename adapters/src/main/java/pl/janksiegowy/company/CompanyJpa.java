package pl.janksiegowy.company;

import javax.persistence.*;

@Entity
@Table( name= "COMPANIES" )
public class CompanyJpa {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private long id;

}
