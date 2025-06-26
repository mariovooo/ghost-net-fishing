package de.mariovogel.ghostnetfishing.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "ghostnet")
public class GhostNet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Float estimatedSize;
	private String state;
	private String assignedUser;
	private Boolean editMode = false;
    @OneToOne(cascade = CascadeType.PERSIST)
	private Location location;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Float getEstimatedSize() {
		return estimatedSize;
	}
	
	public void setEstimatedSize(Float estimatedSize) {
		this.estimatedSize = estimatedSize;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getAssignedUser() {
		return assignedUser;
	}
	
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}
	
	public Boolean getEditMode() {
	    return editMode;
	}

	public void setEditMode(Boolean editMode) {
	    this.editMode = editMode;
	}
}
