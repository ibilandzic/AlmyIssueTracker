package hr.codiraona.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ISSUE_CATEGORY database table.
 * 
 */
@Entity
@Table(name="ISSUE_CATEGORY")
@NamedQuery(name="IssueCategory.findAll", query="SELECT i FROM IssueCategory i")
public class IssueCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String description;

	private String name;

	public IssueCategory() {
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