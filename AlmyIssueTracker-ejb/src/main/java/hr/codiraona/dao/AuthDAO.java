/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author iva.bilandzic
 */
@Stateless
public class AuthDAO implements AuthDAOLocal {

    Logger log = Logger.getLogger(this.getClass().getName());

    @PersistenceContext(unitName = "AlmyIssueTracker")
    private EntityManager em;

    @Override
    public User login(String username, String password) {
        User currentUser = null;
        try {
            TypedQuery<User> query = em.createNamedQuery("User.findByCredentials", User.class);
            query.setParameter("inUsername", username);
            query.setParameter("inPassword", password);
            currentUser = query.getSingleResult();
        } catch (Exception e) {
            log.log(Level.WARNING, "Problem authenticating user");
            log.log(Level.WARNING, e.getMessage());
        }

        return currentUser;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
