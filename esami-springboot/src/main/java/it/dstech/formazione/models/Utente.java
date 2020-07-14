package it.dstech.formazione.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "utente_id", discriminatorType = DiscriminatorType.INTEGER)
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUtente;
	private String email;
	private String username;
	private String password;
	private String nome;
	private String cognome;
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_idUtente"), inverseJoinColumns = @JoinColumn(name = "ruolo_idRuolo"))
	private Set<Ruolo> ruoli;
	private boolean active;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "utente_esame", joinColumns = @JoinColumn(name = "utente_idUtente"), inverseJoinColumns = @JoinColumn(name = "esame_id"))

	private List<Esame> listaEsami;
	
	@OneToMany(mappedBy= "utente")
	private List<Esito> listaEsiti;
	
	public List<Esito> getListaEsiti() {
		return listaEsiti;
	}
	public void setListaEsiti(List<Esito> listaEsiti) {
		this.listaEsiti = listaEsiti;
	}
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Set<Ruolo> getRuoli() {
		return ruoli;
	}
	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<Esame> getListaEsami() {
		return listaEsami;
	}
	public void setListaEsami(List<Esame> listaEsami) {
		this.listaEsami = listaEsami;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((idUtente == null) ? 0 : idUtente.hashCode());
		result = prime * result + ((listaEsami == null) ? 0 : listaEsami.hashCode());
		result = prime * result + ((listaEsiti == null) ? 0 : listaEsiti.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((ruoli == null) ? 0 : ruoli.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Utente other = (Utente) obj;
		if (active != other.active)
			return false;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idUtente == null) {
			if (other.idUtente != null)
				return false;
		} else if (!idUtente.equals(other.idUtente))
			return false;
		if (listaEsami == null) {
			if (other.listaEsami != null)
				return false;
		} else if (!listaEsami.equals(other.listaEsami))
			return false;
		if (listaEsiti == null) {
			if (other.listaEsiti != null)
				return false;
		} else if (!listaEsiti.equals(other.listaEsiti))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (ruoli == null) {
			if (other.ruoli != null)
				return false;
		} else if (!ruoli.equals(other.ruoli))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
	
}
