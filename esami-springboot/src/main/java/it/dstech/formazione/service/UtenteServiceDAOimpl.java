package it.dstech.formazione.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.formazione.models.Utente;
import it.dstech.formazione.repository.UtenteRepository;

@Service
public class UtenteServiceDAOimpl implements UtenteServiceDAO{

	@Autowired
	private UtenteRepository utenteRepo;
	@Override
	public Utente add(Utente utente) {
		
		utenteRepo.save(utente);
		return utente;
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

}
