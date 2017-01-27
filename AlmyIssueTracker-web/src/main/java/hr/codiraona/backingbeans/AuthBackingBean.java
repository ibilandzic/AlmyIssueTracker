/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;

import hr.codiraona.dao.AuthDAOLocal;
import hr.codiraona.dao.UserDAOLocal;
import hr.codiraona.model.Ticket;
import hr.codiraona.model.Users;
import hr.codiraona.utils.MessageUtils;
import hr.codiraona.utils.SessionUtils;
import java.io.IOException;
import javax.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;



import javax.servlet.http.HttpSession;


/**
 *
 * @author iva.bilandzic
 */
@ManagedBean
@Named(value = "authBackingBean")
@SessionScoped
public class AuthBackingBean implements Serializable {

    private Logger log = Logger.getLogger(this.getClass().getName());
    @EJB
    private AuthDAOLocal authDao;

    @EJB
    private UserDAOLocal userDao;
    
    private List<Ticket> ticketOpenedByUser;

    public List<Ticket> getTicketOpenedByUser() {
        return ticketOpenedByUser;
    }

    public void setTicketOpenedByUser(List<Ticket> ticketOpenedByUser) {
        this.ticketOpenedByUser = ticketOpenedByUser;
    }

    public List<Ticket> getTicketAssignedToUser() {
        return ticketAssignedToUser;
    }

    public void setTicketAssignedToUser(List<Ticket> ticketAssignedToUser) {
        this.ticketAssignedToUser = ticketAssignedToUser;
    }
    private List<Ticket> ticketAssignedToUser;
    
    private Users currentUser = null;

    private String username;
    private String password;

    
    public String getUsername() {
        return username;
        
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private boolean isRegistered = false;

    public boolean isIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users user) {
        currentUser = user;
    }

    public AuthBackingBean() {
    }

    @PostConstruct
    public void init() {
        currentUser = new Users();
        ticketAssignedToUser = new ArrayList<>();
        ticketOpenedByUser = new ArrayList<>();
    }
    

    public void login() {
        log.log(Level.INFO, "Loggining in...");
        log.log(Level.INFO, "Inputs: username " + getUsername());
        Users user = authDao.login(getUsername(), getPassword());
        setPassword("");
        if (user != null) {
            log.log(Level.INFO, "User fetched");
            setCurrentUser(user);
            setIsRegistered(true);
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user.getUsername());
            getTickets(currentUser.getUsername());
            log.log(Level.INFO,"Redirecting...");
            redirectToPage();
            
        } else {
            SessionUtils.getSession().invalidate();
            MessageUtils.showResponseMessage("Pogrešni korisnički podaci.", "Pogresni podaci");
            setCurrentUser(null);
            setIsRegistered(false);
        }
    }
    
    private void redirectToPage(){
        String redirectedTo = "";
        switch(currentUser.getRoleId().getName()){
            case "admin": redirectedTo = "tickets.xhtml";
            break;
            case "user": redirectedTo = "userPage.xhtml";
            break;
            case "employee": redirectedTo = "employeePage.xhtml";
            break;
        }
        
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try{
          context.redirect(context.getRequestContextPath()+"/" + redirectedTo);  
        }
        catch(IOException e){
            log.log(Level.WARNING,"Page not found");
        }
        
    }

    
   
    public boolean isAdmin() {
        return isRegistered && isInRole("admin");
    }

    public boolean isEmployee() {
        return isRegistered && isInRole("employee");
    }

    public boolean isRegularUser() {
        return isRegistered && isInRole("user");
    }

    private boolean isInRole(String roleName) {
        if (isRegistered) {
            return currentUser.getRoleId().getName().equals(roleName);
        } else {
            return false;
        }
    }

    public void logout() {
        log.log(Level.INFO,"Logging out...");
        setCurrentUser(null);
        setUsername("");
        setPassword("");
        setIsRegistered(false);
        SessionUtils.getSession().invalidate();
        log.log(Level.INFO,"Session invalidated");
        
    }

    public void updateUserData() {
        
        log.log(Level.INFO, "Updating user with username:" + getCurrentUser().getUsername());
        if (userDao.updateUser(getCurrentUser())) {
            MessageUtils.showResponseMessage("Podaci su ažurirani", "Success");

        } else {
            MessageUtils.showResponseMessage("Podaci nisu ažurirani", "Failure");
        }
    }
    
    public void getTickets(String username) {
        if (currentUser!=null){
            ticketAssignedToUser = userDao.getTicketsAssignedToUser(username);
            ticketOpenedByUser = userDao.getTicketsReportedByUser(username);
        }
    }

}
