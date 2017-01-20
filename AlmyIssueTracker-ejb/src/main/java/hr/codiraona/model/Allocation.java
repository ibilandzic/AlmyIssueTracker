/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.model;


import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ALLOCATION",schema = "TICKETING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Allocation.findAll", query = "SELECT a FROM Allocation a")
    , @NamedQuery(name = "Allocation.findById", query = "SELECT a FROM Allocation a WHERE a.id = :id")
    , @NamedQuery(name = "Allocation.findByBuildingName", query = "SELECT a FROM Allocation a WHERE a.buildingName = :buildingName")
    , @NamedQuery(name = "Allocation.findByRoomRange", query = "SELECT a FROM Allocation a WHERE a.roomRange = :roomRange")
    , @NamedQuery(name = "Allocation.findByFloor", query = "SELECT a FROM Allocation a WHERE a.floor = :floor")
    , @NamedQuery(name = "Allocation.findByFloorPart", query = "SELECT a FROM Allocation a WHERE a.floorPart = :floorPart")
    , @NamedQuery(name = "Allocation.findUnassignedAllocations", query="SELECT a from Allocation a where a.companyId is null")
})
public class Allocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "BUILDING_NAME")
    private String buildingName;
    @Size(max = 255)
    @Column(name = "ROOM_RANGE")
    private String roomRange;
    @Size(max = 255)
    @Column(name = "FLOOR")
    private String floor;
    @Size(max = 255)
    @Column(name = "FLOOR_PART")
    private String floorPart;
    @OneToMany(mappedBy = "allocationId")
    private List<Ticket> ticketList;
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Company companyId;
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Locations locationId;

    public Allocation() {
    }

    public Allocation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomRange() {
        return roomRange;
    }

    public void setRoomRange(String roomRange) {
        this.roomRange = roomRange;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFloorPart() {
        return floorPart;
    }

    public void setFloorPart(String floorPart) {
        this.floorPart = floorPart;
    }

    @XmlTransient
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public Locations getLocationId() {
        return locationId;
    }

    public void setLocationId(Locations locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Allocation)) {
            return false;
        }
        Allocation other = (Allocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
