package it.dstech.formazione.service;

import java.util.List;

import it.dstech.formazione.models.Ruolo;



public interface RuoloServiceDAO {


	Ruolo add(Ruolo ruolo);

	List<Ruolo> findAll();

	void remove(Ruolo ruolo);

	Ruolo edit(Ruolo ruolo);

	Ruolo findById(Long Id);
	
	Ruolo findByRuolo(String ruolo);
}
