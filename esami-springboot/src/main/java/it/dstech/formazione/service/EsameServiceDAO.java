package it.dstech.formazione.service;

import java.util.List;

import it.dstech.formazione.models.Esame;

public interface EsameServiceDAO {

	Esame add(Esame esame);

	List<Esame> findAll();

	void remove(Esame esame);

	Esame edit(Esame esame);

	Esame findById(Long Id);

	List<Esame> filtraEsami(List<Esame> listaEsamiSvolti,Long idUtente);
	
	List<Esame> ordinaPerVoto(List<Esame> listaEsami, Long idUtente);
	
	String esamePiuBocciato(List<Esame> esamiSvolti, Long idUtente);
}
