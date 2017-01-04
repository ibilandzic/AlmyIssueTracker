/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.backingbeans;



import hr.codiraona.dao.CompanyDAOLocal;
import hr.codiraona.model.Company;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author iva.bilandzic
 */

@Named(value = "companyBackingBean")
@RequestScoped
public class CompanyBackingBean implements Serializable{
    private Logger log = Logger.getLogger(this.getClass().getName());
   
    @EJB
    CompanyDAOLocal companyDao;
    
    private List<Company> companies;
    
    private Company company;
    
    private Company selectedCompany;

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }
    
    private Dialog newCompanyDialog;

    public Dialog getNewCompanyDialog() {
        return newCompanyDialog;
    }

    public void setNewCompanyDialog(Dialog newCompanyDialog) {
        this.newCompanyDialog = newCompanyDialog;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    
    public CompanyBackingBean() {
      
    }
    
    @PostConstruct
    public void init(){
          log.log(Level.INFO, "EJB is:" + companyDao);
          companies = companyDao.getAllCompanies();
          initCompany();
          
          
    }
    
    private void initCompany(){
        company = new Company();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    
    public void createCompany(){
        log.log(Level.INFO,company.getAddress());
        if (companyDao.createCompany(company)){
            log.log(Level.INFO,"New company succesfully created");
            log.log(Level.INFO,"Fetching data");
            companies = companyDao.getAllCompanies();
      
        }
              initCompany();
    }
    
    public void editCompany(){
        log.log(Level.INFO, String.format("Editing company %s", selectedCompany.getName()));
        if (companyDao.updateCompany(selectedCompany)){
            log.log(Level.INFO,"Company updated");
            log.log(Level.INFO,"Fetching data");
            companies = companyDao.getAllCompanies();
      
        }
    }
    
    public void deleteCompany(){
        log.log(Level.INFO, String.format("Deleting company %s", selectedCompany.getName()));
        if (companyDao.removeCompany(selectedCompany)){
            log.log(Level.INFO,"Company removed");
            log.log(Level.INFO,"Fetching data");
            companies = companyDao.getAllCompanies();
        }
    }

    
    
}
