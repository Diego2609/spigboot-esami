package it.dstech.formazione.service;

import java.util.List;

import it.dstech.formazione.models.Esame;
import it.dstech.formazione.models.Esito;
import it.dstech.formazione.models.Utente;

public interface EsitoServiceDAO {
Esito add(Esito esito);
List<Esito> findAll();
void remove (Esito esito);
Esito findById(Long id);
Esito findByUtenteAndEsame(Utente utente, Esame esame);
}
