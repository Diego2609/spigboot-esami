package it.dstech.formazione.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.dstech.formazione.models.Esame;
import it.dstech.formazione.models.Esito;
import it.dstech.formazione.models.Ruolo;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.repository.RuoloRepository;
import it.dstech.formazione.repository.UtenteRepository;

@Service
public class UtenteServiceDAOImpl implements UtenteServiceDAO {
	private UtenteRepository utenteRepo;
	private RuoloRepository ruoloRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UtenteServiceDAOImpl(UtenteRepository utenteRepo, RuoloRepository ruoloRepo,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.utenteRepo = utenteRepo;
		this.ruoloRepo = ruoloRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public Utente add(Utente utente) {
		//utente.setListaEsami(new ArrayList<Esame>());
		utente.setPassword(bCryptPasswordEncoder.encode(utente.getPassword()));
		utente.setActive(true);
		Ruolo ruolo = ruoloRepo.findByRuolo("STUDENTE");
		utente.setRuoli(new HashSet<Ruolo>(Arrays.asList(ruolo)));
		return utenteRepo.save(utente);
		
	}
	

	

	@Override
	public List<Utente> findAll() {

		return utenteRepo.findAll();
	}

	@Override
	public void remove(Utente utente) {

		utenteRepo.delete(utente);
	}

	@Override
	public Utente edit(Utente utente) {
		
		utenteRepo.save(utente);
		return utente;
	}

	@Override
	public Utente findById(Long Id) {

		return utenteRepo.findById(Id).get();
	}

	@Override
	public Utente findByCognomeAndNome(String cognome, String nome) {

		return utenteRepo.findByCognomeAndNome(cognome, nome);

	}

	@Override
	public Utente findByUsername(String username) {

		return utenteRepo.findByUsername(username);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {

		return utenteRepo.findByUsernameAndPassword(username, password);
	}

	@Override
	public Double media(Utente u) {
		
		 int somma=0;
		 int count=0;
		 for (Esame esame : u.getListaEsami()) {
				
			 for (Esito esito : esame.getEsito()) {
				 
				 if(esito.getUtente().getIdUtente()==u.getIdUtente()) {
					 
					 if(esito.getVoto()>=18) {
						 somma += esito.getVoto();
						 count++;
					 }
				 }
				
			}
		 }	
		 if (count==0) {
			return null;
		}
		return (double) (somma/count);
	}
	
	

}
