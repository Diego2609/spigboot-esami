package it.dstech.formazione.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.dstech.formazione.models.Esame;
import it.dstech.formazione.models.Esito;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.service.EsameServiceDAO;
import it.dstech.formazione.service.EsitoServiceDAO;
import it.dstech.formazione.service.UtenteServiceDAO;

@Controller
public class SessioneController {
	@Autowired
	private EsitoServiceDAO esitoServ;
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
		docente.getListaEsami().add(esame);
		utenteServ.edit(docente);
		modelAndView.addObject("messaggio", "Esame aggiunto correttamente");
		modelAndView.addObject("listaEsamiCreati", docente.getListaEsami());
		modelAndView.setViewName("docente/homeD");
		return modelAndView;
	}

	@PostMapping(value = "/studente/iscrizioneEsame")
	public ModelAndView iscrizioneEsame(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView();
		Esame esame = esameServ.findById(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente studente = utenteServ.findByUsername(auth.getName());
		esame.getListaStudenti().add(studente);
		esameServ.edit(esame);
		studente.getListaEsami().add(esame);
		utenteServ.edit(studente);
		modelAndView.setViewName("studente/homeS");
		modelAndView.addObject("listaEsami", esameServ.filtraEsami(studente.getListaEsami(), studente.getIdUtente()));
		modelAndView.addObject("listaEsamiIscritti", studente.getListaEsami());
		Double media = utenteServ.media(studente);
		if (media != null)
			modelAndView.addObject("media", media);
		else {
			modelAndView.addObject("media", "non hai svolto ancora esami");

		}
		String bocciature =  esameServ.esamePiuBocciato(studente.getListaEsami(), studente.getIdUtente());
		if (bocciature != null) {
			modelAndView.addObject("bocciature", bocciature);
		} else {
			modelAndView.addObject("bocciature", "Non sei ANCORA mai stato bocciato");
		}
		modelAndView.addObject("messaggio", "Ti sei iscritto correttamente all'esame");
		modelAndView.setViewName("studente/homeS");
		modelAndView.addObject("idUtente", studente.getIdUtente());
		return modelAndView;
	}

	@GetMapping(value = { "/studente/homeS" })
	public ModelAndView studente() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente studente = utenteServ.findByUsername(auth.getName());
		modelAndView.setViewName("studente/homeS");
		modelAndView.addObject("listaEsami", esameServ.filtraEsami(studente.getListaEsami(), studente.getIdUtente()));
		modelAndView.addObject("listaEsamiIscritti", esameServ.ordinaPerVoto(studente.getListaEsami(), studente.getIdUtente()));
		Double media = utenteServ.media(studente);
		if (media != null)
			modelAndView.addObject("media", media);
		else {
			modelAndView.addObject("media", "Non hai svolto ancora esami");
		}
		modelAndView.addObject("idUtente", studente.getIdUtente());
		String bocciature =  esameServ.esamePiuBocciato(studente.getListaEsami(), studente.getIdUtente());
		if (!bocciature.equals("")){
			modelAndView.addObject("bocciature", bocciature);
		} else {
			modelAndView.addObject("bocciature", "Non sei ANCORA mai stato bocciato");
		}
		
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

	@PostMapping(value = { "/docente/dettagli" })
	public ModelAndView dettagli(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView();
		Esame esame = esameServ.findById(id);
		modelAndView.addObject("esame", esame);
		modelAndView.setViewName("docente/dettagli");
		return modelAndView;
	}

	@PostMapping(value = { "/docente/voto" })
	public ModelAndView voto(@RequestParam Long id, @RequestParam Long idUtente) {
		ModelAndView modelAndView = new ModelAndView();
		Esito esito = esitoServ.findByUtenteAndEsame(utenteServ.findById(idUtente), esameServ.findById(id));
		if (esito == null) {
			esito = new Esito();
			esito.setUtente(utenteServ.findById(idUtente));
			esito.setEsame(esameServ.findById(id));
		}
		modelAndView.addObject("esito", esito);
		modelAndView.setViewName("docente/voto");
		return modelAndView;
	}

	@PostMapping(value = "/docente/esito")
	public ModelAndView esito(Esito esito, @RequestParam("idUtente") Long idUtente,
			@RequestParam("idEsame") Long idEsame, @RequestParam("idEsito") Long idEsito) {
		ModelAndView modelAndView = new ModelAndView();
		esito.setId(idEsito);
		esito.setEsame(esameServ.findById(idEsame));
		esito.setUtente(utenteServ.findById(idUtente));
		esitoServ.add(esito);
		Utente utente = esito.getUtente();
		Esame esame = esito.getEsame();
		if (utente.getListaEsiti() == null) {
			utente.setListaEsiti(new ArrayList<>());
		}
		utente.getListaEsiti().add(esito);
		if (esame.getEsito() == null) {
			esame.setEsito(new ArrayList<>());
		}
		utente.getListaEsiti().add(esito);
		utenteServ.edit(utente);
		esame.getEsito().add(esito);
		esameServ.edit(esame);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente docente = utenteServ.findByUsername(auth.getName());
		modelAndView.addObject("messaggio", "voto aggiunto");
		modelAndView.setViewName("docente/homeD");
		modelAndView.addObject("listaEsamiCreati", docente.getListaEsami());
		modelAndView.addObject("esame", new Esame());
		return modelAndView;
	}
@PostMapping(value="/docente/indietro")
public ModelAndView docenteIndietro() {
	ModelAndView modelAndView = new ModelAndView();
	modelAndView.setViewName("redirect:/docente/homeD");
	return modelAndView;
}
}