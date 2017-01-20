/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;

import hr.codiraona.dao.AllocationsDAOLocal;
import hr.codiraona.dao.CompanyDAOLocal;
import hr.codiraona.dao.LocationDAOLocal;
import hr.codiraona.dao.TicketAddDataDAOLocal;
import hr.codiraona.model.Allocation;
import hr.codiraona.model.Category;
import hr.codiraona.model.Company;
import hr.codiraona.model.Locations;
import hr.codiraona.model.Priority;
import hr.codiraona.utils.MessageUtils;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author iva.bilandzic
 */
@ManagedBean
@Named(value = "generalAdminBackingBean")
@ViewScoped
public class GeneralAdminBackingBean implements Serializable {

    /**
     * Creates a new instance of GeneralAdminBackingBean
     */
    private Logger log = Logger.getLogger(this.getClass().getName());
    @EJB
    TicketAddDataDAOLocal generalDao;
    
    @EJB
    LocationDAOLocal locationDao;
    
    @EJB
    AllocationsDAOLocal allocationDao;
    
    @EJB
    CompanyDAOLocal companyDao;
    
    public GeneralAdminBackingBean() {
    }
    
    private List<Category> categories;
    private List<Priority> priorities;
    private List<Locations> locations;
    private List<Allocation> allocations;
    private List<Company> companies;

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Allocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
    }
    
    private Locations newLocation;
    
    private Category newCategory;
    
    private Priority newPriority;
    
    private Locations selectedLocation;
    
    private Allocation newAllocation;
    
    private Allocation selectedAllocation;

    public Allocation getSelectedAllocation() {
        return selectedAllocation;
    }

    public void setSelectedAllocation(Allocation selectedAllocation) {
        this.selectedAllocation = selectedAllocation;
    }

    public Allocation getNewAllocation() {
        return newAllocation;
    }

    public void setNewAllocation(Allocation newAllocation) {
        this.newAllocation = newAllocation;
    }

    public Locations getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(Locations selectedLocation) {
        log.log(Level.INFO,"Location id:"+selectedLocation.getId());
        this.selectedLocation = selectedLocation;
    }

    public Priority getNewPriority() {
        return newPriority;
    }

    public void setNewPriority(Priority newPriority) {
        this.newPriority = newPriority;
    }

    public Category getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(Category newCategory) {
        this.newCategory = newCategory;
    }

    public Locations getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(Locations newLocation) {
        this.newLocation = newLocation;
    }
    
    @PostConstruct
    public void init(){
        categories = generalDao.getAllCategories();
        priorities = generalDao.getAllPriorities();
        locations = locationDao.getAllLocations();
        newLocation = new Locations();
        newCategory = new Category();
        newPriority = new Priority();
        newAllocation = new Allocation();
        selectedLocation = locations.get(0);
        allocations = selectedLocation.getAllocationList();
        companies = companyDao.getAllCompanies();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Priority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<Priority> priorities) {
        this.priorities = priorities;
    }

    public List<Locations> getLocations() {
        return locations;
    }

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }
    
    public void createNewLocation(){
        if(locationDao.createNewLocation(newLocation)){
            log.log(Level.INFO,"Fetching locations ...");
            fetchLocations();
            MessageUtils.showResponseMessage("Uspješno dodavanje nove lokacije", "Uspješno ste dodali novu lokaciju");
        }
        else{
            MessageUtils.showResponseMessage("Nesupješno dodavanje lokacije", "Dodavanje lokacije nije uspjelo");
        }
        newLocation = new Locations();
    }
    
    public void createNewCategory(){
        if(generalDao.createNewCategory(newCategory)){
            log.log(Level.INFO,"Fetching categories...");
            fetchCategories();
            MessageUtils.showResponseMessage("Uspješno dodavanje nove lokacije", "Uspješno ste dodali novu lokaciju");
        }
        else{
            MessageUtils.showResponseMessage("Nesupješno dodavanje lokacije", "Dodavanje lokacije nije uspjelo");
        }
        
        newCategory = new Category();
    }
    
    public void createNewPriority(){
        if(generalDao.createNewPriority(newPriority)){
            log.log(Level.INFO,"Fetching priorities...");
            fetchPriorities();
            MessageUtils.showResponseMessage("Uspješno dodavanje novog prioriteta", "Uspješno ste dodali novi prioritet");
        }
        else{
            MessageUtils.showResponseMessage("Nesupješno dodavanje prioriteta", "Dodavanje prioriteta nije uspjelo");
        }
        
        newPriority = new Priority();
    }
    
    public void allocateSpace(){
        log.log(Level.INFO,"Allocating space..");
        
        newAllocation.setLocationId(selectedLocation);
        selectedLocation.getAllocationList().add(newAllocation);
        log.log(Level.INFO,"Allocation in location name:"+newAllocation.getLocationId().getName());
        log.log(Level.INFO,"Allocation in location id:"+newAllocation.getLocationId().getId());
       
        if (locationDao.updateLocation(selectedLocation)){
            MessageUtils.showResponseMessage("Uspješno alociranje prostora", "Uspješno alociranje prostora");
            
        }
        /*
        if (allocationDao.createNewAllocation(newAllocation)){
            MessageUtils.showResponseMessage("Uspješno alociranje prostora", "Uspješno alociranje prostora");
        }*/
        else{
            MessageUtils.showResponseMessage("Neuspješno alociranje prostora", "Nesupješno alociranje prostora");
        }
        //initialize new allocation form
        
        fetchLocations();
        selectedLocation = newAllocation.getLocationId();
        allocations = selectedLocation.getAllocationList();
        newAllocation = new Allocation();
        
    }
    
    private void fetchCategories(){
        categories = generalDao.getAllCategories();
    }
    
    private void fetchPriorities(){
        priorities = generalDao.getAllPriorities();
    }
    
    private void fetchLocations(){
        locations = locationDao.getAllLocations();
    }
    
    public void deleteAllocation(){
     
        if (allocationDao.deleteAllocation(selectedAllocation)){
            MessageUtils.showResponseMessage("Alokacija je uspješno uklonjena", "Alokacija je uklonjena");
        }
        else{
            MessageUtils.showResponseMessage("Alokacija nije uklonjena", "Alokacija nije uklonjena");
        }
    }
    
    public void updateLocation(){
        if (locationDao.updateLocation(selectedLocation)){
            MessageUtils.showResponseMessage(String.format("Promjene na lokaciji %s su sačuvanje", selectedLocation.getName()), "Lokacija je osvježena");
        }
        else{
            MessageUtils.showResponseMessage(String.format("Promjene na lokaciji %s nisu sačuvanje", selectedLocation.getName()), "Lokacija nije osvježena");
        }
    }
    
    public void updateAllocation(){
        if (allocationDao.updateAllocation(selectedAllocation)){
             MessageUtils.showResponseMessage("Promjene su sačuvane", "Alokacija je osvježena");
        }
        else{
             MessageUtils.showResponseMessage("Promjene nisu sačuvane", "Alokacija nije osvježena");
        }
    }
    
}
