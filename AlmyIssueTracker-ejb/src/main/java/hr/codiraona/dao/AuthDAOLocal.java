/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.User;
import javax.ejb.Local;

/**
 *
 * @author iva.bilandzic
 */
@Local
public interface AuthDAOLocal {
    
    User login(String username, String password);
    
}
