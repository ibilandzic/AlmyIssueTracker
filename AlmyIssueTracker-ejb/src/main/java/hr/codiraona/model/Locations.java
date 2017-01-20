/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author iva.bilandzic
 */
@Entity
@Table(name = "LOCATIONS", schema = "TICKETING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locations.findAll", query = "SELECT l FROM Locations l")
    , @NamedQuery(name = "Locations.findById", query = "SELECT l FROM Locations l WHERE l.id = :id")
    , @NamedQuery(name = "Locations.findByName", query = "SELECT l FROM Locations l WHERE l.name = :name")
    , @NamedQuery(name = "Locations.findByAddress", query = "SELECT l FROM Locations l WHERE l.address = :address")
    , @NamedQuery(name = "Locations.findByCity", query = "SELECT l FROM Locations l WHERE l.city = :city")
    , @NamedQuery(name = "Locations.findByCountry", query = "SELECT l FROM Locations l WHERE l.country = :country")})
public class Locations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "ADDRESS")
    private String address;
    
    @Column(name = "CITY")
    private String city;
    
    @Column(name = "COUNTRY")
    private String country;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
    private List<Allocation> allocationList;

    public Locations() {
    }

    public Locations(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @XmlTransient
    public List<Allocation> getAllocationList() {
        return allocationList;
    }

    public void setAllocationList(List<Allocation> allocationList) {
        this.allocationList = allocationList;
    }


    
}
