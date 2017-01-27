package hr.codiraona.model;

import hr.codiraona.model.Allocation;
import hr.codiraona.model.Messages;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-27T08:18:00")
@StaticMetamodel(Ticket.class)
public class Ticket_ { 

    public static volatile SingularAttribute<Ticket, Integer> priorityNum;
    public static volatile SingularAttribute<Ticket, String> description;
    public static volatile SingularAttribute<Ticket, String> contactPerson;
    public static volatile SingularAttribute<Ticket, Allocation> allocationId;
    public static volatile SingularAttribute<Ticket, String> title;
    public static volatile SingularAttribute<Ticket, String> reportedBy;
    public static volatile SingularAttribute<Ticket, String> priority;
    public static volatile SingularAttribute<Ticket, String> assignedTo;
    public static volatile SingularAttribute<Ticket, Date> createdAt;
    public static volatile ListAttribute<Ticket, Messages> messagesList;
    public static volatile SingularAttribute<Ticket, Integer> id;
    public static volatile SingularAttribute<Ticket, String> category;
    public static volatile SingularAttribute<Ticket, Date> closedAt;
    public static volatile SingularAttribute<Ticket, String> contactPhone;
    public static volatile SingularAttribute<Ticket, String> status;

}