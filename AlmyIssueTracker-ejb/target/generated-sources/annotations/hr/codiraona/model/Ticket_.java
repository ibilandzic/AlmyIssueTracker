package hr.codiraona.model;

import hr.codiraona.model.Location;
import hr.codiraona.model.Message;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-07T16:56:14")
@StaticMetamodel(Ticket.class)
public class Ticket_ { 

    public static volatile SingularAttribute<Ticket, String> description;
    public static volatile ListAttribute<Ticket, Message> messages;
    public static volatile SingularAttribute<Ticket, Location> location;
    public static volatile SingularAttribute<Ticket, Integer> id;
    public static volatile SingularAttribute<Ticket, String> category;
    public static volatile SingularAttribute<Ticket, String> priority;
    public static volatile SingularAttribute<Ticket, String> reportedBy;
    public static volatile SingularAttribute<Ticket, String> title;
    public static volatile SingularAttribute<Ticket, String> assignedTo;
    public static volatile SingularAttribute<Ticket, String> status;

}