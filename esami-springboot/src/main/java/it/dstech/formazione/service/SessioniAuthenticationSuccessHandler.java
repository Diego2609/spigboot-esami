package it.dstech.formazione.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class SessioniAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	 private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
	       handle(request, response, authentication);
	      //  clearAuthenticationAttributes(request);
	    }


	protected void handle(
	        HttpServletRequest request,
	        HttpServletResponse response, 
	        Authentication authentication
	) throws IOException {
	 
	    String targetUrl = determineTargetUrl(authentication);
	 
	  
	 
	    redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	protected String determineTargetUrl(final Authentication authentication) {
		 
		 boolean isEmp= false;
	        boolean isAdmin = false;
	        Collection<? extends GrantedAuthority> authorities
	         = authentication.getAuthorities();
	        for (GrantedAuthority grantedAuthority : authorities) {
	            if (grantedAuthority.getAuthority().equals("STUDENTE")) {
	                isEmp = true;
	                break;
	            } else if (grantedAuthority.getAuthority().equals("DOCENTE")) {
	                isAdmin = true;
	                break;
	            }
	        }

	        if (isEmp) {
	            return "/studente/home";
	        } else if (isAdmin) {
	            return "/docente/home";
	        } else {
	            throw new IllegalStateException();
	        }
	    }
	
/*	protected void clearAuthenticationAttributes(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        return;
	    }
	    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}*/
}
