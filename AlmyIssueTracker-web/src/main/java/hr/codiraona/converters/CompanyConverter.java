/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.converters;

import hr.codiraona.dao.CompanyDAOLocal;
import hr.codiraona.model.Company;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author iva.bilandzic
 */
@ManagedBean(name = "companyConverterBean")
@FacesConverter(value="companyConverter")
public class CompanyConverter implements Converter {

    @EJB
    CompanyDAOLocal companyDao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       
        return companyDao.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Company)value).toString();
    }

    /**
     * Creates a new instance of CompanyConverter
     */
}
