/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;

import hr.codiraona.dao.AuthDAOLocal;
import hr.codiraona.dao.UserDAOLocal;
import hr.codiraona.model.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;

/**
 *
 * @author iva.bilandzic
 */
@Named(value = "authBackingBean")
@SessionScoped
public class AuthBackingBean implements Serializable {

    private Logger log = Logger.getLogger(this.getClass().getName());
    @EJB
    private AuthDAOLocal authDao;

    @EJB
    private UserDAOLocal userDao;

    private User currentUser = null;

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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public AuthBackingBean() {
    }

    @PostConstruct
    public void init() {

        currentUser = new User();
    }

    public void login() {
        log.log(Level.INFO, "Loggining in...");
        log.log(Level.INFO, "Inputs: username " + getUsername());
        User user = authDao.login(getUsername(), getPassword());
        setPassword("");
        if (user != null) {
            log.log(Level.INFO, "User fetched");
            setCurrentUser(user);
            setIsRegistered(true);
            refreshPage();
        } else {
            setCurrentUser(null);
            setIsRegistered(false);
        }
    }

    private void refreshPage() {
        RequestContext.getCurrentInstance().update("authToolbar");
        RequestContext.getCurrentInstance().update("dataUnit");
    }

    public boolean isAdmin() {
        return isInRole("admin");
    }

    public boolean isEmployee() {
        return isInRole("employee");
    }

    public boolean isRegularUser() {
        return isInRole("user");
    }

    private boolean isInRole(String roleName) {
        if (isRegistered) {
            return currentUser.getRole().getName().equals(roleName);
        } else {
            return false;
        }
    }

    public void logout() {
        setCurrentUser(null);
        setUsername("");
        setPassword("");
        setIsRegistered(false);
    }

}
