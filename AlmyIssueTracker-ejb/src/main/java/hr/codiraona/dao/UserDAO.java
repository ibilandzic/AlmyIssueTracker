/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.dto.UserDTO;
import hr.codiraona.model.Allocation;
import hr.codiraona.model.Messages;
import hr.codiraona.model.Role;
import hr.codiraona.model.Ticket;
import hr.codiraona.model.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author iva.bilandzic
 */
@Stateless
public class UserDAO implements UserDAOLocal {

    Logger log = Logger.getLogger(this.getClass().getName());

    @PersistenceContext(unitName = "AlmyIssueTracker")
    private EntityManager em;
    
    @EJB
    private RoleDAOLocal roleDao;
    

    @Override
    public List<Users> getAllUsers() {
        try {
            return em.createNamedQuery("Users.findAll", Users.class).getResultList();
        } catch (Exception e) {
            log.log(Level.WARNING, "Problem fetching all existing users");
            log.log(Level.WARNING, e.getMessage());
            return null;
        }
        
    }

    @Override
    public boolean createUser(Users user) {
        try {
            em.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(Users user) {
        boolean isUpdated = false;
        try {
            em.merge(user);
            log.log(Level.INFO,String.format("User: %s successfully updated", user.getUsername()));
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE,String.format("User: %s not updated",user.getUsername()));
            log.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }


    @Override
    public List<Ticket> getTicketsReportedByUser(String username) {
        try {
            TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByReportedBy", Ticket.class);
            query.setParameter("reportedBy", username);
            List<Ticket> tickets = query.getResultList();
            if (tickets != null) {
                return tickets;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    

    @Override
    public List<Ticket> getTicketsAssignedToUser(String username) {
        try {
            TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByAssignedTo", Ticket.class);
            query.setParameter("assignedTo", username);
            List<Ticket> tickets = query.getResultList();
            if (tickets != null) {
                return tickets;
            } else {
                return null;
            }
        } catch (Exception e) {

            return null;
        }
    }

    @Override
    public boolean removeUser(int id) {
        try {
            //get user by id
            Users user = em.find(Users.class, id);

            if (user != null) {
                log.log(Level.INFO, "Removing user :" + user.getUsername());
                //set assignedTo and reportedBy fields in tickets to null
                setAssignedToErased(user.getUsername());
                setReportedByToErased(user.getUsername());
                //set posted by field in message to username and erased
                setPostedByToErased(user.getUsername());
                em.remove(user);

            } else {
                log.log(Level.INFO, "No users to remove");
            }
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "An error has occur during deleting user");
            log.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private void setPostedByToErased(String username) {
        TypedQuery<Messages> query = em.createNamedQuery("Messages.findByPostedBy", Messages.class);
        query.setParameter("postedBy", username);
        List<Messages> messages = query.getResultList();
        if (!messages.isEmpty()) {
            for (Messages message : messages) {
                message.setPostedBy(String.format("%s-erased", username));
                em.merge(message);
            }
        }
    }

    private void setAssignedToErased(String username) {
        TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByAssignedTo", Ticket.class);
        query.setParameter("assignedTo", username);
        List<Ticket> tickets = query.getResultList();
        if (!tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                ticket.setAssignedTo(String.format("%s-erased", username));
                em.merge(ticket);
            }
        }
    }

    private void setReportedByToErased(String username) {
        TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByReportedBy", Ticket.class);
        query.setParameter("reportedBy", username);
        List<Ticket> tickets = query.getResultList();
        if (!tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                ticket.setReportedBy(String.format("%s-erased", username));
                em.merge(ticket);
            }
        }
    }

    @Override
    public boolean setPassword(String password, int user_id) {
        try {
            Users user = em.find(Users.class, user_id);
            user.setPassword(password);
            em.merge(user);
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public List<Users> getUsersByRole(String inRoleName) {
        Role role = roleDao.getRoleByName(inRoleName);
        return em.createNamedQuery("Users.findByRole", Users.class)
                .setParameter("inRole", role)
                .getResultList();
    }

    @Override
    public List<Ticket> getTicketsReportedByCompany(String username) {
        TypedQuery<Users> query = em.createNamedQuery("Users.findByUsername", Users.class);
        query.setParameter("username", username);
        List<Allocation> allocations = query.getSingleResult().getCompanyId().getAllocationList();
        List<Ticket> allTickets = new ArrayList<>();
        
        for (Allocation allocation : allocations){
            allTickets.addAll(allocation.getTicketList());
        }
        
        return allTickets;
    }

}
