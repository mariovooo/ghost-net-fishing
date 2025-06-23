package de.mariovogel.ghostnetfishing;

import java.io.Serializable;
import java.util.List;

import de.mariovogel.ghostnetfishing.Model.GhostNet;
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
    
	private String location;
	private Float estimatedSize;
	private String state;
	private Boolean editMode;
	
    public String submit() {
        GhostNet net = new GhostNet();
        net.setLocation(location);
        net.setEstimatedSize(estimatedSize);
        net.setState("gemeldet");

        service.save(net);

        return "success";
    }
    
    
    private List<GhostNet> allGhostNets;

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
    
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
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

    public Boolean getEditMode() {
		return editMode;
	}
    public void setEditMode(Boolean editMode) {
    	this.editMode = editMode;
    }

}
