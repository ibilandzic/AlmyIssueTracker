/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;

import hr.codiraona.dao.AllocationsDAOLocal;
import hr.codiraona.dao.CompanyDAOLocal;
import hr.codiraona.dao.RoleDAOLocal;
import hr.codiraona.dao.UserDAOLocal;
import hr.codiraona.model.Allocation;
import hr.codiraona.model.Company;
import hr.codiraona.model.Role;
import hr.codiraona.model.Users;
import hr.codiraona.utils.MessageUtils;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;


/**
 *
 * @author iva.bilandzic
 */
@ManagedBean
@Named(value = "companyBackingBean")
@ViewScoped
public class CompanyBackingBean implements Serializable {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @EJB
    CompanyDAOLocal companyDao;

    @EJB
    UserDAOLocal userDao;

    @EJB
    RoleDAOLocal roleDao;
    
    @EJB
    AllocationsDAOLocal allocationDao;
    

    private List<Company> companies;

    private Company company;

    private Company selectedCompany;

    private List<Users> users;


    public List<Users> getUsers() {
        return selectedCompany.getUsersList();
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    private Users user;

    private Users selectedUser;

    private List<Role> roles;
    
    private List<Allocation> availableAllocations;

    public List<Allocation> getAvailableAllocations() {
        return availableAllocations;
    }

    public void setAvailableAllocations(List<Allocation> availableAllocations) {
        this.availableAllocations = availableAllocations;
    }
   

    /**
     * get user form data
     *
     * @return
     */
    public Users getUser() {
        return user;
    }

    /**
     * set user form data
     *
     * @param user
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * get all rols
     *
     * @return
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * set all roles
     *
     * @param roles
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * return currentlly selected user
     *
     * @return
     */
    public Users getSelectedUser() {
        return selectedUser;
    }

    /**
     * sets selected user
     *
     * @param selectedUser
     */
    public void setSelectedUser(Users selectedUser) {
        log.log(Level.INFO,"Role:"+selectedUser.getRoleId().getName());
        this.selectedUser = selectedUser;
    }

    /**
     * return selected company
     *
     * @return
     */
    public Company getSelectedCompany() {
        return selectedCompany;
    }

    /**
     * sets selected company
     *
     * @param selectedCompany
     */
    public void setSelectedCompany(Company selectedCompany) {
        
        this.selectedCompany = selectedCompany;
    }



    /**
     * return list of all companies
     *
     * @return
     */
    public List<Company> getCompanies() {
        return companies;
    }

    public CompanyBackingBean() {

    }

    /**
     * Sets list of all available companies
     * Sets first company as selected
     * Fetched all users in selected company
     * Fetches all available roles
     * Inializes company object for form
     * Initializes user object for form
     * 
     */
    @PostConstruct
    public void init() {
        companies = companyDao.getAllCompanies();
        selectedCompany = companies.get(0);
        users = selectedCompany.getUsersList();
        roles = roleDao.getAllRoles();
        availableAllocations = allocationDao.getUnassignedAllocations();
        initCompany();
        initUser();

    }

    /**
     * initializes data for cretaion of new company
     */
    private void initCompany() {
        company = new Company();
    }
    
    private void initUser(){
        user = new Users();
    }

    /**
     * return object Company
     *
     * @return
     */
    public Company getCompany() {
        return company;
    }

    /**
     * sets object company
     *
     * @param company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * creates new company
     */
    public void createCompany() {
        log.log(Level.INFO, company.getAddress());
        if (companyDao.createCompany(company)) {
            log.log(Level.INFO, "New company succesfully created");
            log.log(Level.INFO, "Fetching data");
            companies = companyDao.getAllCompanies();

        }
        initCompany();
    }

    /**
     * edits company data
     */
    public void editCompany() {
        log.log(Level.INFO, String.format("Editing company %s", selectedCompany.getName()));
        if (companyDao.updateCompany(selectedCompany)) {
            log.log(Level.INFO, "Company updated");
            log.log(Level.INFO, "Fetching data");
            companies = companyDao.getAllCompanies();

        } else {
            log.log(Level.WARNING, "Company not edited!");
        }
    }

    /**
     * deletes new company
     */
    public void deleteCompany() {
        log.log(Level.INFO, String.format("Deleting company %s", selectedCompany.getName()));
        if (companyDao.removeCompany(selectedCompany)) {
            log.log(Level.INFO, "Company removed");
            log.log(Level.INFO, "Fetching data");
            companies = companyDao.getAllCompanies();
        }
    }

    public void editUser() {
        log.log(Level.INFO, String.format("Editing user %s", selectedUser.getUsername()));
        if (userDao.updateUser(selectedUser)) {
            log.log(Level.INFO, "Company updated");
            log.log(Level.INFO, "Fetching data");
            companies = companyDao.getAllCompanies();

        } else {
            log.log(Level.WARNING, "User not edited!");
        }
    }

    public void deleteUser() {
        log.log(Level.INFO, "Deleting user: " + selectedUser.getUsername());
        selectedCompany.getUsersList().remove(selectedUser);
        if (userDao.removeUser(selectedUser.getId()) && companyDao.updateCompany(selectedCompany)) {
            log.log(Level.INFO, "User " + selectedUser.getUsername() + " succesfully deleted");
            log.log(Level.INFO, "Fetching data");
            companies = companyDao.getAllCompanies();
        } else {
            log.log(Level.WARNING, "User not deleted");
        }

    }

    public void createUser() {
        user.setCompanyId(selectedCompany);
        if (userDao.createUser(user)) {
            log.log(Level.INFO, "User " + user.getUsername() + " successfully created");
            selectedCompany.getUsersList().add(user);
            companyDao.updateCompany(selectedCompany);
        } else {
            log.log(Level.WARNING, "User not created");
        }
        
        
    }
    
    public void detachAllocation(Allocation aloc){
        log.log(Level.INFO,"Dealocating space for company "+aloc.getCompanyId().getName());
        selectedCompany.getAllocationList().remove(aloc);
        aloc.setCompanyId(null);
        
        if (companyDao.updateCompany(selectedCompany) && allocationDao.updateAllocation(aloc)){
            MessageUtils.showResponseMessage("Prostor je uspješno dealociran", "Success");
        }
        else{
            MessageUtils.showResponseMessage("Prostor nije dealociran", "Failure");
        }
        
        
    }
    
    public void allocateSpace(Allocation aloc){
        log.log(Level.INFO,"Allocating space for company "+selectedCompany.getName());
        aloc.setCompanyId(selectedCompany);
        selectedCompany.getAllocationList().add(aloc);
        if (companyDao.updateCompany(selectedCompany) && allocationDao.updateAllocation(aloc)){
            MessageUtils.showResponseMessage("Prostor je uspješno alociran", "Success");
        }
        else{
            MessageUtils.showResponseMessage("Prostor nije alociran", "Failure");
        }
        
        
    }

}
