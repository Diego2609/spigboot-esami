package it.dstech.formazione.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class SessioniAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
	       handle(request, response, authentication);
	        clearAuthenticationAttributes(request);
	    }


	protected void handle(
	        HttpServletRequest request,
	        HttpServletResponse response, 
	        Authentication authentication
	) throws IOException {
	 
	    String targetUrl = determineTargetUrl(authentication);
	 
	  
	 
	    RedirectStrategy.sendRedirect(request, response, targetUrl);
	}
}
