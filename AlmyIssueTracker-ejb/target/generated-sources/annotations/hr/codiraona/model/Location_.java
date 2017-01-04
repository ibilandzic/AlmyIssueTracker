package hr.codiraona.model;

import hr.codiraona.model.Ticket;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-03T13:32:12")
@StaticMetamodel(Location.class)
public class Location_ { 

    public static volatile SingularAttribute<Location, String> country;
    public static volatile SingularAttribute<Location, String> address;
    public static volatile ListAttribute<Location, Ticket> tickets;
    public static volatile SingularAttribute<Location, String> city;
    public static volatile SingularAttribute<Location, String> name;
    public static volatile SingularAttribute<Location, Integer> id;

}