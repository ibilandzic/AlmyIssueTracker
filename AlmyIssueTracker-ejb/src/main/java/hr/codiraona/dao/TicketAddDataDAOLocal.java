/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Category;
import hr.codiraona.model.Priority;
import hr.codiraona.model.Status;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author iva.bilandzic
 */
@Local
public interface TicketAddDataDAOLocal {
    
    List<Priority> getAllPriorities();
    
    List<Category> getAllCategories();
    
    List<Status> getAllStatus();

    boolean createNewCategory(Category category);

    boolean createNewPriority(Priority priority);
    
    
}
