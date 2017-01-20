package hr.codiraona.model;

import hr.codiraona.model.Allocation;
import hr.codiraona.model.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-20T13:22:50")
@StaticMetamodel(Company.class)
public class Company_ { 

    public static volatile SingularAttribute<Company, String> country;
    public static volatile ListAttribute<Company, Users> usersList;
    public static volatile SingularAttribute<Company, String> address;
    public static volatile SingularAttribute<Company, String> city;
    public static volatile SingularAttribute<Company, String> name;
    public static volatile SingularAttribute<Company, Integer> id;
    public static volatile ListAttribute<Company, Allocation> allocationList;

}