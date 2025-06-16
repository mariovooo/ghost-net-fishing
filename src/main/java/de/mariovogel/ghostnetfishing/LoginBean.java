package de.mariovogel.ghostnetfishing;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.json.bind.JsonbBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Form;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
	private String idToken;
    private String username;

    public void handleCallback() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();

        String code = params.get("code");
        if (code != null) {
            // Token holen
            var client = ClientBuilder.newClient();
            var form = new Form();
            form.param("grant_type", "authorization_code");
            form.param("code", code);
            form.param("client_id", "ghostnet-client");
            form.param("redirect_uri", "http://localhost:8080/GhostNetFishing/callback.xhtml");

            var response = client
                .target("http://localhost:8081/realms/ghostnet/protocol/openid-connect/token")
                .request()
                .post(jakarta.ws.rs.client.Entity.form(form));

            String jsonString = response.readEntity(String.class);
            System.out.println("Antwort von Keycloak:\n" + jsonString);
            var jsonb = JsonbBuilder.create();
            var json = jsonb.fromJson(jsonString, java.util.Map.class);
            this.token = (String) json.get("access_token");
            this.idToken = (String) json.get("id_token");

            
            // Weiterleitung auf geschützte Seite
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("secure.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
    
    public String getIdToken() {
        return idToken;
    }
    
    public void checkLogin() {
        if (this.token == null || this.token.isEmpty()) {
            try {
                FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .redirect("login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this.username == null) {
            this.username = decodeUsernameFromJwt(token);
        }
    }
    
    public String logout() {
        // Session invalidieren
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        // Keycloak Logout mit id_token_hint
        String logoutUrl = "http://localhost:8081/realms/ghostnet/protocol/openid-connect/logout"
            + "?id_token_hint=" + urlEncode(getIdToken())
            + "&post_logout_redirect_uri=" + urlEncode("http://localhost:8080/GhostNetFishing");

        try {
            FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect(logoutUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String urlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            return value;
        }
    }

    private String decodeUsernameFromJwt(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
            return payload.split("\"preferred_username\":\"")[1].split("\"")[0];
        } catch (Exception e) {
            return "unbekannt";
        }
    }
}
