/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;

import hr.codiraona.dao.LocationDAOLocal;
import hr.codiraona.dao.TicketAddDataDAOLocal;
import hr.codiraona.dao.TicketDAOLocal;
import hr.codiraona.dao.UserDAOLocal;
import hr.codiraona.model.Category;
import hr.codiraona.model.Locations;
import hr.codiraona.model.Messages;
import hr.codiraona.model.Priority;
import hr.codiraona.model.Status;
import hr.codiraona.model.Ticket;
import hr.codiraona.model.Users;
import hr.codiraona.util.Constants;
import hr.codiraona.utils.MessageUtils;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.inject.Named;

/**
 *
 * @author iva.bilandzic
 */
@ManagedBean
@Named(value = "ticketsManagedBean")
@ViewScoped
public class TicketsManagedBean implements Serializable {

    /**
     * Creates a new instance of TicketsManagedBean
     */
   
    private Logger log = Logger.getLogger(this.getClass().getName());

    @ManagedProperty(value = "#{authBackingBean}")
    private AuthBackingBean authBean;

    public AuthBackingBean getAuthBean() {
        return authBean;
    }

    public void setAuthBean(AuthBackingBean authBean) {
        this.authBean = authBean;
    }
    @EJB
    UserDAOLocal userDao;

    @EJB
    private TicketAddDataDAOLocal ticketAddDataDao;

    @EJB
    private TicketDAOLocal ticketDao;

    @EJB
    private LocationDAOLocal locationDao;

    private List<Ticket> allTickets;

    private List<Ticket> openTickets;

    private List<Ticket> closedTickets;

    private Ticket selectedTicket;

    private Ticket newTicket;

    private List<Status> statuses;

    private List<Priority> priorities;

    private List<Category> categories;

    private List<Users> employeeList;

    private List<Locations> locationList;

    private boolean isReadOnly;

    private int openTicketsSize;

    public List<Locations> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Locations> locationList) {
        this.locationList = locationList;
    }

    public List<Users> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Users> employeeList) {
        this.employeeList = employeeList;
    }

    public Messages getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(Messages newMessage) {
        this.newMessage = newMessage;
    }

    private int closedTicketsSize;

    private Messages newMessage;

    public int getOpenTicketsSize() {
        return openTicketsSize;
    }

    public void setOpenTicketsSize(int openTicketsSize) {
        this.openTicketsSize = openTicketsSize;
    }

    public int getClosedTicketsSize() {
        return closedTicketsSize;
    }

    public void setClosedTicketsSize(int closedTicketsSize) {
        this.closedTicketsSize = closedTicketsSize;
    }

    public boolean isIsReadOnly() {
        return isReadOnly;
    }

    public void setIsReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public List<Priority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<Priority> priorities) {
        this.priorities = priorities;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Ticket> getAllTickets() {
        return allTickets;
    }

    public void setAllTickets(List<Ticket> allTickets) {
        this.allTickets = allTickets;
    }

    public List<Ticket> getOpenTickets() {
        return openTickets;
    }

    public void setOpenTickets(List<Ticket> openTickets) {
        this.openTickets = openTickets;
    }

    public List<Ticket> getClosedTickets() {
        return closedTickets;
    }

    public void setClosedTickets(List<Ticket> closedTickets) {
        this.closedTickets = closedTickets;
    }

    public Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(Ticket selectedTicket) {
        this.selectedTicket = selectedTicket;

        log.log(Level.INFO, "Selected ticket: " + this.selectedTicket.getTitle());
        log.log(Level.INFO, "Selected ticket message list size: " + this.selectedTicket.getMessagesList().size());
        
        FacesContext
                .getCurrentInstance()
                .addMessage(null, 
                        new FacesMessage("Odabrana prijava id: "+this.selectedTicket.getId(), "Odabrana prijava id: "+this.selectedTicket.getId()));

    }

    public Ticket getNewTicket() {
        return newTicket;
    }

    public void setNewTicket(Ticket newTicket) {
        this.newTicket = newTicket;
    }

    public TicketsManagedBean() {
    }

    @PostConstruct
    public void init() {
        newTicket = new Ticket();
        openTickets = ticketDao.getAllOpenedTickets();
        closedTickets = ticketDao.getAllClosedTickets();
        allTickets = ticketDao.getAllTickets();
        log.log(Level.INFO,"Admin: "+authBean.isAdmin());
        categories = ticketAddDataDao.getAllCategories();
        priorities = ticketAddDataDao.getAllPriorities();
        statuses = ticketAddDataDao.getAllStatus();
        locationList = locationDao.getAllLocations();
        isReadOnly = true;
        openTicketsSize = openTickets.size();
        closedTicketsSize = closedTickets.size();
        newMessage = new Messages();
        employeeList = userDao.getUsersByRole(Constants.EMPLOYEE_ROLE_NAME);
    }

    /**
     * Creates new message for specific ticket
     */
    public void createNewMessage() {
        log.log(Level.INFO, authBean.getCurrentUser().getUsername());
        newMessage.setPostedBy(authBean.getCurrentUser().getUsername());
        newMessage.setTicketId(selectedTicket);
        newMessage.setPostedAt(new Timestamp(System.currentTimeMillis()));
        selectedTicket.getMessagesList().add(newMessage);
        ticketDao.editTicket(selectedTicket);
        newMessage = new Messages();

    }

    public void editTicket(Ticket ticket) {
        log.log(Level.INFO, "Updating ticket...");
        if (ticket != null) {
            ticket.setPriorityNum(getPriorityNum(ticket.getPriority()));
            ticketDao.editTicket(ticket);
            log.log(Level.INFO, "Ticket updated. Refreshing opened ticket grid...");
            openTickets = ticketDao.getAllOpenedTickets();
            closedTickets = ticketDao.getAllClosedTickets();
            MessageUtils.showResponseMessage("Editiranje prijave je uspjelo. Editirana prijava. "+ticket.getId(), "Successfull");

        } else {
            log.log(Level.WARNING, "Selected ticket is null, unable to edit ticket. Exiting...");
            MessageUtils.showResponseMessage("Editiranje prijave nije uspjelo.", "Failure");
        }
    }


    private int getPriorityNum(String name) {
        int priorityNumber = 0;
        for (Priority pri : priorities) {
            if (pri.getName().equals(name)) {
                priorityNumber = pri.getPriorityNumber();
                break;
            }
        }
        return priorityNumber;
    }

}
