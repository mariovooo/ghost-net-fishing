package de.mariovogel.ghostnetfishing.Model;

public class KeycloakUser {
    private String username;
    private String phoneNumber;

    public KeycloakUser(String username, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
