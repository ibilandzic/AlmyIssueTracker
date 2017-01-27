package hr.codiraona.model;

import hr.codiraona.model.Ticket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-27T08:18:00")
@StaticMetamodel(Messages.class)
public class Messages_ { 

    public static volatile SingularAttribute<Messages, String> postedBy;
    public static volatile SingularAttribute<Messages, Date> postedAt;
    public static volatile SingularAttribute<Messages, Integer> id;
    public static volatile SingularAttribute<Messages, String> message;
    public static volatile SingularAttribute<Messages, Ticket> ticketId;

}