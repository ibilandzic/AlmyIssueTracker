package hr.codiraona.model;

import hr.codiraona.model.Company;
import hr.codiraona.model.Role;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-20T13:22:50")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> lastName;
    public static volatile SingularAttribute<Users, String> firstName;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, Company> companyId;
    public static volatile SingularAttribute<Users, String> phoneNumber;
    public static volatile SingularAttribute<Users, String> mobileNumber;
    public static volatile SingularAttribute<Users, Role> roleId;
    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, Date> expiresAt;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, String> token;
    public static volatile SingularAttribute<Users, String> username;

}