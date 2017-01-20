/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Messages;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author iva.bilandzic
 */
@Stateless
public class MessageDAO implements MessageDAOLocal {

    @PersistenceContext(unitName = "AlmyIssueTracker")
    private EntityManager em;

    private Logger log = Logger.getLogger(this.getClass().getName());

    public boolean removeMessage(Messages message) {

        try {
            log.log(Level.INFO,"Removing message");
            em.remove(this);
            return true;
        } catch (Exception e) {
            log.log(Level.INFO, "Problem removing message");
            log.log(Level.INFO, "Cause: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean createMessage(Messages message) {
       
        try{
            log.log(Level.INFO,"Creating message");
            em.persist(message);
            return true;
        }
        catch(Exception e){
            log.log(Level.SEVERE,"Exception occured while creating message");
            log.log(Level.SEVERE,"Cause: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean editMessage(Messages message) {
        try{
            log.log(Level.INFO,"Editing message");
            em.merge(message);
            return true;
        }
        catch(Exception e){
            log.log(Level.SEVERE,"Exception occured while editing message");
            log.log(Level.SEVERE,"Cause: "+e.getMessage());
            return false;
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
