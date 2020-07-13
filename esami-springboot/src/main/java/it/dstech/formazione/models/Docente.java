package it.dstech.formazione.models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

//@Entity
//@DiscriminatorValue("2")
public class Docente extends Utente{

//	@OneToMany(mappedBy = "docente")
//	private List<Esame> listaEsami;
//
//	public List<Esame> getListaEsami() {
//		return listaEsami;
//	}
//
//	public void setListaEsami(List<Esame> listaEsami) {
//		this.listaEsami = listaEsami;
//	}
}
