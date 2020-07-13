package it.dstech.formazione.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.formazione.models.Esame;
import it.dstech.formazione.models.Esito;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.repository.EsitoRepository;

@Service
public class EsitoServiceDAOImpl implements EsitoServiceDAO{

	@Autowired
	private EsitoRepository repo;
	@Override
	public Esito add(Esito esito) {
	 repo.save(esito);
		return esito;
	}

	@Override
	public List<Esito> findAll() {
		return repo.findAll();
	}

	@Override
	public void remove(Esito esito) {
		repo.delete(esito);
		
	}

	@Override
	public Esito findById(Long id) {
		return repo.findById(id).get();
	}

	@Override
	public Esito findByUtenteAndEsame(Utente utente, Esame esame) {
		return repo.findByUtenteAndEsame(utente, esame);
		
	}

	
}
