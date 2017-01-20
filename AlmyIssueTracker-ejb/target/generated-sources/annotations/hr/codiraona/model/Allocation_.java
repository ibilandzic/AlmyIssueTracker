package hr.codiraona.model;

import hr.codiraona.model.Company;
import hr.codiraona.model.Locations;
import hr.codiraona.model.Ticket;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-20T13:22:50")
@StaticMetamodel(Allocation.class)
public class Allocation_ { 

    public static volatile SingularAttribute<Allocation, String> buildingName;
    public static volatile SingularAttribute<Allocation, String> floorPart;
    public static volatile ListAttribute<Allocation, Ticket> ticketList;
    public static volatile SingularAttribute<Allocation, Company> companyId;
    public static volatile SingularAttribute<Allocation, String> roomRange;
    public static volatile SingularAttribute<Allocation, Locations> locationId;
    public static volatile SingularAttribute<Allocation, Integer> id;
    public static volatile SingularAttribute<Allocation, String> floor;

}