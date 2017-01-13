/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Ticket;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author iva.bilandzic
 */
@Local
public interface TicketDAOLocal {

    List<Ticket> getAllOpenedTickets();

    List<Ticket> getAllTickets();

    List<Ticket> getAllClosedTickets();

    boolean createNewTicket(Ticket ticket);

    boolean editTicket(Ticket ticket);

    boolean removeTicket(Ticket ticket);
    
}
