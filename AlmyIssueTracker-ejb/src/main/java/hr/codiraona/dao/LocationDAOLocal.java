/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Allocation;
import hr.codiraona.model.Locations;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author iva.bilandzic
 */
@Local
public interface LocationDAOLocal {

    List<Locations> getAllLocations();

    boolean createNewLocation(Locations location);

    boolean updateLocation(Locations location);

    
    
}
