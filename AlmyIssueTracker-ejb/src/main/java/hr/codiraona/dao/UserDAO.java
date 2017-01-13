/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.dto.UserDTO;
import hr.codiraona.model.Message;
import hr.codiraona.model.Role;
import hr.codiraona.model.Ticket;
import hr.codiraona.model.User;
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
    public List<User> getAllUsers() {
        try {
            return em.createNamedQuery("User.findAll", User.class).getResultList();
        } catch (Exception e) {
            log.log(Level.WARNING, "Problem fetching all existing users");
            log.log(Level.WARNING, e.getMessage());
            return null;
        }
        
    }

    @Override
    public boolean createUser(User user) {
        try {
            em.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
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

    private void setUserValues(User user, UserDTO userDTO) {
        user.setCompany(userDTO.getCompany());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setRole(userDTO.getRole());
        user.setUsername(userDTO.getUsername());
        user.setLocation(userDTO.getLocation());
    }

    @Override
    public List<Ticket> getTicketsReportedByUser(String username) {
        try {
            TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByReported", Ticket.class);
            query.setParameter("inUsername", username);
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
    public List<Ticket> getTicketsAssignedByUser(String username) {
        try {
            TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByAssignee", Ticket.class);
            query.setParameter("inUsername", username);
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
            User user = em.find(User.class, id);

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
        TypedQuery<Message> query = em.createNamedQuery("Message.findByAuthor", Message.class);
        query.setParameter("inUsername", username);
        List<Message> messages = query.getResultList();
        if (!messages.isEmpty()) {
            for (Message message : messages) {
                message.setPostedBy(String.format("%s-erased", username));
                em.merge(message);
            }
        }
    }

    private void setAssignedToErased(String username) {
        TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByAssignee", Ticket.class);
        query.setParameter("inUsername", username);
        List<Ticket> tickets = query.getResultList();
        if (!tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                ticket.setAssignedTo(String.format("%s-erased", username));
                em.merge(ticket);
            }
        }
    }

    private void setReportedByToErased(String username) {
        TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByReporter", Ticket.class);
        query.setParameter("inUsername", username);
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
            User user = em.find(User.class, user_id);
            user.setPassword(password);
            em.merge(user);
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public List<User> getUsersByRole(String inRoleName) {
        Role role = roleDao.getRoleByName(inRoleName);
        return em.createNamedQuery("User.findByRole", User.class)
                .setParameter("inRole", role)
                .getResultList();
    }

}
