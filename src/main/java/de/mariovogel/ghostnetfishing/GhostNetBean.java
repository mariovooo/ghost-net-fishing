package de.mariovogel.ghostnetfishing;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.mariovogel.ghostnetfishing.Model.GhostNet;
import de.mariovogel.ghostnetfishing.Model.GhostNet.State;
import de.mariovogel.ghostnetfishing.Model.Location;
import de.mariovogel.ghostnetfishing.Service.GhostNetService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ViewScoped
@Named(value = "ghostnet")
public class GhostNetBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
    private GhostNetService service;
    
	private Float estimatedSize;
	private State state;
	private String assignedUser;
	private Boolean editMode = false;
    private Location location = new Location(0.0, 0.0);
    private String reporterName;
    private String reporterPhone;
	
    public String submit() {
        GhostNet net = new GhostNet();
        net.setLocation(location);
        net.setEstimatedSize(estimatedSize);
        net.setAssignedUser(assignedUser);
        net.getState();
		net.setState(State.REPORTED);
		if (reporterName != null && !reporterName.isEmpty()) {
			net.setReporterName(reporterName);
		}
		
		if (reporterPhone != null && !reporterPhone.isEmpty()) {
			net.setReporterPhone(reporterPhone);
		}

        service.save(net);

        return "success";
    }
    
    public String update(GhostNet ghostNet) {
		ghostNet.setEditMode(false);
        service.update(ghostNet);
		return "success";
	}
    
    private List<GhostNet> allGhostNets;
    
    public List<GhostNet.State> getAllStates() {
    	System.out.println(Arrays.asList(GhostNet.State.values()));
        return Arrays.asList(GhostNet.State.values());
    }

    @PostConstruct
    public void init() {
        allGhostNets = service.findAll();
    }

    public List<GhostNet> getAllGhostNets() {
        return allGhostNets;
    }
    
    public GhostNetService getService() {
		return service;
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
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public String getReporterName() {
		return reporterName;
	}
	
	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}
	
	public String getReporterPhone() {
		return reporterPhone;
	}
	
	public void setReporterPhone(String reporterPhone) {
		this.reporterPhone = reporterPhone;
	}
	
	public String getAssignedUser() {
		return assignedUser;
	}
	
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}
	
	public void assignUser(GhostNet ghostNet, String user) {
		ghostNet.setAssignedUser(user);
		update(ghostNet);
	}

    public Boolean getEditMode() {
		return editMode;
	}
    public void setEditMode(Boolean editMode) {
    	this.editMode = editMode;
    }

}
