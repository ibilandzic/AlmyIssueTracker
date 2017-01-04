package hr.codiraona.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ALLOCATION database table.
 * 
 */
@Entity
@NamedQuery(name="Allocation.findAll", query="SELECT a FROM Allocation a")
public class Allocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="BUILDING_NAME")
	private String buildingName;

	@Column(name="ROOM_RANGE")
	private String roomRange;

	//uni-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="LOCATION_ID")
	private Location location;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="COMPANY_ID")
	private Company company;

	public Allocation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBuildingName() {
		return this.buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getRoomRange() {
		return this.roomRange;
	}

	public void setRoomRange(String roomRange) {
		this.roomRange = roomRange;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}