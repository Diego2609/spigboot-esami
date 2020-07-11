package it.dstech.formazione.models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("1")
public class Studente extends Utente {

	private String matricola;
	@ManyToMany
	@JoinTable(name = "studente_esame", joinColumns = @JoinColumn(name = "studente_idUtente"), inverseJoinColumns = @JoinColumn(name = "esame_id"))
	private List<Esame> listaEsami;

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public List<Esame> getListaEsami() {
		return listaEsami;
	}

	public void setListaEsami(List<Esame> listaEsami) {
		this.listaEsami = listaEsami;
	}
}
