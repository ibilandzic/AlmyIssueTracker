/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author iva.bilandzic
 */
@Entity
@Table(name = "TICKET",schema = "TICKETING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t")
    , @NamedQuery(name = "Ticket.findById", query = "SELECT t FROM Ticket t WHERE t.id=:id")
    , @NamedQuery(name = "Ticket.findByTitle", query = "SELECT t FROM Ticket t WHERE t.title=:title")
    , @NamedQuery(name = "Ticket.findByDescription", query = "SELECT t FROM Ticket t WHERE t.description=:description")
    , @NamedQuery(name = "Ticket.findByReportedBy", query = "SELECT t FROM Ticket t WHERE t.reportedBy=:reportedBy")
    , @NamedQuery(name = "Ticket.findByAssignedTo", query = "SELECT t FROM Ticket t WHERE t.assignedTo=:assignedTo AND t.status='OPEN'")
    , @NamedQuery(name = "Ticket.findByStatus", query = "SELECT t FROM Ticket t WHERE t.status=:status")
    , @NamedQuery(name = "Ticket.findByPriority", query = "SELECT t FROM Ticket t WHERE t.priority=:priority")
    , @NamedQuery(name = "Ticket.findByCategory", query = "SELECT t FROM Ticket t WHERE t.category=:category")
    , @NamedQuery(name = "Ticket.findByPriorityNum", query = "SELECT t FROM Ticket t WHERE t.priorityNum=:priorityNum")
    , @NamedQuery(name = "Ticket.findByCreatedAt", query = "SELECT t FROM Ticket t WHERE t.createdAt=:createdAt")
    , @NamedQuery(name = "Ticket.findByClosedAt", query = "SELECT t FROM Ticket t WHERE t.closedAt=:closedAt")})
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "REPORTED_BY")
    private String reportedBy;
    @Size(max = 255)
    @Column(name = "ASSIGNED_TO")
    private String assignedTo;
    @Size(max = 255)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 255)
    @Column(name = "PRIORITY")
    private String priority;
    @Size(max = 255)
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "PRIORITY_NUM")
    private Integer priorityNum;
    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "CLOSED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketId")
    private List<Messages> messagesList;
    @JoinColumn(name = "ALLOCATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Allocation allocationId;
    @Column(name="CONTACT_PERSON")
    private String contactPerson;
    @Column(name="CONTACT_PHONE")
    private String contactPhone;

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Ticket() {
    }

    public Ticket(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPriorityNum() {
        return priorityNum;
    }

    public void setPriorityNum(Integer priorityNum) {
        this.priorityNum = priorityNum;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    @XmlTransient
    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    public Allocation getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Allocation allocationId) {
        this.allocationId = allocationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hr.codiraona.model.Ticket[ id=" + id + " ]";
    }
    
}
