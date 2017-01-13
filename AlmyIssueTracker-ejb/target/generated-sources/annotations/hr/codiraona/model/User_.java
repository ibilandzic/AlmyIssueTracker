package hr.codiraona.model;

import hr.codiraona.model.Company;
import hr.codiraona.model.Location;
import hr.codiraona.model.Role;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-13T14:59:51")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, Role> role;
    public static volatile SingularAttribute<User, String> mobileNumber;
    public static volatile SingularAttribute<User, Timestamp> expiresAt;
    public static volatile SingularAttribute<User, String> token;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> phoneNumber;
    public static volatile SingularAttribute<User, Location> location;
    public static volatile SingularAttribute<User, Company> company;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}