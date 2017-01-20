/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.dao;

import hr.codiraona.model.Company;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author iva.bilandzic
 */
@Local
public interface CompanyDAOLocal {
    
    boolean createCompany(Company company);
    
    boolean updateCompany(Company company);
    
    boolean removeCompany(Company company);
    
    List<Company> getAllCompanies();

    Company find(String value);
    
    
    
}
