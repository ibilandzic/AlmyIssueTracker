package hr.codiraona.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the PRIORITY database table.
 *
 */
@Entity
@Table(name = "PRIORITY", schema = "TICKETING")
@NamedQuery(name = "Priority.findAll", query = "SELECT p FROM Priority p ORDER BY p.priorityNumber ASC")
public class Priority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    private String name;

    @Column(name = "PRIORITY_NUMBER",unique = true)
    private int priorityNumber;

    public Priority() {
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

    public int getPriorityNumber() {
        return this.priorityNumber;
    }

    public void setPriorityNumber(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

}
