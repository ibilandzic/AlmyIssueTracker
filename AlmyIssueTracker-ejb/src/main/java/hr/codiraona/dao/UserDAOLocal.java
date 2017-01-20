/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.dto.UserDTO;
import hr.codiraona.model.Role;
import hr.codiraona.model.Ticket;
import hr.codiraona.model.Users;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author iva.bilandzic
 */
@Local
public interface UserDAOLocal {
    
    List<Users> getAllUsers();
    
    public boolean createUser(Users user);
    
    public boolean updateUser(Users user);
    
    public boolean setPassword(String password, int user_id);
    
    public List<Ticket> getTicketsReportedByUser(String username);
    
    public List<Ticket> getTicketsAssignedToUser(String username);
    
    public boolean removeUser(int id);
    
    List<Users> getUsersByRole(String inRoleName);

    List<Ticket> getTicketsReportedByCompany(String username);
    
    
}
