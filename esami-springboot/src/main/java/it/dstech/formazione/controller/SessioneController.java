package it.dstech.formazione.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Docente docente = (Docente) utenteServ.findByUsername(auth.getName());
		docente.getListaEsami().add(esame);
		utenteServ.edit(docente);
		esame.setDocente(docente);
		esameServ.add(esame);
		modelAndView.addObject("messaggio", "Esame aggiunto correttamente");
		modelAndView.setViewName("docente/home");
		return modelAndView;
	}

	@PostMapping(value = "/studente/iscrizioneEsame")
	public ModelAndView iscrizioneEsame(Esame esame) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Studente studente = (Studente) utenteServ.findByUsername(auth.getName());
		esame.getListaStudenti().add(studente);
		esameServ.edit(esame);
		studente.getListaEsami().add(esame);
		utenteServ.edit(studente);
		modelAndView.addObject("messaggio", "Ti sei iscritto correttamente all'esame");
		modelAndView.setViewName("studente/home");
		return modelAndView;
	}
}
