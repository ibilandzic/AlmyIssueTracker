/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Allocation;
import hr.codiraona.model.Company;
import hr.codiraona.model.Users;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author iva.bilandzic
 */
@Stateless
public class CompanyDAO implements CompanyDAOLocal, Serializable {

    Logger log = Logger.getLogger(this.getClass().getName());

    @EJB
    UserDAOLocal userDao;

    @PersistenceContext(unitName = "AlmyIssueTracker")
    private EntityManager em;

    
    
    @Override
    public boolean createCompany(Company company) {
        try {
            em.persist(company);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCompany(Company company) {
        try {
            em.merge(company);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.SEVERE, String.format("Company: %s not updated", company.getName()));
            log.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeCompany(Company company) {
        /*
        List<Users> users = company.getUsersList();
        boolean isRemoved = true;

        if (users.size()>0) {
            log.log(Level.INFO, "Users found. Starting to remove users.");
            
            for (Users user : users) {
                //remove all users attaching to company
                isRemoved = userDao.removeUser(user.getId());
                log.log(Level.INFO, "User is removed: " + isRemoved);
                if (!isRemoved) {
                    break;
                }
            }
            if (isRemoved) {
                try {
                    em.remove(company);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            log.log(Level.INFO, "Users not found. Removing company.");
            Company companyToRemove = em.merge(company);
            em.remove(companyToRemove);
            return true;
        }*/
        detachAllocations(company);
        removeUsers(company);
        
        if (!em.contains(company)){
          Company com = em.find(Company.class, company.getId());
          em.remove(com);
        }
        else{
        em.remove(company);
        }
        return true;
    }
    
    private void detachAllocations(Company company){
        log.log(Level.INFO,"Detaching allocations from company: "+company.getName());
        for (Allocation alo:company.getAllocationList()){
            alo.setCompanyId(null);
            em.merge(alo);
        }
        log.log(Level.INFO,"Allocations succesfully detached");
        company.getAllocationList().clear();
    }
    
    private void removeUsers(Company company){
        log.log(Level.INFO,"Removing users from company: "+company.getName());
        for (Users user:company.getUsersList()){
            em.remove(user);
        }
        log.log(Level.INFO,"Users removed");
        company.getUsersList().clear();
    }

    @Override
    public List<Company> getAllCompanies() {
        return em.createNamedQuery("Company.findAll", Company.class).getResultList();
    }

    @Override
    public Company find(String value) {
        return em.find(Company.class, new Integer(value));
    }
}
