/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;

import hr.codiraona.dao.TicketAddDataDAOLocal;
import hr.codiraona.dao.TicketDAOLocal;
import hr.codiraona.dao.UserDAOLocal;
import hr.codiraona.model.Category;
import hr.codiraona.model.Priority;
import hr.codiraona.model.Ticket;
import hr.codiraona.model.Users;
import hr.codiraona.util.Constants;
import hr.codiraona.utils.MessageUtils;
import java.io.Serializable;
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
@Named(value = "userBackingBean")
@ViewScoped
public class UserPageBackingBean implements Serializable {

    /**
     * Creates a new instance of UserPageBackingBean
     */
    private Logger log = Logger.getLogger(this.getClass().getName());
    @ManagedProperty(value = "#{authBackingBean}")
    private AuthBackingBean authBean;

    @EJB
    UserDAOLocal userDao;

    @EJB
    TicketDAOLocal ticketingDao;

    @EJB
    TicketAddDataDAOLocal additionalDao;

    private Ticket newTicket;
    
    private List<Category> ticketCategory;
    
    private List<Priority> ticketPriority;

    public List<Category> getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(List<Category> ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public List<Priority> getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(List<Priority> ticketPriority) {
        this.ticketPriority = ticketPriority;
    }
    
    public Ticket getNewTicket(){
        return newTicket;
    }

    public void setNewTicket(Ticket newTicket) {
        this.newTicket = newTicket;
    }

    public AuthBackingBean getAuthBean() {
        return authBean;
    }

    public void setAuthBean(AuthBackingBean authBean) {
        this.authBean = authBean;
    }

    public UserPageBackingBean() {
    }

    @PostConstruct
    public void init() {
        newTicket = new Ticket();
        ticketCategory = additionalDao.getAllCategories();
        ticketPriority = additionalDao.getAllPriorities();
    }

    

    public void createNewTicket() {
        
        log.log(Level.INFO,"Creating new ticket.");
        //set reported by
        newTicket.setReportedBy(authBean.getCurrentUser().getUsername());
        //set status
        newTicket.setStatus(Constants.OPEN_TICKET);
        //set priority
        if (newTicket.getPriority()==null || newTicket.getPriority().equals("")){
            //priority is not set, setting on lowest priority
            log.log(Level.INFO,"Priority is not set. Setting lowest priority...");
            int lowestPriority = ticketPriority.size()-1;
            newTicket.setPriority(ticketPriority.get(lowestPriority).getName());
            newTicket.setPriorityNum(ticketPriority.get(lowestPriority).getPriorityNumber());
        }
        else{
            //priority is set, need to set pri number
            log.log(Level.INFO,"Priority is set. Setting priority number...");
            for (Priority pri:ticketPriority){
                if (newTicket.getPriority().equals(pri.getName())){
                    newTicket.setPriorityNum(pri.getPriorityNumber());
                    log.log(Level.INFO,"Priority number is set.");
                    break;
                }
            }
        }
        
        //set contact information if user chose to use existing information
        if (newTicket.getContactPerson()==null || newTicket.getContactPhone().equals("")){
            newTicket.setContactPerson(String.format("Tvrtka: %s/ %s %s",
                    authBean.getCurrentUser().getCompanyId().getName(),
                    authBean.getCurrentUser().getFirstName(),
                    authBean.getCurrentUser().getLastName()));
        }
        
        if (newTicket.getContactPhone()==null || newTicket.getContactPhone().equals("")){
            newTicket.setContactPhone(String.format("Telefon: %s, mobitel: %s",
                    authBean.getCurrentUser().getPhoneNumber(),
                    authBean.getCurrentUser().getMobileNumber()));
        }
        
        if (ticketingDao.createNewTicket(newTicket)){
            //resfresh tickets
            authBean.getTicketOpenedByUser().add(newTicket);
            
            MessageUtils.showResponseMessage("Prijava je uspješno kreirana", "Success");
            
        }
        else{
            MessageUtils.showResponseMessage("Prijava nije kreirana", "Failure");
        }
    }
    
    

}
