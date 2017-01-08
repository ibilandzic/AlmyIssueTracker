/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Role;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Iva
 */
@Stateless
public class RoleDAO implements RoleDAOLocal {

     @PersistenceContext(unitName = "AlmyIssueTracker")
    private EntityManager em;
    
     Logger log = Logger.getLogger(this.getClass().getName());
    @Override
    public List<Role> getAllRoles() {
    
      try{
        return em.createNamedQuery("Role.findAll", Role.class).getResultList();
      }  
      catch(Exception e){
          log.log(Level.WARNING, "No roles find");
          log.log(Level.SEVERE,e.getMessage());
          return null;
      }
    
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
