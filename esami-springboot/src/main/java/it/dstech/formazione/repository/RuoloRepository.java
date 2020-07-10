package it.dstech.formazione.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dstech.formazione.models.Ruolo;

public interface RuoloRepository extends JpaRepository<Ruolo, Long>{

	Ruolo findByRuolo(String ruolo);
}
