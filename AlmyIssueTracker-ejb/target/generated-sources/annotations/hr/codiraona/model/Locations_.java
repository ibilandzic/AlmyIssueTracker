package hr.codiraona.model;

import hr.codiraona.model.Allocation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-27T08:18:00")
@StaticMetamodel(Locations.class)
public class Locations_ { 

    public static volatile SingularAttribute<Locations, String> country;
    public static volatile SingularAttribute<Locations, String> address;
    public static volatile SingularAttribute<Locations, String> city;
    public static volatile SingularAttribute<Locations, String> name;
    public static volatile SingularAttribute<Locations, Integer> id;
    public static volatile ListAttribute<Locations, Allocation> allocationList;

}