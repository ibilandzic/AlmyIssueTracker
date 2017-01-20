/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Role;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Iva
 */
@Local
public interface RoleDAOLocal {
    List<Role> getAllRoles();
    Role getRoleByName(String inName);
    
   Role find(String value);
}
