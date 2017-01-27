/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Messages;
import hr.codiraona.model.Ticket;
import hr.codiraona.model.Users;
import hr.codiraona.util.Constants;
import hr.codiraona.util.EmailUtils;
import java.sql.Timestamp;
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
public class TicketDAO implements TicketDAOLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "AlmyIssueTracker")
    private EntityManager em;

    Logger log = Logger.getLogger(this.getClass().getName());

    @EJB
    private MessageDAOLocal messageDao;

    @Override
    public List<Ticket> getAllOpenedTickets() {
        log.log(Level.INFO, "Fetching opened tickets...");
        TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByStatus", Ticket.class);
        query.setParameter("status", Constants.OPEN_TICKET);

        if (query.getResultList().size() > 0) {
            log.log(Level.INFO, "Open tickets exist...");
        } else {
            log.log(Level.INFO, "Open tickets does not exist...");
        }
        return query.getResultList();
    }

    @Override
    public List<Ticket> getAllTickets() {

        return em.createNamedQuery("Ticket.findAll", Ticket.class).getResultList();
    }

    @Override
    public List<Ticket> getAllClosedTickets() {
        log.log(Level.INFO, "Fetching closed tickets...");
        TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByStatus", Ticket.class);
        query.setParameter("status", Constants.CLOSED_TICKET);
        return query.getResultList();
    }

    @Override
    public boolean createNewTicket(Ticket ticket) {
        log.log(Level.INFO, "Creating new ticket...");
        try {
            em.persist(ticket);
            log.log(Level.INFO, "Ticket succesfully created!");
            sendNotification(ticket.getReportedBy(),
                    Constants.OPEN_TICKET_SUBJECT,
                    constructMessageForOpen(ticket)
            );
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Problem occured during persisting new ticket");
            log.log(Level.SEVERE, "Cause: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean editTicket(Ticket ticket) {
        try {
            log.log(Level.INFO, "Updating ticket");
            //if ticket is closing, set closed at field
            if (ticket.getStatus().equals(Constants.CLOSED_TICKET)) {
                log.log(Level.INFO,"Ticket closed");
                ticket.setClosedAt(new Timestamp(System.currentTimeMillis()));
                //
                sendNotification(ticket.getReportedBy(), 
                        Constants.CLOSED_TICKET_SUBJECT,
                        constructMessageForClosing(ticket));
            }
            em.merge(ticket);
            return true;

        } catch (Exception e) {
            log.log(Level.SEVERE, "Ticket not updated");
            log.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeTicket(Ticket ticket) {
        try {
            if (removeMessage(ticket.getMessagesList())) {
                log.log(Level.INFO, "Removing ticket");
                em.remove(ticket);
                return true;
            } else {
                log.log(Level.SEVERE, "Messages not removed. Ticket will not be removed. B reaking from operation...");
                return false;
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Remove not successfull");
            log.log(Level.SEVERE, "Cause: " + e.getMessage());
            return false;
        }
    }

    private boolean removeMessage(List<Messages> messages) {
        boolean isRemoved = true;

        for (Messages message : messages) {
            isRemoved = messageDao.removeMessage(message);
            if (!isRemoved) {
                log.log(Level.SEVERE, "Message id:" + message.getId() + " not removed. Breking from operation.");
                break;
            } else {
                log.log(Level.INFO, "Message id:" + message.getId() + " removed.");
            }
        }

        if (isRemoved) {
            log.log(Level.INFO, "All messages successfully removed");
        }
        return isRemoved;
    }
    
    private void sendNotification(String username, String subject, String msg) {
        log.log(Level.INFO, "Searching email for specific user...");
        
        Users user = em.createNamedQuery("Users.findByUsername", Users.class)
                .setParameter("username", username)
                .getSingleResult();
        
        if (EmailUtils.sendNotification(user, subject, msg)){
           log.log(Level.INFO,"Notification has been successfully sent");
        }
        else{
          log.log(Level.SEVERE,"Problem sending notification");  
        }
        
                log.log(Level.INFO,"Notification has been successfully sent");
                
        
    }
    
      
    private String constructMessageForClosing(Ticket ticket){
        return "Poštovani,"
                + "\nVaša prijava pod naslovom: "+ticket.getTitle()+" je zatvorena."
                + " Molimo Vas da provjerite da li ste zadovoljni ishodom."
                + "\nLijep pozdrav,"
                + "\n Trius nekretnine";
    }

    private String constructMessageForOpen(Ticket ticket) {
        return "Poštovani,"
                + "\nOtvorili ste prijavu pod naslovom: "+ticket.getTitle()+"."
                + " Prijava će biti riješena u najkraćem mogućem roku."
                + "\nStanje prijave možete pratiti na korisničkim stranicama."
                + "\nLijep pozdrav,"
                + "\n Trius nekretnine";
    }

   
}
