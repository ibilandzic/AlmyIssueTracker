/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;

import hr.codiraona.dao.AllocationsDAOLocal;
import hr.codiraona.dao.TicketDAOLocal;
import hr.codiraona.model.Allocation;
import hr.codiraona.model.Messages;
import hr.codiraona.model.Ticket;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author iva.bilandzic
 */
@ManagedBean
@Named(value = "employeeBackingBean")
@ViewScoped
public class EmployeeBackingBean implements Serializable{

    /**
     * Creates a new instance of EmployeeBackingBean
     */
    private Logger log = Logger.getLogger(this.getClass().getName());
    @EJB
    private AllocationsDAOLocal allocationDao;
    
    @EJB
    private TicketDAOLocal ticketDao;
    
    @ManagedProperty(value = "#{authBackingBean}")
    private AuthBackingBean authBean;
    
    private List<Allocation> allocations;
    
    private Messages newMessage;

    public Messages getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(Messages newMessage) {
        this.newMessage = newMessage;
    }
    
    private Ticket selectedTicket;

    public Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(Ticket selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public AuthBackingBean getAuthBean() {
        return authBean;
    }

    public void setAuthBean(AuthBackingBean authBean) {
        this.authBean = authBean;
    }

    public List<Allocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
    }
    
    public EmployeeBackingBean() {
    }
    
    @PostConstruct
    private void init(){
        allocations = allocationDao.getAllAllocations();
        newMessage = new Messages();
        
    }
    
    public void createNewMessage() {
        log.log(Level.INFO, authBean.getUsername());
        newMessage.setPostedBy(authBean.getUsername());
        newMessage.setTicketId(selectedTicket);
        newMessage.setPostedAt(new Timestamp(System.currentTimeMillis()));
        selectedTicket.getMessagesList().add(newMessage);
        ticketDao.editTicket(selectedTicket);
        newMessage = new Messages();

    }
    
    
    
}
