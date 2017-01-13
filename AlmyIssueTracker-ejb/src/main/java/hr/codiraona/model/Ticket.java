package hr.codiraona.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the TICKET database table.
 *
 */
@Entity
@Table(name = "TICKET", schema = "TICKETING")
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t ORDER BY t.priorityNum,t.createdAt ASC")
    ,
	@NamedQuery(name = "Ticket.findByReporter", query = "SELECT t FROM Ticket t where t.reportedBy =:inUsername ORDER BY t.priorityNum, t.createdAt ASC")
    ,
	@NamedQuery(name = "Ticket.findByAssignee", query = "SELECT t FROM Ticket t where t.assignedTo =:inUsername ORDER BY t.priorityNum, t.createdAt ASC"),
        
        @NamedQuery(name="Ticket.findTicketByStatus", query="SELECT t from Ticket t where t.status=:inStatus ORDER BY t.priorityNum, t.createdAt ASC")
})

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ASSIGNED_TO")
    private String assignedTo;

    private String category;

    private String description;

    private String priority;

    @Column(name="PRIORITY_NUM")
    private int priorityNum;

    @Column(name = "REPORTED_BY")
    private String reportedBy;

    private String status;

    private String title;

    //bi-directional many-to-one association to Location
    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    //bi-directional many-to-one association to Message
    @OneToMany(mappedBy = "ticket")
    private List<Message> messages;
    
    @Column(name="CREATED_AT")
    private Timestamp createdAt;
    
    @Column(name="CLOSED_AT")
    private Timestamp closedAt;

    public Timestamp getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Timestamp closedAt) {
        this.closedAt = closedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Constructor
     */
    public Ticket() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReportedBy() {
        return this.reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message addMessage(Message message) {
        getMessages().add(message);
        message.setTicket(this);

        return message;
    }

    public Message removeMessage(Message message) {
        getMessages().remove(message);
        message.setTicket(null);

        return message;
    }

    public int getPriorityNum() {
        return priorityNum;
    }

    public void setPriorityNum(int priorityNum) {
        this.priorityNum = priorityNum;
    }
}
