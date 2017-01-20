/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Category;
import hr.codiraona.model.Priority;
import hr.codiraona.model.Status;
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
public class TicketAddDataDAO implements TicketAddDataDAOLocal {

    @PersistenceContext(unitName = "AlmyIssueTracker")
    private EntityManager em;

    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public List<Priority> getAllPriorities() {
        log.log(Level.INFO, "Fetching all available priorities");
        return em.createNamedQuery("Priority.findAll", Priority.class).getResultList();
    }

    @Override
    public List<Category> getAllCategories() {
        log.log(Level.INFO, "Fetching all available categories");
        return em.createNamedQuery("Category.findAll", Category.class).getResultList();
    }

    @Override
    public List<Status> getAllStatus() {
        log.log(Level.INFO, "Fetching all available statuses");
        return em.createNamedQuery("Status.findAll", Status.class).getResultList();
    }
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean createNewCategory(Category category) {
        log.log(Level.INFO,"Creating new category by name: "+category.getName());
        
        try{
            em.persist(category);
            return true;
        }
        catch(Exception e){
            log.log(Level.SEVERE,"Problem persisting new category. Cause:"+e.getMessage());
            return false;
            
        }
        
    }

    @Override
    public boolean createNewPriority(Priority priority) {
        
        log.log(Level.INFO,"Creating new priority...");
        try{
            em.persist(priority);
            return true;
        }
        catch(Exception e){
            log.log(Level.SEVERE,"Problem persisting priority. Cause: "+e.getMessage());
            return false;
        }
    }
    
    
}
