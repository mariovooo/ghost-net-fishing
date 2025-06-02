package de.mariovogel.ghostnetfishing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named(value = "TestBean")
public class TestBean {

	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
    public void submit() {
        System.out.println("Hallo" + name + "!");
    }
	
}
