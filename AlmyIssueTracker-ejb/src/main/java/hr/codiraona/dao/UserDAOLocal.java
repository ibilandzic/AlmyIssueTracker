/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.dto.UserDTO;
import hr.codiraona.model.Ticket;
import hr.codiraona.model.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author iva.bilandzic
 */
@Local
public interface UserDAOLocal {
    
    List<User> getAllUsers();
    
    public boolean createUser(User user);
    
    public boolean updateUser(UserDTO userDTO);
    
    public boolean setPassword(String password, int user_id);
    
    public List<Ticket> getTicketsReportedByUser(String username);
    
    public List<Ticket> getTicketsAssignedByUser(String username);
    
    public boolean removeUser(int id);
    
    
}
