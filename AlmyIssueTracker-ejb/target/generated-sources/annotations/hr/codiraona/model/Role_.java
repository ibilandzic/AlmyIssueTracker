package hr.codiraona.model;

import hr.codiraona.model.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-20T13:22:50")
@StaticMetamodel(Role.class)
public class Role_ { 

    public static volatile ListAttribute<Role, Users> usersList;
    public static volatile SingularAttribute<Role, String> name;
    public static volatile SingularAttribute<Role, String> description;
    public static volatile SingularAttribute<Role, Integer> id;

}