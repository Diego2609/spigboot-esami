package it.dstech.formazione.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.dstech.formazione.models.Esame;
import it.dstech.formazione.repository.EsameRepository;

public class EsameServiceDAOImpl implements EsameServiceDAO {
	@Autowired
	private EsameRepository esameRepo;

	@Override
	public Esame add(Esame esame) {
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

	@Override
	public List<Esame> findByIdUtenteOrderByVoto(Long idUtente) {
		return esameRepo.findByIdUtenteOrderByVoto(idUtente);
	}

}
