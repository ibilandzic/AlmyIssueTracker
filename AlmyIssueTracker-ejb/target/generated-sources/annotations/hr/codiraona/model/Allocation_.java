package hr.codiraona.model;

import hr.codiraona.model.Company;
import hr.codiraona.model.Location;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-07T16:56:14")
@StaticMetamodel(Allocation.class)
public class Allocation_ { 

    public static volatile SingularAttribute<Allocation, String> buildingName;
    public static volatile SingularAttribute<Allocation, String> roomRange;
    public static volatile SingularAttribute<Allocation, Location> location;
    public static volatile SingularAttribute<Allocation, Company> company;
    public static volatile SingularAttribute<Allocation, Integer> id;

}