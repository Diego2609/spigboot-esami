package it.dstech.formazione.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Esame {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String materia;
	@JsonFormat(pattern = "dd-MM-yyyy ")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDateTime data;
	@ManyToMany
	@JoinTable(name = "esame_utente", joinColumns = @JoinColumn(name = "esame_id"), inverseJoinColumns = @JoinColumn(name = "utente_idUtente"))
	private List<Utente> listaStudenti;
	private int voto;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public List<Utente> getListaStudenti() {
		return listaStudenti;
	}
	public void setListaStudenti(List<Utente> listaStudenti) {
		this.listaStudenti = listaStudenti;
	}
	
	public int getVoto() {
		return voto;
	}
	public void setVoto(int voto) {
		this.voto = voto;
	}
	
	
	

}
