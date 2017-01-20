/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Allocation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author iva.bilandzic
 */
@Local
public interface AllocationsDAOLocal {

    List<Allocation> getAllAllocations();

    List<Allocation> getUnassignedAllocations();

    boolean createNewAllocation(Allocation allocation);

    boolean deleteAllocation(Allocation allocation);

    boolean updateAllocation(Allocation allocation);

    boolean removeAllocationFromCompany(Allocation allocation);

    Allocation find(String value);
    
}
