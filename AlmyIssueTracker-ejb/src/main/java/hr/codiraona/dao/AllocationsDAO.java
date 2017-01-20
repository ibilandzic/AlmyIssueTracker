/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Allocation;
import hr.codiraona.model.Company;
import hr.codiraona.model.Locations;
import hr.codiraona.model.Ticket;
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
public class AllocationsDAO implements AllocationsDAOLocal {
    
    @PersistenceContext(unitName = "AlmyIssueTracker")
    private EntityManager em;

    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public List<Allocation> getAllAllocations() {
        return em.createNamedQuery("Allocation.findAll", Allocation.class)
                .getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Allocation> getUnassignedAllocations() {
        return em.createNamedQuery("Allocation.findUnassignedAllocations", Allocation.class)
                .getResultList();
    }

    @Override
    public boolean createNewAllocation(Allocation allocation) {
        try{
            
            em.persist(allocation);
            log.log(Level.INFO,"New allocation successfully added.");
            return true;
        }
        catch(Exception e){
            log.log(Level.SEVERE,"Problem persisting new allocation. Cause: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAllocation(Allocation allocation) {
        try{
            removeAllocationFromCompany(allocation);
            removeAllocationFromTicket(allocation);
            removeAllocationFromLocation(allocation);
            log.log(Level.INFO,"Removing allocation: "+allocation.getId());
            
            Allocation al = em.find(Allocation.class, allocation.getId());
            em.remove(al);
            return true;
        }
        catch(Exception e){
            log.log(Level.SEVERE,"Problem deleting allocation. Cause: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    
    


    @Override
    public boolean updateAllocation(Allocation allocation) {
        em.merge(allocation);
        return true;
    }

    @Override
    public boolean removeAllocationFromCompany(Allocation allocation) {
        try{
            log.log(Level.INFO,"Removing allocation id: "+allocation.getId()+" from company "+allocation.getCompanyId().getName());
            Company com=allocation.getCompanyId();
            em.merge(com);
            log.log(Level.INFO,"Removing is complete");
            allocation.setCompanyId(null);
            em.merge(allocation);
            return true;
        }
        catch(Exception e){
            log.log(Level.SEVERE,"Removing was not successfull. Cause: "+e.getMessage());
            return false;
        }
    }
    
    
    private boolean removeAllocationFromLocation(Allocation allocation){
        Locations loc =allocation.getLocationId();
        loc.getAllocationList().remove(allocation);
        em.merge(loc);
        allocation.setLocationId(null);
        return true;
        
    }
   private boolean removeAllocationFromTicket(Allocation allocation){
       
       try{
           log.log(Level.INFO,String.format("Removing allocation id= %s from tickets", allocation.getId()));
           for (Ticket ticket:allocation.getTicketList()){
               log.log(Level.INFO,String.format("Ticket id: %s setting allocation_id to null", ticket.getId()));
               ticket.setAllocationId(null);
               em.merge(ticket);
           }
           allocation.setTicketList(null);
           em.merge(allocation);
           log.log(Level.INFO,"Removal from tickets is successfull");
           return true;
       }
       catch(Exception e){
           log.log(Level.SEVERE,"An error occured. Cause: "+e.getMessage());
           return false;
       }
   }

    @Override
    public Allocation find(String value) {
        return em.find(Allocation.class, new Integer(value));
    }
   
   

}
