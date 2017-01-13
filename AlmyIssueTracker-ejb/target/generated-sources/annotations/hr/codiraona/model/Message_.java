package hr.codiraona.model;

import hr.codiraona.model.Ticket;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-13T14:59:51")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, String> postedBy;
    public static volatile SingularAttribute<Message, Ticket> ticket;
    public static volatile SingularAttribute<Message, Timestamp> postedAt;
    public static volatile SingularAttribute<Message, Integer> id;
    public static volatile SingularAttribute<Message, String> message;

}