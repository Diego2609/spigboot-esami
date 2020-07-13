package it.dstech.formazione.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.dstech.formazione.models.Docente;
import it.dstech.formazione.models.Esame;
import it.dstech.formazione.models.Studente;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.service.EsameServiceDAO;
import it.dstech.formazione.service.UtenteServiceDAO;

@Controller
public class SessioneController {

	@Autowired
	private UtenteServiceDAO utenteServ;
	@Autowired
	private EsameServiceDAO esameServ;

	@GetMapping(value = { "/", "/login" })
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping(value = "/registrazione")
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		Utente user = new Utente();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registrazione");
		return modelAndView;
	}

	@PostMapping(value = "/registrazione")
	public ModelAndView createNewUser(Utente user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Utente userExists = utenteServ.findByUsername(user.getUsername());
		if (userExists != null) {
			bindingResult.rejectValue("username", "error.user",
					"There is already a user registered with the user name provided");

		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registrazione");
		} else {
			utenteServ.add(user);
			modelAndView.addObject("messaggio", "User has been registered successfully");
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}

	@PostMapping(value = "/docente/nuovoEsame")
	public ModelAndView aggiungiEsame(Esame esame) {
		ModelAndView modelAndView = new ModelAndView();
		esameServ.add(esame);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente docente = utenteServ.findByUsername(auth.getName());
		docente.setListaEsami(new ArrayList<>());
		docente.getListaEsami().add(esame);
		utenteServ.edit(docente);
		modelAndView.addObject("messaggio", "Esame aggiunto correttamente");
		modelAndView.setViewName("docente/homeD");
		return modelAndView;
	}

	@PostMapping(value = "/studente/iscrizioneEsame")
	public ModelAndView iscrizioneEsame(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView();
		Esame esame=esameServ.findById(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente studente = utenteServ.findByUsername(auth.getName());
		esame.getListaStudenti().add(studente);
		esameServ.edit(esame);
		studente.getListaEsami().add(esame);
		utenteServ.edit(studente);
		modelAndView.setViewName("studente/homeS");
		List<Esame> esami=esameServ.findAll();
		esami.removeAll(studente.getListaEsami());		
		modelAndView.addObject("listaEsami", esami);
		modelAndView.addObject("listaEsamiIscritti", studente.getListaEsami());
		modelAndView.addObject("messaggio", "Ti sei iscritto correttamente all'esame");
		modelAndView.setViewName("studente/homeS");
		return modelAndView;
	}
	@GetMapping(value = { "/studente/homeS" })
	public ModelAndView studente() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente studente = utenteServ.findByUsername(auth.getName());
		modelAndView.setViewName("studente/homeS");
		List<Esame> esami=esameServ.findAll();
		esami.removeAll(studente.getListaEsami());		
		modelAndView.addObject("listaEsami", esami);
		modelAndView.addObject("listaEsamiIscritti", studente.getListaEsami());
		return modelAndView;
	}
	
	@GetMapping(value = { "/docente/homeD" })
	public ModelAndView docente() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente docente = utenteServ.findByUsername(auth.getName());
		modelAndView.setViewName("docente/homeD");
		modelAndView.addObject("esame", new Esame());
		modelAndView.addObject("listaEsamiCreati", docente.getListaEsami());
		return modelAndView;
	}
}
