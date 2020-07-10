package it.dstech.formazione.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import it.dstech.formazione.models.Ruolo;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.service.EsameServiceDAO;
import it.dstech.formazione.service.UtenteServiceDAO;

@Controller
public class SessioneController {
	@Autowired
	private EsameServiceDAO esameServ;
	@Autowired
	private UtenteServiceDAO utenteServ;

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

	@PostMapping(value = "/login")
	public ModelAndView access(String username, String password) {
		ModelAndView modelAndView = new ModelAndView();
		Utente user = utenteServ.findByUsernameAndPassword(username, password);
		if (user != null) {
			for (Ruolo ruolo : user.getRuoli()) {
				if (ruolo.getRuolo().equals("STUDENTE")) {
					modelAndView.setViewName("studente/home");
					return modelAndView;
				}
				modelAndView.setViewName("docente/home");
				return modelAndView;
			}
		}
		modelAndView.addObject("messaggio", "User not found");
		modelAndView.setViewName("index");
		return modelAndView;
	}
}
