package de.mariovogel.ghostnetfishing.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "ghostnet")
public class GhostNet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Float estimatedSize;
	private String assignedUser;
	private Boolean editMode = false;
    @OneToOne(cascade = CascadeType.PERSIST)
	private Location location;
    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
        REPORTED("Gemeldet"),
        ANNOUNCED("Bergung bevorstehend"),
        RECOVERED("Geborgen"),
        LOST("Verschollen");

        private final String label;

        State(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
	
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
