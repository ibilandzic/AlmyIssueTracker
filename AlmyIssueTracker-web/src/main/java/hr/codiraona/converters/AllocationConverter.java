/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.converters;

import hr.codiraona.dao.AllocationsDAOLocal;
import hr.codiraona.model.Allocation;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author iva.bilandzic
 */
@ManagedBean(name = "allocationConverterBean")
@FacesConverter(value="allocationConverter")
public class AllocationConverter implements Converter {
    @EJB
    AllocationsDAOLocal allocDao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       
        return allocDao.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Allocation)value).toString();
    }

    /**
     * Creates a new instance of CompanyConverter
     */
}
