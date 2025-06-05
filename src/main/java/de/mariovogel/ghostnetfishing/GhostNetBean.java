package de.mariovogel.ghostnetfishing;

import java.util.List;

import de.mariovogel.ghostnetfishing.Model.GhostNet;
import de.mariovogel.ghostnetfishing.Service.GhostNetService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named(value = "ghostnet")
public class GhostNetBean {
	
	@Inject
    private GhostNetService service;
    
	private String location;
	private Float estimatedSize;
	private String state;
	
    public String submit() {
        GhostNet net = new GhostNet();
        net.setLocation(location);
        net.setEstimatedSize(estimatedSize);
        net.setState(state);

        service.save(net);

        return "success";
    }
    
    
    private List<GhostNet> allGhostNets;

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
}
