package it.dstech.formazione.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dstech.formazione.models.Esame;
import it.dstech.formazione.models.Esito;
import it.dstech.formazione.models.Utente;

public interface EsitoRepository extends JpaRepository<Esito, Long>{

	Esito findByUtenteAndEsame(Utente u, Esame e);
}
