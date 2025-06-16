package de.mariovogel.ghostnetfishing;

import java.io.IOException;

import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import de.mariovogel.ghostnetfishing.*;

@WebFilter("/secure.xhtml")
public class AuthFilter implements Filter {

    @Inject
    private LoginBean loginBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Keine Initialisierung nötig
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Casten auf HttpServletRequest/Response
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Token aus LoginBean prüfen
        if (loginBean == null || loginBean.getToken() == null || loginBean.getToken().isEmpty()) {
            // Nicht eingeloggt → auf Login-Seite weiterleiten
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        } else {
            // Eingeloggt → Anfrage weiterleiten
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Keine Ressourcen freigeben
    }
}
