package it.dstech.formazione.service;

import java.util.List;

import it.dstech.formazione.models.Utente;

public interface UtenteServiceDAO {

	Utente add(Utente utente);

	List<Utente> findAll();

	void remove(Utente utente);

	Utente edit(Utente utente);

	Utente findById(Long Id);

	Utente findByCognomeAndNome(String cognome, String nome);

	Utente findByUsername(String username);

	Utente findByUsernameAndPassword(String username, String password);

}
