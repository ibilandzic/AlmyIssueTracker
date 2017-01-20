/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Allocation;
import hr.codiraona.model.Locations;
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
public class LocationDAO implements LocationDAOLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "AlmyIssueTracker")
    private EntityManager em;

    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public List<Locations> getAllLocations() {
        return em.createNamedQuery("Locations.findAll", Locations.class)
                .getResultList();
        
    }
    

    @Override
    public boolean createNewLocation(Locations location) {
        
        log.log(Level.INFO,"Creating new location ...");
        try{
            em.persist(location);
            log.log(Level.INFO,"New location created");
            return true;
        }
        catch(Exception e){
            log.log(Level.SEVERE,"Error occured while persisting new location...");
            log.log(Level.SEVERE,"Cause: "+e.getMessage());
            return false;
        }

    }

    @Override
    public boolean updateLocation(Locations location) {
        try{
            em.merge(location);
            log.log(Level.INFO,"Location updated.");
            return true;
        }
        catch(Exception e){
            log.log(Level.SEVERE,"Location not updated. Cause: "+e.getMessage());
            return false;
        }
    }

   
    
    
    
    
    
    
}
