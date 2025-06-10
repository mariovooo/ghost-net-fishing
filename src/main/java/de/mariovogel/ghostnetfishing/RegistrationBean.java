package de.mariovogel.ghostnetfishing;

import de.mariovogel.ghostnetfishing.Security.KeycloakAdminClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named(value = "registrationBean")
@RequestScoped
public class RegistrationBean {

    private String username;
    private String password;

    private KeycloakAdminClient kcClient = new KeycloakAdminClient();

    public String register() {
        try {
            kcClient.createUser(username, password);
            return "login.xhtml?faces-redirect=true"; // Weiterleitung zur Login-Seite
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getter / Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
