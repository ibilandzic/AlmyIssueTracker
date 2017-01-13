package hr.codiraona.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ROLE database table.
 * 
 */
@Entity
@Table(name="ROLE", schema="TICKETING")
@NamedQueries({
    @NamedQuery(name="Role.findAll", query="SELECT r FROM Role r"),
    @NamedQuery(name="Role.findByName", query="SELECT r from Role r where r.name=:inName")
})

public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String description;

	private String name;

	public Role() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}