package it.dstech.formazione.models;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


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
	private Date data;
	@ManyToMany
	@JoinTable(name = "esame_utente", joinColumns = @JoinColumn(name = "esame_id"), inverseJoinColumns = @JoinColumn(name = "utente_idUtente"))
	private List<Utente> listaStudenti;
	@OneToMany(mappedBy= "esame")
	private List<Esito> esito;

	
	public List<Esito> getEsito() {
		return esito;
	}

	public void setEsito(List<Esito> esito) {
		this.esito = esito;
	}

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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Utente> getListaStudenti() {
		return listaStudenti;
	}

	public void setListaStudenti(List<Utente> listaStudenti) {
		this.listaStudenti = listaStudenti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((materia == null) ? 0 : materia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Esame other = (Esame) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (materia == null) {
			if (other.materia != null)
				return false;
		} else if (!materia.equals(other.materia))
			return false;
		return true;
	}
	

}
