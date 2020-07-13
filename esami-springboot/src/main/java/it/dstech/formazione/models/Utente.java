package it.dstech.formazione.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
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
	@ManyToMany
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

	
	
}
