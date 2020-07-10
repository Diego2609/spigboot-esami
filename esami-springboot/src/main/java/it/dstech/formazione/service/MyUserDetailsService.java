package it.dstech.formazione.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.dstech.formazione.models.Ruolo;
import it.dstech.formazione.models.Utente;

@Service
public class MyUserDetailsService {
	 @Autowired
	    private UtenteServiceDAO utenteService;

	    @Override
	    @Transactional
	    public UserDetails loadUserByUsername(String userName) {
	        Utente utente = utenteService.findByUsername(userName);
	        List<GrantedAuthority> authorities = getUserAuthority(utente.getRuoli());
	        return buildUserForAuthentication(utente, authorities);
	    }

	    private List<GrantedAuthority> getUserAuthority(Set<Ruolo> userRoles) {
	        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
	        for (Ruolo role : userRoles) {
	            roles.add(new SimpleGrantedAuthority(role.getRuolo()));
	        }
	        return new ArrayList<>(roles);
	    }

	    private UserDetails buildUserForAuthentication(Utente user, List<GrantedAuthority> authorities) {
	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
	                user.getActive(), true, true, true, authorities);
	    }
}
