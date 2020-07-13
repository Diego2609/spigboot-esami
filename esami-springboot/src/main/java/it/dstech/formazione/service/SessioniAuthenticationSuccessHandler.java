package it.dstech.formazione.service;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

public class SessioniAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//private EsameServiceDAO esameServ;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		handle(request, response, authentication);

	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String targetUrl = determineTargetUrl(authentication);
//		if("/studente/home".equals(targetUrl)) {
//			request.setAttribute("listaEsami", esameServ.findAll());
//		} else {
//			request.setAttribute("listaEsami", esameServ.findAll());
//		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(final Authentication authentication) {

		boolean isEmp = false;
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
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
			return "/studente/homeS";
		} else if (isAdmin) {
			return "/docente/homeD";
		} else {
			throw new IllegalStateException();
		}
	}

}
