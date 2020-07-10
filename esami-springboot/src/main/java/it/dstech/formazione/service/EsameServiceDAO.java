package it.dstech.formazione.service;

import java.util.List;

import it.dstech.formazione.models.Esame;

public interface EsameServiceDAO {

	Esame add(Esame esame);

	List<Esame> findAll();

	void remove(Esame esame);

	Esame edit(Esame esame);

	Esame findById(Long Id);

	List<Esame> findByIdUtenteOrderByVoto(Long idUtente);

}
