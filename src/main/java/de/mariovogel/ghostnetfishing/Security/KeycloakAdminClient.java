package de.mariovogel.ghostnetfishing.Security;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class KeycloakAdminClient {
    private final String serverUrl = "http://localhost:8081";
    private final String realm = "master";
    private final String clientId = "admin-cli";
    private final String username = "admin";
    private final String password = "admin";
    
    public Form getClientForm() {
		return new Form()
			.param("grant_type", "password")
			.param("client_id", clientId)
			.param("username", username)
			.param("password", password);
	}

    public String getAdminAccessToken() {
        Client client = ClientBuilder.newClient();
        Form form = getClientForm();

        Response response = client.target(serverUrl + "/realms/" + realm + "/protocol/openid-connect/token")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.form(form));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed to get admin token");
        }

        String json = response.readEntity(String.class);
        return new JSONObject(json).getString("access_token");
    }
    
    public List<String> getAllUsernames() {
		String token = getAdminAccessToken();

		Client client = ClientBuilder.newClient();
		Response response = client.target(serverUrl + "/admin/realms/" + realm + "/users")
			.request(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + token)
			.get();

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed to fetch users");
		}

		List<JSONObject> users = response.readEntity(List.class);
		System.out.println("Fetched users: " + users);
		return users.stream()
				.map(user -> user.getString("username"))
				.toList();
	}

    public void createUser(String username, String password, String phone) {
        String token = getAdminAccessToken();

        Client client = ClientBuilder.newClient();
        
        JSONObject attributes = new JSONObject();
        attributes.put("phoneNumber", phone);

        String userJson = new JSONObject()
            .put("username", username)
            .put("enabled", true)
            .put("attributes", attributes)
            .toString();
        
        

        Response response = client.target(serverUrl + "/admin/realms/ghostnet/users")
            .request()
            .header("Authorization", "Bearer " + token)
            .post(Entity.json(userJson));

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user");
        }

        String location = response.getHeaderString("Location");
        String userId = location.substring(location.lastIndexOf('/') + 1);

        String credJson = new JSONObject()
            .put("type", "password")
            .put("value", password)
            .put("temporary", false)
            .toString();

        client.target(serverUrl + "/admin/realms/ghostnet/users/" + userId + "/reset-password")
            .request()
            .header("Authorization", "Bearer " + token)
            .put(Entity.json(credJson));
    }
}
