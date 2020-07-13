package it.dstech.formazione.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.formazione.models.Esame;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.repository.EsameRepository;
@Service
public class EsameServiceDAOImpl implements EsameServiceDAO {
	@Autowired
	private EsameRepository esameRepo;

	@Override
	public Esame add(Esame esame) {
		esame.setListaStudenti(new ArrayList<Utente>());
		esameRepo.save(esame);
		return esame;
	}

	@Override
	public List<Esame> findAll() {

		return esameRepo.findAll();
	}

	@Override
	public void remove(Esame esame) {
		esameRepo.delete(esame);

	}

	@Override
	public Esame edit(Esame esame) {
		esameRepo.save(esame);
		return esame;
	}

	@Override
	public Esame findById(Long Id) {
		return esameRepo.findById(Id).get();
	}

	

}
