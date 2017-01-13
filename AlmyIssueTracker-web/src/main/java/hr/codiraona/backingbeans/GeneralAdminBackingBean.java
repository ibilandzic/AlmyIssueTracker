/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;

import hr.codiraona.dao.LocationDAOLocal;
import hr.codiraona.dao.TicketAddDataDAO;
import hr.codiraona.dao.TicketAddDataDAOLocal;
import hr.codiraona.model.Category;
import hr.codiraona.model.Location;
import hr.codiraona.model.Priority;
import java.io.Serializable;
import java.util.List;
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
    @EJB
    TicketAddDataDAOLocal generalDao;
    
    @EJB
    LocationDAOLocal locationDao;
    
    public GeneralAdminBackingBean() {
    }
    
    private List<Category> categories;
    private List<Priority> priorities;
    private List<Location> locations;
    
    @PostConstruct
    public void init(){
        categories = generalDao.getAllCategories();
        priorities = generalDao.getAllPriorities();
        locations = locationDao.getAllLocations();
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

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
    
}
