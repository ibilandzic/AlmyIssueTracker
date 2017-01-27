/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;

import hr.codiraona.dao.AllocationsDAOLocal;
import hr.codiraona.dao.CompanyDAOLocal;
import hr.codiraona.dao.MessageDAOLocal;
import hr.codiraona.dao.TicketAddDataDAOLocal;
import hr.codiraona.dao.TicketDAOLocal;
import hr.codiraona.dao.UserDAOLocal;
import hr.codiraona.model.Allocation;
import hr.codiraona.model.Category;
import hr.codiraona.model.Company;
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
@Named(value = "employeeBackingBean")
@ViewScoped
public class EmployeeBackingBean implements Serializable{

    /**
     * Creates a new instance of EmployeeBackingBean
     */
    private Logger log = Logger.getLogger(this.getClass().getName());
    @ManagedProperty(value = "#{authBackingBean}")
    private AuthBackingBean authBean;
    @EJB
    private AllocationsDAOLocal allocationDao;
    
    @EJB
    private TicketDAOLocal ticketDao;
    
    @EJB
    private TicketAddDataDAOLocal addDao;
    
    @EJB
    private CompanyDAOLocal companyDao;
    
    @EJB
    private UserDAOLocal userDao;
    
    @EJB
    private MessageDAOLocal messageDao;
    
    private List<Company> companyList;
    
    private List<Priority> ticketPriority;
    
    private Company selectedCompany;
    
    private List<Status> statusList;
    
    private List<Users> adminList;

    public List<Users> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Users> adminList) {
        this.adminList = adminList;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        
        this.selectedCompany = selectedCompany;
        
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }
    
    private List<Category> ticketCategory;

    public List<Category> getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(List<Category> ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public TicketAddDataDAOLocal getAddDao() {
        return addDao;
    }

    public void setAddDao(TicketAddDataDAOLocal addDao) {
        this.addDao = addDao;
    }
    
    
    
    private List<Allocation> allocations;
    
    private Messages newMessage;
    
    private Ticket newTicket;

    public Ticket getNewTicket() {
        return newTicket;
    }

    public void setNewTicket(Ticket newTicket) {
        this.newTicket = newTicket;
    }

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

        log.log(Level.INFO, "Selected ticket: " + this.selectedTicket.getTitle());
        log.log(Level.INFO, "Selected ticket message list size: " + this.selectedTicket.getMessagesList().size());
        
        FacesContext
                .getCurrentInstance()
                .addMessage(null, 
                        new FacesMessage("Odabrana prijava id: "+this.selectedTicket.getId(), "Odabrana prijava id: "+this.selectedTicket.getId()));
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

    public List<Priority> getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(List<Priority> ticketPriority) {
        this.ticketPriority = ticketPriority;
    }
    
    
    @PostConstruct
    private void init(){
        allocations = allocationDao.getAllAllocations();
        newMessage = new Messages();
        newTicket = new Ticket();
        ticketCategory = addDao.getAllCategories();
        companyList = companyDao.getAllCompanies();
        ticketPriority = addDao.getAllPriorities();
        selectedCompany = companyList.get(0);
        statusList = addDao.getAllStatus();
        adminList = userDao.getUsersByRole(Constants.ADMIN_ROLE_NAME);
        
    }
    
    public void createNewMessage() {
        log.log(Level.INFO, "Username: "+ authBean.getCurrentUser().getUsername());
        newMessage.setPostedBy(authBean.getCurrentUser().getUsername());
        newMessage.setTicketId(selectedTicket);
        newMessage.setPostedAt(new Timestamp(System.currentTimeMillis()));
        selectedTicket.getMessagesList().add(newMessage);
        ticketDao.editTicket(selectedTicket);
        
        
        newMessage = new Messages();
        

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
        
        if (ticketDao.createNewTicket(newTicket)){
            //resfresh tickets
            authBean.getTicketOpenedByUser().add(newTicket);
            
            MessageUtils.showResponseMessage("Prijava je uspješno kreirana", "Success");
            
        }
        else{
            MessageUtils.showResponseMessage("Prijava nije kreirana", "Failure");
        }
    }
   
   public void editTicket(Ticket ticket) {
        log.log(Level.INFO, "Updating ticket...");
        if (ticket != null) {
            ticketDao.editTicket(ticket);
            log.log(Level.INFO, "Ticket updated. Refreshing opened ticket grid...");
            authBean.getTickets(authBean.getCurrentUser().getUsername());
            MessageUtils.showResponseMessage("Editiranje prijave je uspjelo. Editirana prijava. "+ticket.getId(), "Successfull");

        } else {
            log.log(Level.WARNING, "Selected ticket is null, unable to edit ticket. Exiting...");
            MessageUtils.showResponseMessage("Editiranje prijave nije uspjelo.", "Failure");
        }
    }
    
    
    
}
